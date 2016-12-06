package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.myapplication.utils.JSONReader;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class BowlingSource extends JSONResource {

    public BowlingSource() {
        setIcon(R.drawable.bowling_icon);
        setMarker(R.drawable.bowling_marker); // Size has to be 25x25
    }

    public BowlingSource(Context context, ProgressDialog progressDialog) {
        new BowlingSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public BowlingSource(Context context) {
        new BowlingSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        JSONObject jsonObject;
        List<MapItem> mapItems = new ArrayList<>();

        AsyncTask<String, Void, JSONObject> asyncTask = new JSONReader(this.context, this.progressDialog).execute("http://datos.gijon.es/doc/deporte/boleras.json");

        try {
            LatLng latLng;
            String title, url;

            jsonObject = asyncTask.get();
            JSONArray jsonArray = jsonObject.getJSONObject("directorios").getJSONArray("directorio");

            String[] location;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                location = jsonObject.getJSONObject("localizacion").getString("content").split(" ");
                title = jsonObject.getJSONObject("nombre").getString("content");
                url = jsonObject.getString("url");
                latLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));

                mapItems.add(new MapItem(title, latLng, this.marker, url));
            }
        } catch (InterruptedException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } catch (ExecutionException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } catch (JSONException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        }

        return mapItems;
    }
}
