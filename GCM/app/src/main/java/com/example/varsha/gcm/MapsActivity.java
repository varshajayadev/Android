package com.example.varsha.gcm;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
   // private GoogleApiClient mGoogleApiClient;
   // public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
   // private LocationRequest mLocationRequest;
    private double currentLattitude;
    private double currentLongitude;
    private String lat;
    private String lng;
    private String time_stamp;
    RequestQueue queue;
    String mPhoneNumber;
    //Location location;
    //private static final String TAG = "MainActivity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    boolean isReceiverRegistered;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();
        queue = Volley.newRequestQueue(this);
        //mythread  = new MyThread();

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        mLocationRequest = LocationRequest.create().setInterval(1000).setFastestInterval(1).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //getLatLong();
    }
    *//*private void BackgroundSplashTask(Location location) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        //return "";
                    }
                    while(true)    {
                        Thread.sleep(1000);
                        //location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        handleNewLocation(location);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute(null, null, null);
    }*//*





    */
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *//*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onResume() {
        super.onResume();
        //setUpMap();
        mGoogleApiClient.connect();
    }

    @SuppressWarnings("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mGoogleApiClient.disconnect();
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
            isReceiverRegistered = false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        *//*Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.varsha.mymapapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);*//*
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        *//*Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.varsha.gcm/http/host/path")
        );*//*
        *//*AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        mGoogleApiClient.disconnect();*//*
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Location sevices connected.");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            handleNewLocation(location);
            try {
                postLatLong();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    *//*@Override
    public synchronized void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if(!isRunning){
            mythread.interrupt();
            mythread.stop();
        }
    }*//*

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        currentLattitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        lat = Double.toString(currentLattitude);
        lng = Double.toString(currentLongitude);
        *//*Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        time_stamp = sdf.format(date);
        Log.d("time", "timestamp" + time_stamp);*//*
        LatLng latlng = new LatLng(currentLattitude, currentLongitude);
        MarkerOptions options = new MarkerOptions().position(latlng).title("I am here!");
        options.position(latlng);
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
        Toast.makeText(getApplicationContext(), "LocationChange called", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code" + connectionResult.getErrorCode());
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handleNewLocation(location);
        }
        //BackgroundSplashTask(location);

    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

    }

    *//*public static final  String BASE_URL = "";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:MM:ssZ").create();
    //Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

    public interface MyApiEndpointInterface {
        @POST("http://52.53.199.84:3000/setLocation")
        Call<ResponseBody>(@Body );
    }*//*
    public void postLatLong() throws JSONException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("number", mPhoneNumber);
        params.put("lat", lat);
        params.put("lng", lng);
    *//*params.put("event_id", event_id);
    params.put("user_name", user_name);
    params.put("user_id", user_id);*//*

        String url = "http://52.53.190.143:3000/setLocation";
        Log.d("Post", "SenderNumber" + mPhoneNumber);
        Log.d("Post", "Lat" + lat);
        Log.d("Post", "Lng" + lng);
        Log.d("POST", "LatLong sent");
        *//*JSONObject obj = new JSONObject();
    obj.put("Content-Type", "application/json; charset=utf-8");
    obj.put("time_stamp", time_stamp);
    obj.put("latitude", lat);
    obj.put("longitude", lng);*//*
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", "Response received: " + response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "error: " + error);
            }
        });  *//*{
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", "My useragent");
                *//**//*params.put("time_stamp", time_stamp);
                params.put("latitude", lat);
                params.put("longitude", lng);
                params.put("event_id", event_id);
                params.put("user_name", user_name);
                params.put("user_id", user_id);*//**//*

                return headers;
            }


        };*//*


        queue.add(postRequest);
    }

    public void getLatLong() {
        String url = "http://52.53.190.143:3000/test";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("args");
                   *//* String event_id = response.getString("event_id");
                    String user_id = response.getString("user_id");
                    String user_name = response.getString("user_name");*//*
                    String latitude = response.getString("lat");
                    String longitude = response.getString("lng");
                    String user_number = response.getString("number");
                    Log.d("GET", "Response" + response);
                    Double lati = Double.parseDouble(latitude);
                    Double lng = Double.parseDouble(longitude);
                    LatLng latlng = new LatLng(lati, lng);
                    displayOnMap(latlng);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "error: " + error);
            }

        });
        queue.add(getRequest);
    }

    public void displayOnMap(LatLng latlng) {
        MarkerOptions options = new MarkerOptions().position(latlng).title("Other Users");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));

    }

    *//*public void registerReceiver()  {
        if(!isReceiverRegistered)   {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(QuickstartPreferences.REGISTARTION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS)  {
            if(apiAvailability.isUserResolvableError(resultCode))   {
                apiAvailability.getErrorDialog(this, resultCode, 0).show();
            }   else {
                Log.i(TAG, "This device is not supported");
                finish();
            }
            return false;

        }
        return true;
    }*//*
    *//*protected LocationListener locListener;

    public void BackgroundLocationService() {


        LocationManager locManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        Criteria crt = new Criteria();
        crt.setAccuracy(Criteria.ACCURACY_FINE);
        String bestProvider = locManager.getBestProvider(crt, true);

        boolean gps_enabled;
        boolean network_enabled;
        boolean best_enabled;

        gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        best_enabled = locManager.isProviderEnabled(bestProvider);

        locListener = new MyLocationListener();

        if (best_enabled) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locManager.requestLocationUpdates(bestProvider, 0, 0, locListener);

            Log.i(TAG, "Best enabled: " + bestProvider);
        } else {
            Log.i("Location Provider: ", "best not enabled: " + bestProvider);
            if (gps_enabled) {
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
                Log.i(TAG, "gps enabled!");
            }
            if (network_enabled) {
                locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
                Log.i(TAG, "network enabled!");
            }
        }

    }
