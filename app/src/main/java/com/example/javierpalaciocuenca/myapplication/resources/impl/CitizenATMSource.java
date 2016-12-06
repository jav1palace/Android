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

public class CitizenATMSource extends JSONResource {

    public CitizenATMSource() {
        setIcon(R.drawable.atm_citizen_icon);
        setMarker(R.drawable.atm_citizen_marker); // Size has to be 25x25
    }

    public CitizenATMSource(Context context, ProgressDialog progressDialog) {
        new CitizenATMSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public CitizenATMSource(Context context) {
        new CitizenATMSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return standardExecute("http://opendata.gijon.es/descargar.php?id=7&tipo=JSON");
    }
}
