package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;
import com.example.javierpalaciocuenca.myapplication.utils.URLConstants;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class ATMSource extends JSONResource {
    private static final String TAG = "ATMSource";

    public ATMSource() {
        setIcon(R.drawable.atm_icon);
        setMarker(R.drawable.atm_marker); // Size has to be 25x25
        setURL(URLConstants.ATM_SOURCE_URL);
    }

    public ATMSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public ATMSource(Context context) {
        new ATMSource(context, null);
    }
}
