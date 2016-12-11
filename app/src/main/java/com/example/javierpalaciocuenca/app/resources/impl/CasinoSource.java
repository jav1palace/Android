package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class CasinoSource extends JSONResource {
    private static final String TAG = "CasinoSource";

    public CasinoSource() {
        setURL(URLConstants.CASINO_SOURCE_URL);
    }

    public CasinoSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public CasinoSource(Context context) {
        new CasinoSource(context, null);
    }
}
