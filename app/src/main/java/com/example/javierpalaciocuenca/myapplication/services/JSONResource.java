package com.example.javierpalaciocuenca.myapplication.services;

import android.content.Context;
import android.widget.ProgressBar;

import com.example.javierpalaciocuenca.myapplication.utilities.MapItem;

import java.util.List;

/**
 * Created by javierpalaciocuenca on 27/11/2016.
 */

public abstract class JSONResource {
    protected Context context;
    protected ProgressBar progressBar;
    protected Integer icon;
    protected abstract List<MapItem> execute();

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

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
