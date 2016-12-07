package com.example.javierpalaciocuenca.myapplication.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.javierpalaciocuenca.myapplication.R;
import com.example.javierpalaciocuenca.myapplication.resources.JSONResource;
import com.example.javierpalaciocuenca.myapplication.ui.activities.utils.MapItem;
import com.example.javierpalaciocuenca.myapplication.utils.Constants;
import com.example.javierpalaciocuenca.myapplication.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.myapplication.utils.JSONReader;
import com.example.javierpalaciocuenca.myapplication.utils.URLConstants;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class CitizenATMSource extends JSONResource {

    public CitizenATMSource() {
        setIcon(R.drawable.atm_citizen_icon);
        setMarker(R.drawable.atm_citizen_marker); // Size has to be 25x25
        setURL(URLConstants.CITIZEN_SOURCE_URL);
        setKey(Constants.JSON_CLOTHES_ARRAY_NAME_PLURAL);
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
