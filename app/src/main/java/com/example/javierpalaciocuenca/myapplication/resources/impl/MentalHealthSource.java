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

public class MentalHealthSource extends JSONResource {

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
