package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class CinemaSource extends JSONResource {

    public CinemaSource() {

    }

    public CinemaSource(Context context, ProgressDialog progressDialog) {
        new CinemaSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public CinemaSource(Context context) {
        new CinemaSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return standardExecute("http://datos.gijon.es/doc/cultura-ocio/cines.json");
    }
}
