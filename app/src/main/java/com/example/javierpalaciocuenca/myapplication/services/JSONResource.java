package com.example.javierpalaciocuenca.myapplication.services;

import android.content.Context;
import android.widget.ProgressBar;

import java.util.List;

import com.example.javierpalaciocuenca.myapplication.utilities.MapItem;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public abstract class JSONResource {
    protected Context context;
    protected ProgressBar progressBar;
    protected abstract List<MapItem> execute();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
