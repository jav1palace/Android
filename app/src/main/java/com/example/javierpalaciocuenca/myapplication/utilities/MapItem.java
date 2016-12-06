package com.example.javierpalaciocuenca.myapplication.utilities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public class MapItem implements Parcelable {
    public static final Parcelable.Creator<MapItem> CREATOR = new Parcelable.Creator<MapItem>() {
        public MapItem createFromParcel(Parcel in) {
            return new MapItem(in.readString(), new LatLng(in.readDouble(), in.readDouble()), in.readInt(), in.readString());
        }

        public MapItem[] newArray(int size) {
            return new MapItem[size];
        }
    };

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

    public void setUrl(String url) {
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
        out.writeDouble(latLng.latitude);
        out.writeDouble(latLng.longitude);
        out.writeInt(marker);
        out.writeString(url);
    }
}