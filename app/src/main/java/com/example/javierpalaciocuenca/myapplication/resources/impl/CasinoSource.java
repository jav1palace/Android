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

public class CasinoSource extends JSONResource {

    public CasinoSource() {
        //setIcon(R.drawable.atm_icon);
        //setMarker(R.drawable.atm_marker); // Size has to be 25x25
    }

    public CasinoSource(Context context, ProgressDialog progressDialog) {
        new CasinoSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public CasinoSource(Context context) {
        new CasinoSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return standardExecute("http://datos.gijon.es/doc/turismo/casinos.json");
    }
}
