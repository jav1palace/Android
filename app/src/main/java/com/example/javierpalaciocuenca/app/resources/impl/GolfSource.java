package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class GolfSource extends JSONResource {
    private static final String TAG = "GolfSource";

    public GolfSource() {
        setURL(URLConstants.GOLF_SOURCE_URL);
    }

    public GolfSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public GolfSource(Context context) {
        new GolfSource(context, null);
    }
}
