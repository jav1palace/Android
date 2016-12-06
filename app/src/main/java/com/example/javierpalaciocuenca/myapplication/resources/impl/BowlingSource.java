package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.myapplication.utils.JSONReader;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        return standardExecute("http://datos.gijon.es/doc/deporte/boleras.json");
    }
}
