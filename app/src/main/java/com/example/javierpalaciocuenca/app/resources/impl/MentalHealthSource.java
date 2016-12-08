package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class MentalHealthSource extends JSONResource {
    private static final String TAG = "MentalHealthSource";

    public MentalHealthSource() {
        setURL(URLConstants.MENTAL_HEALTH_SOURCE_URL);
    }

    public MentalHealthSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public MentalHealthSource(Context context) {
        new MentalHealthSource(context, null);
    }
}
