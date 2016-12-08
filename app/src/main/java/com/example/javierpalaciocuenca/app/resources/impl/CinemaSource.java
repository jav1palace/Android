package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class CinemaSource extends JSONResource {
    private static final String TAG = "CinemaSource";

    public CinemaSource() {
        setURL(URLConstants.CINEMA_SOURCE_URL);
    }

    public CinemaSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public CinemaSource(Context context) {
        new CinemaSource(context, null);
    }
}
