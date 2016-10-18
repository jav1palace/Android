package com.example.javierpalaciocuenca.myapplication;

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

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;

    public CustomList(Activity context, String[] web) {
        super(context, R.layout.single_item, web);
        this.context = context;
        this.web = web;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_item, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        imageView.setImageResource( R.drawable.image1);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(web[position]);

        return rowView;
    }
}