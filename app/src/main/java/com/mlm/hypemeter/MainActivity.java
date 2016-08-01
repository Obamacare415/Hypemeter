package com.mlm.hypemeter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hypemeter.logger.LogWrapper;

import android.content.res.Resources;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        com.google.android.gms.location.LocationListener,
        LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    double mLatitude = 0;
    double mLongitude = 0;
    private LocationManager locationManager;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FloatingActionButton fab;
    private boolean mUserSawDrawer = false;
    public static final String FIRST_TIME = "first_time";

    private int otherIcon;
    private Marker[] placeMarkers;
    private Marker placeZoom;
    private final int MAX_PLACES = 15; //20 = most returned from google
    private MarkerOptions[] places;
    private boolean updateFinished = true;
    private int PROXIMITY_RADIUS = 3218;

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    //private TextView mPlaceDetailsText;
    //private TextView mPlaceAttribution;
    public static final String TAG = "ActivityBase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildGoogleApiClient();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (!didUserSeeDrawer()) {
            showDrawer();
            markDrawerSeen();
        } else {
            hideDrawer();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                openAutocompleteActivity();
            }
        });

        otherIcon = R.drawable.hotspot_small;
        placeMarkers = new Marker[MAX_PLACES];

        // Getting LocationManager object from System Service LOCATION_SERVICE
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 0, this);

    }

    private boolean didUserSeeDrawer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = sharedPreferences.getBoolean(FIRST_TIME, false);

        return mUserSawDrawer;
    }

    private void markDrawerSeen() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = true;
        sharedPreferences.edit().putBoolean(FIRST_TIME, mUserSawDrawer).apply();
    }

    private void showDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    private void hideDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setPadding(0, 200, 0, 50);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);

        locationManager.getLastKnownLocation(provider);

        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false); // Enable / Disable zooming controls
        mMap.getUiSettings().setCompassEnabled(false); // Enable / Disable Compass icon
        mMap.getUiSettings().setRotateGesturesEnabled(false); // Enable / Disable Rotate gesture
        mMap.getUiSettings().setZoomGesturesEnabled(true); // Enable / Disable zooming functionality

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 14));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                .zoom(14)                   // Sets the zoom, higher # = more zoom
                .bearing(0)                // Sets the orientation of the camera to north, value in degrees
                .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

    }

    public void onLocationChanged(Location location) {

        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        updatePlaces();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.v("MyMapActivity", "provider disabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.v("MyMapActivity", "provider enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.v("MyMapActivity", "status changed");
    }

    /*
     * update the place markers
     */
    private void updatePlaces() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location lastLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double lat = lastLoc.getLatitude();
        double lng = lastLoc.getLongitude();

        //placeText = (EditText) findViewById(R.id.placeText);

        //build places query string
        //String type = placeText.getText().toString();
        String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=" + lat + "," + lng +
                "&radius=" + PROXIMITY_RADIUS +
                "&sensor=true" +
                "&type=bar" +
                "&opennow" +
                "&rankby" +
                "&key=AIzaSyCP7lWNLHJBJlgcRzmr4jntMoz-o-AbMlg"; //ADD KEY

        new GetPlaces().execute(placesSearchStr);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class GetPlaces extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... placesURL) {
            //fetch places
            updateFinished = false;
            StringBuilder placesBuilder = new StringBuilder();
            for (String placeSearchURL : placesURL) {
                try {

                    URL requestUrl = new URL(placeSearchURL);
                    HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        BufferedReader reader = null;

                        InputStream inputStream = connection.getInputStream();
                        if (inputStream == null) {
                            return "";
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));

                        String line;
                        while ((line = reader.readLine()) != null) {

                            placesBuilder.append(line + "\n");
                        }

                        if (placesBuilder.length() == 0) {
                            return "";
                        }

                        Log.d("test", placesBuilder.toString());
                    } else {
                        Log.i("test", "Unsuccessful HTTP Response Code: " + responseCode);
                    }
                } catch (MalformedURLException e) {
                    Log.e("test", "Error processing Places API URL", e);
                } catch (IOException e) {
                    Log.e("test", "Error connecting to Places API", e);
                }
            }
            return placesBuilder.toString();
        }

        //process data retrieved from doInBackground
        protected void onPostExecute(String result) {
            //parse place data returned from Google Places
            //remove existing markers
            if (placeMarkers != null) {
                for (int pm = 0; pm < placeMarkers.length; pm++) {
                    if (placeMarkers[pm] != null)
                        placeMarkers[pm].remove();
                }
            }
            try {
                //create JSONObject, pass stinrg returned from doInBackground
                JSONObject resultObject = new JSONObject(result);
                //get "results" array
                JSONArray placesArray = resultObject.getJSONArray("results");
                //marker options for each place returned
                places = new MarkerOptions[placesArray.length()];
                //loop through places

                Log.d("test", "The placesArray length is " + placesArray.length() + "...............");

                for (int p = 0; p < placesArray.length(); p++) {
                    //parse each place
                    //if any values are missing we won't show the marker
                    boolean missingValue = false;
                    LatLng placeLL = null;
                    String placeName = "";
                    String vicinity = "";
                    int currIcon = otherIcon;

                    try {
                        //attempt to retrieve place data values
                        missingValue = false;
                        //get place at this index
                        JSONObject placeObject = placesArray.getJSONObject(p);
                        //get location section
                        JSONObject loc = placeObject.getJSONObject("geometry")
                                .getJSONObject("location");
                        //read lat lng
                        placeLL = new LatLng(Double.valueOf(loc.getString("lat")),
                                Double.valueOf(loc.getString("lng")));
                        //get types
                        JSONArray types = placeObject.getJSONArray("types");

                        //vicinity
                        vicinity = placeObject.getString("vicinity");
                        //name
                        placeName = placeObject.getString("name");

                        //loop through types
                        for (int t = 0; t < types.length(); t++) {
                            //what type is it
                            String thisType = types.get(t).toString();

                            Drawable circleTrans = getDrawable(R.drawable.hotspot_transparent);
                            Canvas canvasTrans = new Canvas();
                            Bitmap bitmapTrans = Bitmap.createBitmap(circleTrans.getIntrinsicWidth(), circleTrans.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                            canvasTrans.setBitmap(bitmapTrans);
                            circleTrans.setBounds(0, 0, circleTrans.getIntrinsicWidth(), circleTrans.getIntrinsicHeight());
                            circleTrans.draw(canvasTrans);
                            BitmapDescriptor bdTrans = BitmapDescriptorFactory.fromBitmap(bitmapTrans);

                            Drawable circleSmall = getDrawable(R.drawable.hotspot_small);
                            Canvas canvasSmall = new Canvas();
                            Bitmap bitmapSmall = Bitmap.createBitmap(circleSmall.getIntrinsicWidth(), circleSmall.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                            canvasSmall.setBitmap(bitmapSmall);
                            circleSmall.setBounds(0, 0, circleSmall.getIntrinsicWidth(), circleSmall.getIntrinsicHeight());
                            circleSmall.draw(canvasSmall);
                            BitmapDescriptor bdSmall = BitmapDescriptorFactory.fromBitmap(bitmapSmall);
                            //Animation hotspot1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.hotspot_anim);

                            Drawable circleMed = getDrawable(R.drawable.hotspot_med);
                            Canvas canvasMed = new Canvas();
                            Bitmap bitmapMed = Bitmap.createBitmap(circleMed.getIntrinsicWidth(), circleMed.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                            canvasMed.setBitmap(bitmapMed);
                            circleMed.setBounds(0, 0, circleMed.getIntrinsicWidth(), circleMed.getIntrinsicHeight());
                            circleMed.draw(canvasMed);
                            BitmapDescriptor bdMed = BitmapDescriptorFactory.fromBitmap(bitmapMed);

                            Drawable circleLarge = getDrawable(R.drawable.hotspot_large);
                            Canvas canvasLarge = new Canvas();
                            Bitmap bitmapLarge = Bitmap.createBitmap(circleLarge.getIntrinsicWidth(), circleLarge.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                            canvasLarge.setBitmap(bitmapLarge);
                            circleLarge.setBounds(0, 0, circleLarge.getIntrinsicWidth(), circleLarge.getIntrinsicHeight());
                            circleLarge.draw(canvasLarge);
                            BitmapDescriptor bdLarge = BitmapDescriptorFactory.fromBitmap(bitmapLarge);

                            //if values missing we don't display
                            if (missingValue) places[p] = null;

                            else
                                places[p] = new MarkerOptions()
                                        .position(placeLL)
                                        .title(placeName)
                                        .snippet(vicinity)
                                        .icon(bdTrans);

                            //check for particular types - set icons
                            if (thisType.contains("restaurant")) {
                                //currIcon = hotspotSml;
                                mMap.addMarker(new MarkerOptions()
                                        .position(placeLL)
                                        .title(placeName)
                                        .snippet(vicinity)
                                        .icon(bdSmall));
                                //circleSmall.startAnimation(hotspot1);
                                break;
                            } else if (thisType.contains("bar")) {

                                mMap.addMarker(new MarkerOptions()
                                        .position(placeLL)
                                        .title(placeName)
                                        .snippet(vicinity)
                                        .icon(bdMed));
                                break;
                            } else if (thisType.contains("night_club")) {

                                mMap.addMarker(new MarkerOptions()
                                        .position(placeLL)
                                        .title(placeName)
                                        .snippet(vicinity)
                                        .icon(bdLarge));
                                break;
                            }
                        }

                    } catch (JSONException jse) {
                        Log.v("PLACES", "missing value");
                        missingValue = true;
                        jse.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (places != null && placeMarkers != null) {
                Log.d("test", "The placeMarkers length is " + placeMarkers.length + "...............");

                for (int p = 0; p < places.length && p < placeMarkers.length; p++) {
                    //will be null if a value was missing

                    if (places[p] != null) {

                        placeMarkers[p] = mMap.addMarker(places[p]);
                    }
                }
            }

            final List<Marker> markerList = new ArrayList<>();

            //markerList.add(placeMarkers);
            // Pan to see all markers in view.
            // Cannot zoom to bounds until the map has a size
            final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 100, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.removeUpdates(this);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), FiltersActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_nearby) {
                // Handle the action
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                return (true);

            } else if (id == R.id.nav_rankings) {

                Fragment navRankings = new RankingsFragment();
                FragmentTransaction ftRankings = getSupportFragmentManager().beginTransaction();
                ftRankings.replace(R.id.activity_maps, navRankings);
                ftRankings.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ftRankings.addToBackStack(null);
                ftRankings.commit();

                fab.setVisibility(View.GONE);

            } else if (id == R.id.nav_profile) {

                Fragment navProfile = new ProfileFragment();
                FragmentTransaction ftProfile = getSupportFragmentManager().beginTransaction();
                ftProfile.replace(R.id.activity_maps, navProfile);
                ftProfile.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ftProfile.addToBackStack(null);
                ftProfile.commit();

                fab.setVisibility(View.GONE);

            } else if (id == R.id.nav_notifications) {

                Fragment navNotifications = new NotificationsNavFragment();
                FragmentTransaction ftNotifications = getSupportFragmentManager().beginTransaction();
                ftNotifications.replace(R.id.activity_maps, navNotifications);
                ftNotifications.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ftNotifications.addToBackStack(null);
                ftNotifications.commit();

                fab.setVisibility(View.GONE);

            } else if (id == R.id.nav_settings) {

                Fragment navSettings = new SettingsFragment();
                FragmentTransaction ftSettings = getSupportFragmentManager().beginTransaction();
                ftSettings.replace(R.id.activity_maps, navSettings);
                ftSettings.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ftSettings.addToBackStack(null);
                ftSettings.commit();

                fab.setVisibility(View.GONE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place Selected: " + place.getName());

                /*// Format the place's details and display them in the TextView.
                mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress(), place.getPhoneNumber(),
                        place.getWebsiteUri()));

                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
                    mPlaceAttribution.setText("");
                }*/
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }

    /**
     * Helper method to format information about a place nicely.
     */
    /*private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }*/
}
