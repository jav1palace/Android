package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class GolfSource extends JSONResource {

    public GolfSource() {

    }

    public GolfSource(Context context, ProgressDialog progressDialog) {
        new GolfSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public GolfSource(Context context) {
        new GolfSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return standardExecute("http://datos.gijon.es/doc/deporte/campos-golf.json");
    }
}
