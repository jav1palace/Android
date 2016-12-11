package com.example.javierpalaciocuenca.app.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.javierpalaciocuenca.app.persistence.model.BusStop;
import com.example.javierpalaciocuenca.app.utils.constants.DataBaseConstants;
import com.example.javierpalaciocuenca.app.utils.ExceptionDialogBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javierpalaciocuenca on 08/12/2016.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyDataBaseHelper";

    private static MyDataBaseHelper sInstance;
    private static Context context;

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private MyDataBaseHelper(Context context) {
        super(context, DataBaseConstants.DATABASE_NAME, null, DataBaseConstants.DATABASE_VERSION);
        this.context = context;
    }

    public static synchronized MyDataBaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MyDataBaseHelper(context.getApplicationContext());
        }

        return sInstance;
    }

    private void dropAndCreateTables(SQLiteDatabase db) {
        String CREATE_BUS_STOP_TABLE = "CREATE TABLE " + DataBaseConstants.TABLE_BUS_STOP +
                "(" +
                DataBaseConstants.KEY_BUS_STOP_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                DataBaseConstants.KEY_BUS_STOP_ORDEN + " INTEGER," +
                DataBaseConstants.KEY_BUS_STOP_LINEA + " INTEGER," +
                DataBaseConstants.KEY_BUS_STOP_TRAYECTO + " INTEGER," +
                DataBaseConstants.KEY_BUS_STOP_UTMY + " NUMERIC," +
                DataBaseConstants.KEY_BUS_STOP_UTMX + " NUMERIC" +
                ")";

        db.execSQL("DROP TABLE IF EXISTS " + DataBaseConstants.TABLE_BUS_STOP);
        db.execSQL(CREATE_BUS_STOP_TABLE);

        Log.v(TAG, "DataBase tables dropped ✓");
        Log.v(TAG, "DataBase create tables ✓");
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "Creating DataBase...");
        dropAndCreateTables(db);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "Updating DataBase...");
        if (oldVersion != newVersion) {
            dropAndCreateTables(db);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.v(TAG, "Opening DataBase...");
        dropAndCreateTables(db);
    }

    public void insertBusStop(BusStop busStop) {
        List<BusStop> busStops = new ArrayList<BusStop>();
        busStops.add(busStop);

        insertBusStops(busStops);
    }

    public void insertBusStops(List<BusStop> busStops) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {

            ContentValues values;
            for (BusStop busStop : busStops) {
                values = new ContentValues();
                values.put(DataBaseConstants.KEY_BUS_STOP_ORDEN, busStop.getOrden());
                values.put(DataBaseConstants.KEY_BUS_STOP_LINEA, busStop.getIdLinea());
                values.put(DataBaseConstants.KEY_BUS_STOP_TRAYECTO, busStop.getIdTrayecto());
                values.put(DataBaseConstants.KEY_BUS_STOP_UTMY, busStop.getUtmy());
                values.put(DataBaseConstants.KEY_BUS_STOP_UTMX, busStop.getUtmx());

                // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
                db.insertOrThrow(DataBaseConstants.TABLE_BUS_STOP, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.v(TAG, "Error while inserting bus stops x");
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } finally {
            db.endTransaction();
            Log.v(TAG, busStops.size() + " bus stops inserted ✓");
        }
    }

    public List<BusStop> getAllBusStops() {
        List<BusStop> busStops = new ArrayList<>();

        String POSTS_SELECT_QUERY = String.format("SELECT * FROM %s", DataBaseConstants.TABLE_BUS_STOP);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                BusStop busStop;

                do {
                    busStop = new BusStop();
                    busStop.setId(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_ID)));
                    busStop.setOrden(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_ORDEN)));
                    busStop.setIdLinea(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_LINEA)));
                    busStop.setIdTrayecto(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_TRAYECTO)));
                    busStop.setUtmx(cursor.getDouble(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_UTMX)));
                    busStop.setUtmy(cursor.getDouble(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_UTMY)));

                    busStops.add(busStop);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.v(TAG, "Error while getting bus stops x");
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                Log.v(TAG, busStops.size() + " bus stops were retrieved ✓");
                cursor.close();
            }
        }
        return busStops;
    }

    public List<BusStop> getBusStops(int idLinea, int idTrayecto) {
        List<BusStop> busStops = new ArrayList<>();

        String POSTS_SELECT_QUERY = String.format("SELECT * FROM %s WHERE idlinea=%s AND idtrayecto=%s",
                DataBaseConstants.TABLE_BUS_STOP, idLinea, idTrayecto);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                BusStop busStop;

                do {
                    busStop = new BusStop();
                    busStop.setId(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_ID)));
                    busStop.setOrden(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_ORDEN)));
                    busStop.setIdLinea(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_LINEA)));
                    busStop.setIdTrayecto(cursor.getInt(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_TRAYECTO)));
                    busStop.setUtmx(cursor.getDouble(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_UTMY)));
                    busStop.setUtmy(cursor.getDouble(cursor.getColumnIndex(DataBaseConstants.KEY_BUS_STOP_UTMX)));

                    busStops.add(busStop);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.v(TAG, "Error while getting bus stops x");
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                Log.v(TAG, busStops.size() + " bus stops were retrieved ✓");
                cursor.close();
            }
        }
        return busStops;
    }

    public void emptyTable(SQLiteDatabase db, String tableName) {

        db.beginTransaction();

        try {
            db.delete(tableName, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.v(TAG, "Error deleting " + tableName + " content x");
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } finally {
            Log.v(TAG, tableName + " content deleted ✓");
            db.endTransaction();
        }
    }
}