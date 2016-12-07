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

public class CampingSource extends JSONResource {

    public CampingSource() {

    }

    public CampingSource(Context context, ProgressDialog progressDialog) {
        new CampingSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public CampingSource(Context context) {
        new CampingSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return standardExecute("http://datos.gijon.es/doc/turismo/camping.json");
    }
}
