package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class RecreationalAreaSource extends JSONResource {
    private static final String TAG = "RecreationalAreaSource";

    public RecreationalAreaSource() {
        setURL(URLConstants.RECREATIONAL_AREA_SOURCE_URL);
    }

    public RecreationalAreaSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public RecreationalAreaSource(Context context) {
        new RecreationalAreaSource(context, null);
    }
}
