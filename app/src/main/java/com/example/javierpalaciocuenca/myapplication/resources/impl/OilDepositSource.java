package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;
import com.example.javierpalaciocuenca.myapplication.utils.Constants;
import com.example.javierpalaciocuenca.myapplication.utils.URLConstants;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class OilDepositSource extends JSONResource {

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
