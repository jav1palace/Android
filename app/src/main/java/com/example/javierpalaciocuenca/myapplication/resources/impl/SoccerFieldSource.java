package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;
import com.example.javierpalaciocuenca.myapplication.utils.URLConstants;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class SoccerFieldSource extends JSONResource {
    private static final String TAG = "SoccerFieldSource";

    public SoccerFieldSource() {
        setURL(URLConstants.SOCCER_FIELD_SOURCE_URL);
    }

    public SoccerFieldSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public SoccerFieldSource(Context context) {
        new SoccerFieldSource(context, null);
    }
}
