package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class RecreationalAreaSource extends JSONResource {

    public RecreationalAreaSource() {

    }

    public RecreationalAreaSource(Context context, ProgressDialog progressDialog) {
        new RecreationalAreaSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public RecreationalAreaSource(Context context) {
        new RecreationalAreaSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return standardExecute("http://datos.gijon.es/doc/medio-ambiente/areas-recreativas.json");
    }
}
