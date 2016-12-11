package com.example.javierpalaciocuenca.app.ui.activities.utils;

import android.app.Application;

/**
 * Created by javierpalaciocuenca on 08/12/2016.
 */

public class ActivityStatus extends Application {

    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

}
