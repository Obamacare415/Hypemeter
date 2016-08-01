package com.mlm.hypemeter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.Switch;


/**
 * Created by Martin on 2/15/2016.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        // Get the between instance stored values
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        // Set the values of the UI
        Switch switchCheckIn = (Switch)findViewById(R.id.notifycheckin_switch);
        switchCheckIn.setChecked(preferences.getBoolean("checkin", false));

        Switch switchHistory = (Switch)findViewById(R.id.savehistory_switch);
        switchHistory.setChecked(preferences.getBoolean("history", false));

        Switch switchSubs = (Switch)findViewById(R.id.confirmsubs);
        switchSubs.setChecked(preferences.getBoolean("subs", false));

        Switch switchPush = (Switch)findViewById(R.id.push_yournotif);
        switchPush.setChecked(preferences.getBoolean("push", false));
        // End of UI values
    }

    @Override
    public void onPause()
    {
        super.onPause();

        // Store values between instances here
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Put the values from the UI
        Switch switchCheckIn = (Switch)findViewById(R.id.notifycheckin_switch);
        boolean blnCheckIn = switchCheckIn.isChecked();

        Switch switchHistory = (Switch)findViewById(R.id.savehistory_switch);
        boolean blnHistory = switchHistory.isChecked();

        Switch switchSubs = (Switch)findViewById(R.id.confirmsubs);
        boolean blnSubs = switchSubs.isChecked();

        Switch switchPush = (Switch)findViewById(R.id.push_yournotif);
        boolean blnPush = switchPush.isChecked();

        editor.putBoolean("checkin", blnCheckIn); // stores check in switch value
        editor.putBoolean("history", blnHistory); // stores history switch value
        editor.putBoolean("subs", blnSubs); // stores subs switch value
        editor.putBoolean("push", blnPush); // stores push switch value

        // Commit to storage
        editor.apply();

    }
}
