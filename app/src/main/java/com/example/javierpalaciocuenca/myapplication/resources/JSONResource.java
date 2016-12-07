package com.example.javierpalaciocuenca.myapplication.resources;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;
import com.example.javierpalaciocuenca.myapplication.utils.Constants;
import com.example.javierpalaciocuenca.myapplication.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.myapplication.utils.JSONReader;
import com.example.javierpalaciocuenca.myapplication.utils.URLConstants;
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
    protected String URL = URLConstants.DEFAULT_SOURCE_URL;
    protected String key = Constants.JSON_DEFAULT_ARRAY_NAME_PLURAL;

    protected String getKey() {
        return key;
    }

    protected void setKey(String key) {
        this.key = key;
    }

    Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Integer getIcon() {
        return icon;
    }

    protected void setIcon(Integer icon) {
        this.icon = icon;
    }

    Integer getMarker() {
        return marker;
    }

    ProgressDialog getProgressBar() {
        return progressDialog;
    }

    protected String getURL() {
        return URL;
    }

    protected void setURL(String URL) {
        this.URL = URL;
    }

    protected void setMarker(Integer marker) {
        this.marker = marker;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public List<MapItem> execute() {
         class Utils {
            private LatLng getLatLng(JSONObject jsonObject) throws JSONException {
                LatLng latLng;

                if (getKey().equals(Constants.JSON_DEFAULT_ARRAY_NAME_PLURAL)) {
                    String[] location;
                    JSONObject objectLocationContent;

                    objectLocationContent = jsonObject.getJSONObject("localizacion");
                    location = objectLocationContent.has("content") ? objectLocationContent.getString("content").split(" ") : null;
                    latLng = location != null ? new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1])) : null;

                } else {
                    latLng = new LatLng(jsonObject.getDouble("latitud"), jsonObject.getDouble("longitud"));
                }

                return latLng;
            }

            private String getTitle(JSONObject jsonObject) throws JSONException {
                String title;

                if (getKey().equals(Constants.JSON_DEFAULT_ARRAY_NAME_PLURAL)) {
                    JSONObject objectTitle;

                    objectTitle = jsonObject.getJSONObject("nombre");
                    title = objectTitle.has("content") ? objectTitle.getString("content") : null;

                } else {
                    title = jsonObject.getString("lugar");
                }

                return title;
            }

            private String getURL(JSONObject jsonObject) throws JSONException {
                String urlObject = jsonObject.has("url") ? jsonObject.getString("url") : null;

                return urlObject;
            }
        }

        JSONObject jsonObject;
        List<MapItem> mapItems = new ArrayList<>();

        if (!getURL().equals(URLConstants.DEFAULT_SOURCE_URL)) {

            try {
                LatLng latLng;
                String title, urlForMarker;

                AsyncTask<String, Void, JSONObject> asyncTask = new JSONReader(this.context, this.progressDialog).execute(getURL());

                jsonObject = asyncTask.get();
                JSONArray jsonArray = jsonObject.getJSONObject(getKey()).getJSONArray(getKey().substring(0, getKey().length() - 1));

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                    //Location
                    latLng = new Utils().getLatLng(jsonObject);

                    //Title
                    title = new Utils().getTitle(jsonObject);

                    //URL
                    urlForMarker = new Utils().getURL(jsonObject);

                    mapItems.add(new MapItem(title, latLng, getMarker(), urlForMarker));

                }
            } catch (InterruptedException e) {
                ExceptionDialogBuilder.createExceptionDialog(getContext(), e.getMessage()).show();
            } catch (ExecutionException e) {
                ExceptionDialogBuilder.createExceptionDialog(getContext(), e.getMessage()).show();
            } catch (JSONException e) {
                ExceptionDialogBuilder.createExceptionDialog(getContext(), e.getMessage()).show();
            } catch (Exception e) {
                ExceptionDialogBuilder.createExceptionDialog(getContext(), e.getMessage()).show();
            }
        }

        return mapItems;
    }
}
