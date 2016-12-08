package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.javierpalaciocuenca.myapplication.persistence.model.BusStop;
import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.utils.Constants;
import com.example.javierpalaciocuenca.myapplication.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.myapplication.utils.JSONReader;
import com.example.javierpalaciocuenca.myapplication.utils.URLConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class BusStopSource extends JSONResource {
    private String singularKey = Constants.JSON_BUS_STOPS_NAME_SINGULAR;

    public BusStopSource() {
        setURL(URLConstants.BUS_STOP_SOURCE_URL);
        setKey(Constants.JSON_BUS_STOPS_NAME_PLURAL);
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
        ArrayList<BusStop> busStops = new ArrayList<>();
        try {
            int orden, idLinea, idTrayecto;
            double utmx, utmy;
            JSONObject jsonObject;

            AsyncTask<String, Void, JSONObject> asyncTask = new JSONReader(this.context, this.progressDialog).execute(getURL());

            jsonObject = asyncTask.get();
            JSONArray jsonArray = jsonObject.getJSONObject(getKey()).getJSONArray(singularKey);

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                orden = Integer.parseInt(jsonObject.getString("orden"));
                idLinea = Integer.parseInt(jsonObject.getString("idlinea"));
                idTrayecto = Integer.parseInt(jsonObject.getString("idtrayecto"));
                utmx = Double.parseDouble(jsonObject.getString("utmx"));
                utmy = Double.parseDouble(jsonObject.getString("utmy"));

                busStops.add(new BusStop(0, orden, idLinea, idTrayecto, utmx, utmy));
            }

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
}
