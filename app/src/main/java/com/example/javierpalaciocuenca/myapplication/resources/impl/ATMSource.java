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

public class ATMSource extends JSONResource {

    public ATMSource() {
        setIcon(R.drawable.atm_icon);
        setMarker(R.drawable.atm_marker); // Size has to be 25x25
    }

    public ATMSource(Context context, ProgressDialog progressDialog) {
        new ATMSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public ATMSource(Context context) {
        new ATMSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return execute("http://datos.gijon.es/doc/informacion/bancos-cajeros.json");
    }
}
