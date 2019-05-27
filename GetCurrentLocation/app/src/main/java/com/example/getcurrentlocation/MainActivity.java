package com.example.getcurrentlocation;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.getcurrentlocation.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity implements OnSuccessListener<Location>{
    /**
     * Steps to Get Current Location
     * Add Google Play Services Dependency
     * Initialize FusedLocationProviderClient
     * Implement OnSuccessListener with Location Object
     * Attach on SuccessListener with FusedLocationProviderClient
     * Check the Location object in onSuccessMethod
     * Display Location properties
     */
    private ActivityMainBinding activityMainBinding;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationSettingsRequest;
    private long FATEST_UPDATE_INTERVEL = 2000;
    private long UPDATE_INTERVEL = 3000;

    private String[] locationPermissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    private int LOCATION_PERMISSION_REQUES= 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }



    public void askForCurrentLocation(View view)
    {
        askForPermission(locationPermissions,this,LOCATION_PERMISSION_REQUES);

    }
    private void getCurrentLocation()
    {
        locationProviderClient.getLastLocation().addOnSuccessListener(this, this);
    }

    private void setLocationSettingsRequest()
    {
        locationSettingsRequest = new LocationRequest();
        locationSettingsRequest.setInterval(UPDATE_INTERVEL);
        locationSettingsRequest.setFastestInterval(FATEST_UPDATE_INTERVEL);
        locationSettingsRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private boolean isPermissionRequired()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void askForPermission(String[]locationPermissions, Activity activity,int LOCATION_REQUST_CODE)
    {
        if(ContextCompat.checkSelfPermission(activity,locationPermissions[0])!= PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity,locationPermissions[1])!=PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,locationPermissions[0])&& ActivityCompat.shouldShowRequestPermissionRationale(activity,locationPermissions[1]))
            {
                ActivityCompat.requestPermissions(this,locationPermissions,LOCATION_REQUST_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,locationPermissions,LOCATION_REQUST_CODE);
            }
        }
        else
        {
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==LOCATION_PERMISSION_REQUES)
        {
            if (grantResults[0] ==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
            else
            {
                Toast.makeText(this, "Permission is Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccess(Location location) {
        if(location!=null)
        {
            String myLocation= "Location is: lat:"+location.getLatitude()+" "+"lng:"+location.getLongitude();
            activityMainBinding.textViewCurrentLocation.setText(myLocation);

        }

    }
}
