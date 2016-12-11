package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;
import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.app.utils.constants.Constants;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class CitizenATMSource extends JSONResource {
    private static final String TAG = "CitizenATMSource";

    public CitizenATMSource() {
        setIcon(R.drawable.atm_citizen_icon);
        setMarker(R.drawable.atm_citizen_marker); // Size has to be 25x25
        setURL(URLConstants.CITIZEN_SOURCE_URL);
        setPluralKey(Constants.JSON_CLOTHES_ARRAY_NAME_PLURAL);
        setSingularKey(Constants.JSON_CLOTHES_ARRAY_NAME_SINGULAR);
    }

    public CitizenATMSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public CitizenATMSource(Context context) {
        new CitizenATMSource(context, null);
    }
}
