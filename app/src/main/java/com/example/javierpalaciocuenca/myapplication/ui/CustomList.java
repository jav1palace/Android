package com.example.javierpalaciocuenca.myapplication.ui;

/**
 * Created by javierpalaciocuenca on 16/10/2016.
 */


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javierpalaciocuenca.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final ArrayList<String> items;
    private final HashMap<String, Integer> imageMap;

    public CustomList(Activity context, Set<String> items, HashMap<String, Integer> imageMap) {
        super(context, R.layout.single_item, new ArrayList<>(items));
        this.context = context;
        this.items = new ArrayList<>(items);
        this.imageMap = imageMap;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_item, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        Integer image = imageMap.get(items.get(position));

        if (image != null) {
            imageView.setImageResource(imageMap.get(items.get(position)));
        } else {
            imageView.setImageResource(R.drawable.empty);
        }

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(items.get(position));

        return rowView;
    }
}