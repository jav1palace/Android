package com.example.javierpalaciocuenca.myapplication.services.impl;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.services.JSONResource;
import com.example.javierpalaciocuenca.myapplication.utilities.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.myapplication.utilities.JSONReader;
import com.example.javierpalaciocuenca.myapplication.utilities.MapItem;
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

public class CitizenATMSource extends JSONResource {

    public CitizenATMSource() {
        setIcon(R.drawable.image1);
        setMarker(R.drawable.marker1); // Size has to be 25x25
    }

    public CitizenATMSource(Context context, ProgressBar progressBar) {
        new CitizenATMSource();
        this.context = context;
        this.progressBar = progressBar;
    }

    public CitizenATMSource(Context context) {
        new CitizenATMSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        MapItem mapItem;
        JSONObject jsonObject;
        List<MapItem> mapItems = new ArrayList<>();

        AsyncTask<String, Void, JSONObject> asyncTask = new JSONReader(progressBar, context).execute("http://opendata.gijon.es/descargar.php?id=7&tipo=JSON");

        try {
            LatLng latLng;
            String title;

            jsonObject = asyncTask.get();
            JSONArray jsonArray = jsonObject.getJSONObject("contenedorropas").getJSONArray("contenedorropa");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                latLng = new LatLng(jsonObject.getDouble("latitud"), jsonObject.getDouble("longitud"));
                title = jsonObject.getString("lugar");
                mapItem = new MapItem(title, latLng, marker);
                mapItems.add(mapItem);
            }
        } catch (InterruptedException e) {
            ExceptionDialogBuilder.createExceptionDialog(context, e.getMessage()).show();
        } catch (ExecutionException e) {
            ExceptionDialogBuilder.createExceptionDialog(context, e.getMessage()).show();
        } catch (JSONException e) {
            ExceptionDialogBuilder.createExceptionDialog(context, e.getMessage()).show();
        }

        return mapItems;
    }
}
