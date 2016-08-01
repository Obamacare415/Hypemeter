package com.mlm.hypemeter;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.hypemeter.logger.Log;
import com.hypemeter.logger.LogWrapper;
/**
 * Created by Martin on 2/24/2016.
 */
public class ActivityBase extends FragmentActivity {

    public static final String TAG = "ActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
    }

    /** Set up targets to receive log data */
    public void initializeLogging() {
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);

        Log.i(TAG, "Ready");
    }
}
