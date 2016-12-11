package com.example.javierpalaciocuenca.app.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.javierpalaciocuenca.app.persistence.MyDataBaseHelper;
import com.example.javierpalaciocuenca.app.persistence.model.MapItem;
import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.resources.impl.ATMSource;
import com.example.javierpalaciocuenca.app.resources.impl.BowlingSource;
import com.example.javierpalaciocuenca.app.resources.impl.BusLocationSource;
import com.example.javierpalaciocuenca.app.resources.impl.BusStopSource;
import com.example.javierpalaciocuenca.app.resources.impl.CampingSource;
import com.example.javierpalaciocuenca.app.resources.impl.CasinoSource;
import com.example.javierpalaciocuenca.app.resources.impl.CinemaSource;
import com.example.javierpalaciocuenca.app.resources.impl.CitizenATMSource;
import com.example.javierpalaciocuenca.app.resources.impl.GolfSource;
import com.example.javierpalaciocuenca.app.resources.impl.HealthCentreSource;
import com.example.javierpalaciocuenca.app.resources.impl.MentalHealthSource;
import com.example.javierpalaciocuenca.app.resources.impl.OilDepositSource;
import com.example.javierpalaciocuenca.app.resources.impl.RecreationalAreaSource;
import com.example.javierpalaciocuenca.app.resources.impl.SoccerFieldSource;
import com.example.javierpalaciocuenca.app.ui.custom.CustomList;
import com.example.javierpalaciocuenca.app.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.app.utils.MapItemsData;
import com.example.javierpalaciocuenca.app.utils.constants.Constants;
import com.example.javierpalaciocuenca.myapplication.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ServiceListActivity extends Activity {
    private static final String TAG = "ServiceListActivity";
    private HashMap<String, Class> classMap = new HashMap<String, Class>() {
        {
            put("Clothes Deposits", CitizenATMSource.class);
            put("Bowling", BowlingSource.class);
            put("ATMs", ATMSource.class);
            put("Recreational Areas", RecreationalAreaSource.class);
            put("Casinos", CasinoSource.class);
            put("Campings", CampingSource.class);
            put("Golf", GolfSource.class);
            put("Soccer Fields", SoccerFieldSource.class);
            put("Health Centres", HealthCentreSource.class);
            put("Cinemas", CinemaSource.class);
            put("Mental Health", MentalHealthSource.class);
            put("Oil Deposits", OilDepositSource.class);
            put("Live BUS", BusLocationSource.class);
            put("BUS Lines", BusStopSource.class);
        }
    };
    private List<String> headers = new ArrayList<>(this.classMap.keySet());
    private ProgressDialog progressDialog;
    private CustomList adapter;
    private ListView listView;
    private GoogleApiClient client;
    
    private List<MapItem> getMapItems(int position) throws IllegalAccessException, InstantiationException {
        JSONResource jsonResource = (JSONResource) this.classMap.get(headers.get(position)).newInstance();
        jsonResource.setContext(ServiceListActivity.this);
        jsonResource.setProgressDialog(progressDialog);

        return jsonResource.execute();
    }

    private Intent createIntentAndPopulateItems(Class usableClass, ArrayList<MapItem> mapItems) {
        String intentType = usableClass != BusLocationSource.class ? Constants.INTENT_TYPE_DEFAULT : Constants.INTENT_TYPE_BUS;

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        intent.putExtra("intentType", intentType);

        //Populate the mapItems to be accessed xplatform (not in the intent anymore, too big sometimes)
        MapItemsData.getInstance().setMapItems(mapItems);

        return intent;
    }

    private void clickLogic(int position) {
        //TODO: Create a factory that creates the object or maybe create a naming code like in MyJet with the upserts
        try {
            Class usableClass = classMap.get(headers.get(position));
            if (usableClass != null) {

                ArrayList<MapItem> mapItems = (ArrayList<MapItem>) getMapItems(position);
                Intent intent = createIntentAndPopulateItems(usableClass, mapItems);

                startActivity(intent);
            } else {
                Toast.makeText(ServiceListActivity.this, "The " + headers.get(+position) + " is not available yet ", Toast.LENGTH_SHORT).show();
            }

        } catch (InstantiationException e) {
            ExceptionDialogBuilder.createExceptionDialog(ServiceListActivity.this, e.getMessage()).show();
        } catch (IllegalAccessException e) {
            ExceptionDialogBuilder.createExceptionDialog(ServiceListActivity.this, e.getMessage()).show();
        }
    }

    private void initAdapter() {
        if (this.adapter == null) {
            this.adapter = new CustomList(ServiceListActivity.this, classMap);
        }
    }

    private void initProgressDialog() {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(ServiceListActivity.this);
            this.progressDialog.setTitle("Connecting");
            this.progressDialog.setMessage("Resource is being downloaded and processed");
        }
    }

    private void initListView() {
        this.listView = (ListView) findViewById(R.id.listview);
        this.listView.setAdapter(this.adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickLogic(position);
            }
        });
    }

    private void initDatabase() {
        MyDataBaseHelper dataBaseHelper = MyDataBaseHelper.getInstance(ServiceListActivity.this);

        BusStopSource busSource = new BusStopSource(ServiceListActivity.this, null);
        dataBaseHelper.insertBusStops(busSource.getBusStops());

        //TODO: Show a message like the one we use when no resource available
        if (dataBaseHelper.getAllBusStops().size() != 0) {
            Log.v(TAG, "DataBase bus stops fillup ✓");
            Toast.makeText(ServiceListActivity.this, "WARNING: DataBase bus stops fillup, they won't be shown ", Toast.LENGTH_LONG).show();
        } else {
            String message = "DataBase bus stops fillup x";
            Toast.makeText(ServiceListActivity.this, "WARNING: DataBase bus stops fillup, they won't be shown ", Toast.LENGTH_LONG).show();
            Log.v(TAG, message);
        }
    }

    private void init() {
        setContentView(R.layout.main_activity);
        initAdapter();
        initProgressDialog();
        initListView();
        initDatabase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // ATTENTION: This was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
            init();
        }catch (Exception e) {
            ExceptionDialogBuilder.createExceptionDialog(ServiceListActivity.this, e.getMessage()).show();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("My Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Hides the progress dialog anytime coming from another activity
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        this.client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        this.client.disconnect();
    }
}
