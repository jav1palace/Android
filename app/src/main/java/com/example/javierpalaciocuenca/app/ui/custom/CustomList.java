package com.example.javierpalaciocuenca.app.ui.custom;

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

import com.example.javierpalaciocuenca.app.resources.JSONResource;
import com.example.javierpalaciocuenca.app.utils.ExceptionDialogBuilder;
import com.example.javierpalaciocuenca.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomList extends ArrayAdapter<String>{
    private static final String TAG = "CustomList";

    private final Activity context;
    private final ArrayList<String> items;
    private final HashMap<String, Class> imageMap;

    public CustomList(Activity context, HashMap<String, Class> items) {
        super(context, R.layout.single_item, new ArrayList<>(items.keySet()));
        this.context = context;
        this.items = new ArrayList<>(items.keySet());
        this.imageMap = items;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Integer icon = null;
        LayoutInflater inflater = this.context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_item, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        try {
            Class classToInit = this.imageMap.get(items.get(position));
            if (classToInit != null) {
                JSONResource jsonResource = (JSONResource) classToInit.newInstance();
                icon = jsonResource.getIcon();
            }

            /* Set the default image in case it's not implemented */
            if (icon != null) {
                imageView.setImageResource(icon);
            } else {
                imageView.setImageResource(R.drawable.empty);
            }

            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
            txtTitle.setText(this.items.get(position));

        } catch (InstantiationException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        } catch (IllegalAccessException e) {
            ExceptionDialogBuilder.createExceptionDialog(this.context, e.getMessage()).show();
        }

        return rowView;
    }
}