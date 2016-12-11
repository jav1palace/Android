package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class CampingSource extends JSONResource {
    private static final String TAG = "CampingSource";

    public CampingSource() {
        setURL(URLConstants.CAMPING_SOURCE_URL);
    }

    public CampingSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public CampingSource(Context context) {
        new CampingSource(context, null);
    }
}
