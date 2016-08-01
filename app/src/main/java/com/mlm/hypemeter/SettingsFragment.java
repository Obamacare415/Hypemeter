package com.mlm.hypemeter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Martin on 7/13/2016.
 */
public class SettingsFragment extends Fragment
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status> {

    private View SettingsView;
    protected ArrayList<Geofence> mGeofenceList;
    protected GoogleApiClient mGoogleApiClient;
    public Button mAddGeofencesButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SettingsView = inflater.inflate(R.layout.fragment_settings, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");

        // Get the between instance stored values
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        // Set the values of the UI
        Switch switchCheckIn = (Switch) SettingsView.findViewById(R.id.notifycheckin_switch);
        switchCheckIn.setChecked(preferences.getBoolean("checkin", false));

        Switch switchHistory = (Switch) SettingsView.findViewById(R.id.savehistory_switch);
        switchHistory.setChecked(preferences.getBoolean("history", false));

        Switch switchSubs = (Switch) SettingsView.findViewById(R.id.confirmsubs);
        switchSubs.setChecked(preferences.getBoolean("subs", false));

        Switch switchPush = (Switch) SettingsView.findViewById(R.id.push_yournotif);
        switchPush.setChecked(preferences.getBoolean("push", false));
        // End of UI values

        mAddGeofencesButton = (Button) SettingsView.findViewById(R.id.sethomebutton);

        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<>();

        // Get the geofences used. Geofence data is hard coded in this sample.
        populateGeofenceList();

        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();

        //addGeofencesButtonHandler();

        Button mAddGeofencesButton = (Button) SettingsView.findViewById(R.id.sethomebutton);

        mAddGeofencesButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                if (!mGoogleApiClient.isConnected()) {
                    Toast.makeText(getActivity().getBaseContext(), "Google API Client not connected!", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    LocationServices.GeofencingApi.addGeofences(
                            mGoogleApiClient,
                            getGeofencingRequest(),
                            getGeofencePendingIntent());
                    //).setResultCallback(getActivity().getApplicationInfo()); // Result processed in onResult().
                } catch (SecurityException securityException) {
                    // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
                }

            }
        });

        return SettingsView;
    }

    // Get the geofences used. Geofence data is hard coded in this sample.
    public void populateGeofenceList() {
        for (Map.Entry<String, LatLng> entry : Constants.LANDMARKS.entrySet()) {
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Constants.GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }
        for (Map.Entry<String, LatLng> entry : Constants.WORK.entrySet()) {
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Constants.GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build());
        }
    }

    // Kick off the request to build GoogleApiClient.
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Do something with result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    /*public void addGeofencesButtonHandler() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this.getActivity(), "Google API Client not connected!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
        }
    }*/

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(this.getActivity(), GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling addgeoFences()
        return PendingIntent.getService(this.getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {
            Toast.makeText(
                    this.getActivity(),
                    "Geofences Added",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(
                    this.getActivity(),
                    "Geofence Error",
                    Toast.LENGTH_SHORT
            ).show();
                    status.getStatusCode();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Store values between instances here
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Put the values from the UI
        Switch switchCheckIn = (Switch) SettingsView.findViewById(R.id.notifycheckin_switch);
        boolean blnCheckIn = switchCheckIn.isChecked();

        Switch switchHistory = (Switch) SettingsView.findViewById(R.id.savehistory_switch);
        boolean blnHistory = switchHistory.isChecked();

        Switch switchSubs = (Switch) SettingsView.findViewById(R.id.confirmsubs);
        boolean blnSubs = switchSubs.isChecked();

        Switch switchPush = (Switch) SettingsView.findViewById(R.id.push_yournotif);
        boolean blnPush = switchPush.isChecked();

        editor.putBoolean("checkin", blnCheckIn); // stores check in switch value
        editor.putBoolean("history", blnHistory); // stores history switch value
        editor.putBoolean("subs", blnSubs); // stores subs switch value
        editor.putBoolean("push", blnPush); // stores push switch value

        // Commit to storage
        editor.apply();

    }
}
