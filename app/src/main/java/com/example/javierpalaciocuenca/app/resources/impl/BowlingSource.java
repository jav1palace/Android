package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;
import com.example.javierpalaciocuenca.myapplication.R;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class BowlingSource extends JSONResource {
    private static final String TAG = "BowlingSource";

    public BowlingSource() {
        setIcon(R.drawable.bowling_icon);
        setMarker(R.drawable.bowling_marker); // Size has to be 25x25
        setURL(URLConstants.BOWLING_SOURCE_URL);
    }

    public BowlingSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public BowlingSource(Context context) {
        new BowlingSource(context, null);
    }
}
