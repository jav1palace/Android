package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class HealthCentreSource extends JSONResource {
    private static final String TAG = "HealthCentreSource";

    public HealthCentreSource() {
        setURL(URLConstants.HEALTH_CENTRE_SOURCE_URL);
    }

    public HealthCentreSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public HealthCentreSource(Context context) {
        new HealthCentreSource(context, null);
    }
}
