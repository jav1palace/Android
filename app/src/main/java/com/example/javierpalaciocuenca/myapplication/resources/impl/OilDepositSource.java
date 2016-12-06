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

public class OilDepositSource extends JSONResource {

    public OilDepositSource() {

    }

    public OilDepositSource(Context context, ProgressDialog progressDialog) {
        new OilDepositSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public OilDepositSource(Context context) {
        new OilDepositSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return execute("http://opendata.gijon.es/descargar.php?id=6&tipo=JSON", "contenedoraceites");
    }
}
