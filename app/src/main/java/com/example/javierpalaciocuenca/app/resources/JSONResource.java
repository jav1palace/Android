package com.example.javierpalaciocuenca.app.resources;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.javierpalaciocuenca.app.persistence.model.BusStop;
import com.example.javierpalaciocuenca.app.persistence.model.MapItem;
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

public abstract class JSONResource {
    private static final String TAG = "JSONResource";

    protected Context context;
    protected ProgressDialog progressDialog;
    protected Integer icon = R.drawable.default_icon;
    protected Integer marker = R.drawable.default_marker;
    protected String URL = URLConstants.DEFAULT_SOURCE_URL;
    protected String pluralKey = Constants.JSON_DEFAULT_ARRAY_NAME_PLURAL;
    protected String singularKey = Constants.JSON_DEFAULT_ARRAY_NAME_SINGULAR;

    protected String getPluralKey() {
        return pluralKey;
    }

    protected void setPluralKey(String pluralKey) {
        this.pluralKey = pluralKey;
    }

    protected String getSingularKey() {
        return singularKey;
    }

    protected void setSingularKey(String singularKey) {
        this.singularKey = singularKey;
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

    public Integer getMarker() {
        return marker;
    }

    ProgressDialog getProgressDialog() {
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
        JSONObject jsonObject;
        List<MapItem> mapItems = new ArrayList<>();

        if (!getURL().equals(URLConstants.DEFAULT_SOURCE_URL)) {

            try {
                AsyncTask<String, Void, JSONObject> asyncTask = new JSONReader(getContext(), getProgressDialog()).execute(getURL());
                jsonObject = asyncTask.get();

                mapItems = JSONResourceUtils.getMapItems(jsonObject, getPluralKey(), getSingularKey(), getMarker());
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
