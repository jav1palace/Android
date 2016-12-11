package com.example.javierpalaciocuenca.app.utils;

import com.example.javierpalaciocuenca.app.persistence.model.MapItem;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 10/12/2016.
 */
public class MapItemsData {
    private static MapItemsData ourInstance = new MapItemsData();

    public static MapItemsData getInstance() {
        return ourInstance;
    }

    public List<MapItem> getMapItems() {
        return mapItems;
    }

    public void setMapItems(List<MapItem> mapItems) {
        this.mapItems = mapItems;
    }

    private List<MapItem> mapItems;
    private MapItemsData() {
    }
}