*//*

}
*/


    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 100 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Button btnFusedLocation;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    String eventId;
    Marker marker;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent i = getIntent();
        eventId = i.getStringExtra("eventid");
        Log.d(TAG, "onCreate ...............................");
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();
        queue = Volley.newRequestQueue(this);
        createLocationRequest();
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(AppIndex.API).build();

        while(true) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getLatLong();
        }
        //tvLocation = (TextView) findViewById(R.id.tvLocation);

        //btnFusedLocation = (Button) findViewById(R.id.btnShowLocation);
        /*btnFusedLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                updateUI();
            }
        });*/

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.varsha.gcm/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.varsha.gcm/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged.............................................."+location);
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        handleNewLocation(location);
       // updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());
            tvLocation.setText("At Time: " + mLastUpdateTime + "\n" +
                    "Latitude: " + lat + "\n" +
                    "Longitude: " + lng + "\n" +
                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
                    "Provider: " + mCurrentLocation.getProvider());
        } else {
            Log.d(TAG, "location is null ...............");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        currentLattitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        lat = Double.toString(currentLattitude);
        lng = Double.toString(currentLongitude);
        try {
            postLatLong();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LatLng latlng = new LatLng(currentLattitude, currentLongitude);
        Log.d("handlenewlocation","............."+currentLattitude);
        Log.d("handlenewlocation","............."+currentLongitude);
        Log.d("handlenewlocation","............."+latlng);
        mMap.clear();
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions().position(latlng).title("I am here!"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
        Toast.makeText(getApplicationContext(), "LocationChange called", Toast.LENGTH_LONG).show();
    }
    public void postLatLong() throws JSONException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("eventid", eventId);
        params.put("number", mPhoneNumber);
        params.put("lat", lat);
        params.put("lng", lng);

        String url = "http://52.53.190.143:3000/setLocation";
        Log.d("Post", "SenderNumber" + mPhoneNumber);
        Log.d("Post", "Lat" + lat);
        Log.d("Post", "Lng" + lng);
        Log.d("POST", "LatLong sent");
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", "Response received: " + response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "error: " + error);
            }
        });
            queue.add(postRequest);
}

    public void getLatLong() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("eventid", eventId);
        String url = "http://52.53.190.143:3000/test";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String latitude = response.getString("lat");
                    String longitude = response.getString("lng");
                    String user_number = response.getString("number");
                    Log.d("GET", "Response" + response);
                    Double lati = Double.parseDouble(latitude);
                    Double longi = Double.parseDouble(longitude);
                    LatLng latlng = new LatLng(lati, longi);
                    displayOnMap(latlng, user_number);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "error: " + error);
            }

        });
        queue.add(getRequest);
    }

    public void displayOnMap(LatLng latlng, String num) {


        MarkerOptions options = new MarkerOptions().position(latlng).title(num);
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));

    }

}