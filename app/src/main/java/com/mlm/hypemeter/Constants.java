package com.mlm.hypemeter;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by Martin on 7/15/2016.
 */
public class Constants {

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 20;

    public static final HashMap<String, LatLng> LANDMARKS = new HashMap<String, LatLng>();
    static {
        // Set Home
        LANDMARKS.put("Home", new LatLng(37.739427,-122.450648));

        // set work
        //LANDMARKS.put("Work", new LatLng(37.727717,-122.464170));

        // other test
        LANDMARKS.put("Zack", new LatLng(37.724097,-122.439213));
    }

    public static final HashMap<String, LatLng> WORK = new HashMap<String, LatLng>();
    static {

        //Set Work
        WORK.put("Work", new LatLng(37.727717, -122.464170));
    }
}
