package com.example.javierpalaciocuenca.myapplication.resources;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;
import com.example.javierpalaciocuenca.myapplication.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.myapplication.utils.JSONReader;
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

public abstract class JSONResource {
    protected Context context;
    protected ProgressDialog progressDialog;
    protected Integer icon = R.drawable.default_icon;
    protected Integer marker = R.drawable.default_marker;

    public abstract List<MapItem> execute();

    protected void setIcon(Integer icon) {
        this.icon = icon;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    Context getContext() {
        return context;
    }

    public Integer getIcon() {
        return icon;
    }

    Integer getMarker() {
        return marker;
    }

    protected void setMarker(Integer marker) {
        this.marker = marker;
    }

    ProgressDialog getProgressBar() {
        return progressDialog;
    }

    //Default execute for standard JSON formatting
    protected List<MapItem> execute(String resourceURL) {
        JSONObject jsonObject;
        List<MapItem> mapItems = new ArrayList<>();

        AsyncTask<String, Void, JSONObject> asyncTask = new JSONReader(this.context, this.progressDialog).execute(resourceURL);

        try {
            LatLng latLng;
            String[] location;
            String title, url;
            JSONObject objectLocationContent, objectTitle;

            jsonObject = asyncTask.get();
            JSONArray jsonArray = jsonObject.getJSONObject("directorios").getJSONArray("directorio");

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                //Location
                objectLocationContent = jsonObject.getJSONObject("localizacion");
                location = objectLocationContent.has("content") ? objectLocationContent.getString("content").split(" ") : null;
                latLng = location != null ? new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1])) : null;

                //Title
                objectTitle = jsonObject.getJSONObject("nombre");
                title = objectTitle.has("content") ? objectTitle.getString("content") : null;

                //URL
                url = jsonObject.has("url") ? jsonObject.getString("url") : null;

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

    protected List<MapItem> execute(String url, String key) {
        MapItem mapItem;
        JSONObject jsonObject;
        List<MapItem> mapItems = new ArrayList<>();

        AsyncTask<String, Void, JSONObject> asyncTask = new JSONReader(this.context, this.progressDialog).execute(url);

        try {
            LatLng latLng;
            String title;

            jsonObject = asyncTask.get();
            JSONArray jsonArray = jsonObject.getJSONObject(key).getJSONArray(key.substring(0, key.length() - 1));

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);

                latLng = new LatLng(jsonObject.getDouble("latitud"), jsonObject.getDouble("longitud"));
                title = jsonObject.getString("lugar");
                mapItem = new MapItem(title, latLng, this.marker);
                mapItems.add(mapItem);
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
