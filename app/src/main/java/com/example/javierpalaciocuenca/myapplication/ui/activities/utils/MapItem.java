package com.example.javierpalaciocuenca.myapplication.ui.activities.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class MapItem implements Parcelable {

    public static final Parcelable.Creator<MapItem> CREATOR = new Parcelable.Creator<MapItem>() {
        public MapItem createFromParcel(Parcel in) {
            String title = in.readString();
            String url = in.readString();
            Double latitude = in.readDouble();
            Double longitude = in.readDouble();
            Integer marker = in.readInt();

            LatLng latLng = null;
            if (latitude != 0 && longitude != 0) {
                latLng = new LatLng(latitude, longitude);
            }

            return new MapItem(title, latLng, marker, url);
        }

        public MapItem[] newArray(int size) {
            return new MapItem[size];
        }
    };
    private static final String TAG = "MapItem";

    private LatLng latLng;
    private String title, url;
    private Integer marker;

    public MapItem(String title, LatLng latLng, Integer marker){
        this.title = title;
        this.latLng = latLng;
        this.marker = marker;
    }

    public MapItem(String title, LatLng latLng, Integer marker, String url) {
        this(title, latLng, marker);
        this.url = url;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public Integer getMarker() {
        return marker;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setMarker(Integer marker) {
        this.marker = marker;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(title);
        out.writeString(url);

        if (latLng != null) {
            out.writeDouble(latLng.latitude);
            out.writeDouble(latLng.longitude);
        } else {
            out.writeDouble(0);
            out.writeDouble(0);
        }

        out.writeInt(marker);
    }
}