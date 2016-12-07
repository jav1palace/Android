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

public class BowlingSource extends JSONResource {

    public BowlingSource() {
        setIcon(R.drawable.bowling_icon);
        setMarker(R.drawable.bowling_marker); // Size has to be 25x25
    }

    public BowlingSource(Context context, ProgressDialog progressDialog) {
        new BowlingSource();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public BowlingSource(Context context) {
        new BowlingSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        return execute("http://datos.gijon.es/doc/deporte/boleras.json");
    }
}
