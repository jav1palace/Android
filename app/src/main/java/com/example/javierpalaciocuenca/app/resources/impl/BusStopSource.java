package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.javierpalaciocuenca.app.persistence.MyDataBaseHelper;
import com.example.javierpalaciocuenca.app.persistence.model.BusStop;
import com.example.javierpalaciocuenca.app.persistence.model.MapItem;
import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.resources.utils.JSONReader;
import com.example.javierpalaciocuenca.app.resources.utils.JSONResourceUtils;
import com.example.javierpalaciocuenca.app.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.app.utils.constants.Constants;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;
import com.example.javierpalaciocuenca.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class BusStopSource extends JSONResource {
    private static final String TAG = "BusStopSource";

    public BusStopSource() {
        setURL(URLConstants.BUS_STOP_SOURCE_URL);
        setPluralKey(Constants.JSON_BUS_STOPS_NAME_PLURAL);
        setSingularKey(Constants.JSON_BUS_STOPS_NAME_SINGULAR);
        setMarker(R.drawable.busstop_marker);
        setIcon(R.drawable.busstop_icon);
    }

    public BusStopSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public BusStopSource(Context context) {
        new BusStopSource(context, null);
    }

    public List<BusStop> getBusStops() {
        List<BusStop> busStops = new ArrayList<>();
        try {

            AsyncTask<String, Void, JSONObject> asyncTask = new JSONReader(this.context, this.progressDialog).execute(getURL());

            JSONObject jsonObject;
            jsonObject = asyncTask.get();

            busStops = JSONResourceUtils.getBusStops(jsonObject, getPluralKey(), getSingularKey());

        } catch (InterruptedException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } catch (ExecutionException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } catch (JSONException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } catch (Exception e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        }

        return busStops;
    }

    public List<MapItem> execute() {
        MyDataBaseHelper dbHelper = MyDataBaseHelper.getInstance(this.context);
        return JSONResourceUtils.createMapItemsFromBusStops(dbHelper.getAllBusStops(), getMarker());
    }
}
