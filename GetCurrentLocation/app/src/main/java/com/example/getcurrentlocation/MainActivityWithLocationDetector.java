package com.example.getcurrentlocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.getcurrentlocation.databinding.ActivityMainBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

public class MainActivityWithLocationDetector extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    /*
     * Steps to Get Current Location
     * Add Google Play Services Dependency
     * Initialize FusedLocationProviderClient
     * Implement OnSuccessListener with Location Object
     * Attach on SuccessListener with FusedLocationProviderClient
     * Check the Location object in onSuccessMethod
     * Display Location properties
     * ****Settings Location Client Steps****
     * Create a object of LocationRequest and Initialize it with
     *  Perority
     *  Update Interval
     *  Fastest Intervel
     * Create a Object of LocationSettingRequest it with
     * LocationSettingRequest builder  with  locaitonRequest
     *
     * Create a Task for LocationSettingResponse
     * Implement onCompleteListener for LocationSetting response
     * After Intialize LoationSettingResponse Task from
     * attach onCompleteListener with Task
     *
     * Check the  status of Setting in onComplete
     *
     *
     *
     *
     */


    private ActivityMainBinding activityMainBinding;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private GoogleApiClient googleApiClient;
    private boolean isDetectLocation;
    private int REQUEST_LOCATION_SETTINGS = 5000;
    private long FATEST_UPDATE_INTERVEL = 2000;
    private long UPDATE_INTERVEL = 3000;

    private String[] locationPermissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    private int LOCATION_PERMISSION_REQUES = 1000;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    private void init() {
        createGoogleApiClient();
        createFusedLocationApiClient();
        createLocationRequest();
        createLocationUpdate();

    }

    private void removeLocationUpdateListener()
    {
        locationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void createLocationUpdate()
    {
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onLocationChanged(locationResult.getLastLocation());
            }
        };
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdate()
    {
        locationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
    }

    private  void createFusedLocationApiClient()
    {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }
    private void createGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this,this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

    }

    public void askForCurrentLocation(View view) {
        askForPermission(locationPermissions, this, LOCATION_PERMISSION_REQUES);

    }

    private void getCurrentLocation() {

    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVEL);
        locationRequest.setFastestInterval(FATEST_UPDATE_INTERVEL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    private void askForPermission(String[] locationPermissions, Activity activity, int LOCATION_REQUST_CODE) {
        if (ContextCompat.checkSelfPermission(activity, locationPermissions[0]) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity, locationPermissions[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, locationPermissions[0]) && ActivityCompat.shouldShowRequestPermissionRationale(activity, locationPermissions[1])) {
                ActivityCompat.requestPermissions(this, locationPermissions, LOCATION_REQUST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, locationPermissions, LOCATION_REQUST_CODE);
            }
        } else {
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUES) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission is Denied", Toast.LENGTH_SHORT).show();
            }
        }
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

    @Override
    public void onLocationChanged(Location location) {

    }
}
