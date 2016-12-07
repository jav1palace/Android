package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class MentalHealthSource extends JSONResource {

    public MentalHealthSource() {

    }

    public MentalHealthSource(Context context, ProgressDialog progressDialog) {
        new MentalHealthSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public MentalHealthSource(Context context) {
        new MentalHealthSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return standardExecute("http://datos.gijon.es/doc/salud/centros-salud-mental.json");
    }
}
