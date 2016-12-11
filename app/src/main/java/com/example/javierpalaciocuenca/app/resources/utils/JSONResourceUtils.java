package com.example.javierpalaciocuenca.app.resources.utils;

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

    public static List<MapItem> getMapItems(JSONObject jsonObject, String key, Integer marker) throws JSONException {
        LatLng latLng;
        String title, urlForMarker;

        List<MapItem> mapItems = new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONObject(key).getJSONArray(key.substring(0, key.length() - 1));

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);

            latLng = getLatLng(jsonObject, key);
            title = getTitle(jsonObject, key);
            urlForMarker = getURL(jsonObject);

            mapItems.add(new MapItem(title, latLng, marker, urlForMarker));
        }

        return mapItems;
    }
}