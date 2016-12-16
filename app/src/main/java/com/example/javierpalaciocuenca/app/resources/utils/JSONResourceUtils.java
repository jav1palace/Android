package com.example.javierpalaciocuenca.app.resources.utils;

import android.content.Context;

import com.example.javierpalaciocuenca.app.persistence.MyDataBaseHelper;
import com.example.javierpalaciocuenca.app.persistence.model.BusStop;
import com.example.javierpalaciocuenca.app.persistence.model.MapItem;
import com.example.javierpalaciocuenca.app.utils.constants.Constants;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javierpalaciocuenca on 08/12/2016.
 */

public class JSONResourceUtils {
    private static LatLng getLatLng(JSONObject jsonObject, String key) throws JSONException {
        LatLng latLng;

        if (key.equals(Constants.JSON_DEFAULT_ARRAY_NAME_PLURAL)) {
            String[] location;
            JSONObject objectLocationContent;

            objectLocationContent = jsonObject.getJSONObject("localizacion");
            location = objectLocationContent.has("content") ? objectLocationContent.getString("content").split(" ") : null;
            latLng = location != null ? new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1])) : null;

        } else {
            latLng = new LatLng(jsonObject.getDouble("latitud"), jsonObject.getDouble("longitud"));
        }

        return latLng;
    }

    private static String getTitle(JSONObject jsonObject, String key) throws JSONException {
        String title;

        if (key.equals(Constants.JSON_DEFAULT_ARRAY_NAME_PLURAL)) {
            JSONObject objectTitle;

            objectTitle = jsonObject.getJSONObject("nombre");
            title = objectTitle.has("content") ? objectTitle.getString("content") : null;

        } else {
            title = jsonObject.getString("lugar");
        }

        return title;
    }

    private static String getURL(JSONObject jsonObject) throws JSONException {
        String urlObject = jsonObject.has("url") ? jsonObject.getString("url") : null;

        return urlObject;
    }

    public static List<MapItem> getMapItems(JSONObject jsonObject, String pluralKey, String singularKey, Integer marker) throws JSONException {
        LatLng latLng;
        String title, urlForMarker;

        List<MapItem> mapItems = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONObject(pluralKey).getJSONArray(singularKey);

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);

            latLng = getLatLng(jsonObject, pluralKey);
            title = getTitle(jsonObject, pluralKey);
            urlForMarker = getURL(jsonObject);

            mapItems.add(new MapItem(title, latLng, marker, urlForMarker));
        }

        return mapItems;
    }

    public static List<BusStop> getBusStops(JSONObject jsonObject, String pluralKey, String singularKey) throws JSONException {
        List<BusStop> busStops = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONObject(pluralKey).getJSONArray(singularKey);
        Integer idLinea, idTrayecto, orden;
        Double utmx, utmy;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            orden = jsonObject.has("orden") ? jsonObject.getInt("orden") : 0;
            idLinea = jsonObject.getInt("idlinea");
            idTrayecto = jsonObject.getInt("idtrayecto");
            utmx = jsonObject.getDouble("utmx");
            utmy = jsonObject.getDouble("utmy");

            busStops.add(new BusStop(0, orden, idLinea, idTrayecto, utmx, utmy));

        }

        return busStops;
    }

    public static List<BusStop> createAllBusStops(List<BusStop> busStops, Context context) {
        List<BusStop> allBusStops = new ArrayList<>();

        MyDataBaseHelper dataBaseHelper = MyDataBaseHelper.getInstance(context);

        allBusStops.addAll(busStops);
        for (BusStop busStop : busStops) {
            //   allBusStops.addAll(dataBaseHelper.getBusStops(busStop.getIdLinea(), busStop.getIdTrayecto()));
        }

        return allBusStops;
    }

    private static LatLng createLatLngFromUTM(Double utmx, Double utmy) {
        if (utmy == 0 && utmx == 0) {
            return null;
        }
        double a = 6378137;
        double e = 0.081819191;
        double e1sq = 0.006739497;
        double k0 = 0.9996;

        double arc = utmy / k0;
        double mu = arc / (a * (1 - Math.pow(e, 2) / 4.0 - 3 * Math.pow(e, 4) / 64.0 - 5 * Math.pow(e, 6) / 256.0));

        double ei = (1 - Math.pow((1 - e * e), (1 / 2.0))) / (1 + Math.pow((1 - e * e), (1 / 2.0)));

        double ca = 3 * ei / 2 - 27 * Math.pow(ei, 3) / 32.0;

        double cb = 21 * Math.pow(ei, 2) / 16 - 55 * Math.pow(ei, 4) / 32;
        double cc = 151 * Math.pow(ei, 3) / 96;
        double cd = 1097 * Math.pow(ei, 4) / 512;
        double phi1 = mu + ca * Math.sin(2 * mu) + cb * Math.sin(4 * mu) + cc * Math.sin(6 * mu) + cd * Math.sin(8 * mu);

        double n0 = a / Math.pow((1 - Math.pow((e * Math.sin(phi1)), 2)), (1 / 2.0));

        double r0 = a * (1 - e * e) / Math.pow((1 - Math.pow((e * Math.sin(phi1)), 2)), (3 / 2.0));
        double fact1 = n0 * Math.tan(phi1) / r0;

        double _a1 = 500000 - utmx;
        double dd0 = _a1 / (n0 * k0);
        double fact2 = dd0 * dd0 / 2;

        double t0 = Math.pow(Math.tan(phi1), 2);
        double Q0 = e1sq * Math.pow(Math.cos(phi1), 2);
        double fact3 = (5 + 3 * t0 + 10 * Q0 - 4 * Q0 * Q0 - 9 * e1sq) * Math.pow(dd0, 4) / 24;

        double fact4 = (61 + 90 * t0 + 298 * Q0 + 45 * t0 * t0 - 252 * e1sq - 3 * Q0 * Q0) * Math.pow(dd0, 6) / 720;

        double lof1 = _a1 / (n0 * k0);
        double lof2 = (1 + 2 * t0 + Q0) * Math.pow(dd0, 3) / 6.0;
        double lof3 = (5 - 2 * Q0 + 28 * t0 - 3 * Math.pow(Q0, 2) + 8 * e1sq + 24 * Math.pow(t0, 2)) * Math.pow(dd0, 5) / 120;
        double _a2 = (lof1 - lof2 + lof3) / Math.cos(phi1);
        double _a3 = _a2 * 180 / Math.PI;

        double latitude = 180 * (phi1 - fact1 * (fact2 + fact3 + fact4)) / Math.PI;

        double zone = 30;
        double longitude = ((6 * zone - 183.0)) - _a3;

        return new LatLng(latitude, longitude);
    }

    public static List<MapItem> createMapItemsFromBusStops(List<BusStop> busStops, Integer marker, boolean isBusStop) {
        List<MapItem> mapItems = new ArrayList<>();
        MapItem mapItem;
        String title;
        for (BusStop busStop : busStops) {
            LatLng latLng = createLatLngFromUTM(busStop.getUtmx(), busStop.getUtmy());
            if (latLng != null) {
                title = String.valueOf(busStop.getIdLinea());
                title = isBusStop ? "P" + title : title;
                mapItem = new MapItem(title, latLng, marker);
                mapItem.setBusStop(busStop);
                mapItems.add(mapItem);
            }
        }

        return mapItems;
    }

    //TODO: CHANGE BASIC MARKER FOR A CUSTOM MARKER DEPENDING ON THE Nº DE LINEA
    public static List<MapItem> createMapItemsFromBusStops(List<BusStop> busStops, Integer marker) {
        return createMapItemsFromBusStops(busStops, marker, false);
    }

    public static List<BusStop> createPreviousAndNextBusStop(List<BusStop> busStops, Context context) {
        MyDataBaseHelper dbHelper = MyDataBaseHelper.getInstance(context);
        ArrayList<BusStop> busStopsReturn = new ArrayList<>();
        BusStop busStopToAdd;
        for (BusStop busStop : busStops) {
            busStopToAdd = dbHelper.getBusStop(busStop.getOrden(), busStop.getIdLinea(), busStop.getIdTrayecto());
            if (busStopToAdd != null) {
                busStopsReturn.add(busStopToAdd);
            }
        }

        return busStopsReturn;
    }
}