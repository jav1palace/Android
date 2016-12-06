package com.example.javierpalaciocuenca.myapplication.services;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.myapplication.utilities.MapItem;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public abstract class JSONResource {
    protected Context context;
    protected ProgressDialog progressDialog;
    protected Integer icon, marker;
    protected abstract List<MapItem> execute();


    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setMarker(Integer marker) {
        this.marker = marker;
    }

    public void setProgressBar(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    public Integer getIcon() {
        return icon;
    }

    public Context getContext() {
        return context;
    }

    public Integer getMarker() {
        return marker;
    }

    public ProgressDialog getProgressBar() {
        return progressDialog;
    }


}
