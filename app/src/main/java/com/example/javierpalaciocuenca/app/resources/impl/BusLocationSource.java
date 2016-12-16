package com.example.javierpalaciocuenca.app.resources.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.javierpalaciocuenca.app.persistence.model.BusStop;
import com.example.javierpalaciocuenca.app.persistence.model.MapItem;
import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.resources.utils.JSONReader;
import com.example.javierpalaciocuenca.app.resources.utils.JSONResourceUtils;
import com.example.javierpalaciocuenca.app.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.app.utils.constants.Constants;
import com.example.javierpalaciocuenca.app.utils.constants.URLConstants;
import com.example.javierpalaciocuenca.myapplication.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class BusLocationSource extends JSONResource {
    private static final String TAG = "BusLocationSource";

    public BusLocationSource() {
        setURL(URLConstants.BUS_LOCATION_SOURCE_URL);
        setPluralKey(Constants.JSON_BUS_LOCATION_NAME_PLURAL);
        setSingularKey(Constants.JSON_BUS_LOCATION_NAME_SINGULAR);
        setMarker(R.drawable.buslocation_marker);
        setIcon(R.drawable.buslocation_icon);
    }

    public BusLocationSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    private boolean isBusHome(MapItem mapItem) {
        LatLng latLng = mapItem.getLatLng();
        return latLng.latitude > 43.531297 && latLng.longitude > -5.704565 &&
                latLng.latitude < 43.535175 && latLng.longitude < -5.699263;
    }

    private List<MapItem> clearBusesHome(List<MapItem> mapItems) {
        ArrayList<MapItem> mapItemsReturn = new ArrayList<>();
        for (MapItem mapItem : mapItems) {
            if (!isBusHome(mapItem)) {
                mapItemsReturn.add(mapItem);
            }
        }
        return mapItemsReturn;
    }

    private List<BusStop> geBusStopsFromMapItems(List<MapItem> mapItems) {
        ArrayList<BusStop> busStops = new ArrayList<>();
        for (MapItem mapItem : mapItems) {
            busStops.add(mapItem.getBusStop());
        }

        return busStops;
    }

    public BusLocationSource(Context context) {
        new BusLocationSource(context, null);
    }

    @Override
    public List<MapItem> execute() {
        JSONObject jsonObject;
        List<MapItem> mapItems = new ArrayList<>();

        if (!getURL().equals(URLConstants.DEFAULT_SOURCE_URL)) {

            try {
                AsyncTask<String, Void, JSONObject> asyncTask = new JSONReader(this.context, this.progressDialog).execute(getURL());
                jsonObject = asyncTask.get();

                List<BusStop> latestBusStops = JSONResourceUtils.getBusStops(jsonObject, getPluralKey(), getSingularKey());
                mapItems = JSONResourceUtils.createMapItemsFromBusStops(latestBusStops, getMarker());
                mapItems = clearBusesHome(mapItems);
                latestBusStops = geBusStopsFromMapItems(mapItems);

                mapItems.addAll(JSONResourceUtils.createMapItemsFromBusStops(
                        JSONResourceUtils.createPreviousAndNextBusStop(latestBusStops, context), new BusStopSource().getMarker(), true));

            } catch (InterruptedException e) {
                ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
            } catch (ExecutionException e) {
                ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
            } catch (JSONException e) {
                ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
            } catch (Exception e) {
                ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
            }
        }

        return mapItems;
    }
}
