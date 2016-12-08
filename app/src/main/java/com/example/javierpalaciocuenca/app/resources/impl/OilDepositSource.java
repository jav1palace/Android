package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;
import com.example.javierpalaciocuenca.app.utils.constants.Constants;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class OilDepositSource extends JSONResource {
    private static final String TAG = "OilDepositSource";

    public OilDepositSource() {
        setURL(URLConstants.OIL_DEPOSIT_SOURCE_URL);
        setKey(Constants.JSON_OIL_ARRAY_NAME_PLURAL);
    }

    public OilDepositSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public OilDepositSource(Context context) {
        new OilDepositSource(context, null);
    }
}
