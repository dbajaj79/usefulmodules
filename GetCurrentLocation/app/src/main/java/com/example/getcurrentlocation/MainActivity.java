package com.example.getcurrentlocation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.getcurrentlocation.databinding.ActivityMainBinding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements OnSuccessListener<Location>,OnCompleteListener<LocationSettingsResponse> {
    /**
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
    private int REQUEST_LOCATION_SETTINGS = 5000;
    private long FATEST_UPDATE_INTERVEL = 2000;
    private long UPDATE_INTERVEL = 3000;

    private String[] locationPermissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
    private int LOCATION_PERMISSION_REQUES= 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationProviderClient.getLastLocation().addOnSuccessListener(this, this);
    }



    public void askForCurrentLocation(View view)
    {
        askForPermission(locationPermissions,this,LOCATION_PERMISSION_REQUES);

    }
    private void getCurrentLocation()
    {
        setLocationRequest();
        setLocationSettingsRequest();

    }

    private void setLocationRequest()
    {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVEL);
        locationRequest.setFastestInterval(FATEST_UPDATE_INTERVEL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    private void setLocationSettingsRequest()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).setAlwaysShow(true);
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> resultTask = settingsClient.checkLocationSettings(builder.build());
        resultTask.addOnCompleteListener(this);
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

    @Override
    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
        try
        {
            LocationSettingsResponse locationSettingsResponse = task.getResult(ApiException.class);
        } catch (ApiException e) {
            switch (e.getStatusCode())
            {
                case LocationSettingsStatusCodes.SUCCESS:
                    getCurrentLocation();
                    break;
                case LocationSettingsStatusCodes
                        .RESOLUTION_REQUIRED:

                    ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                    try {
                        resolvableApiException.startResolutionForResult(this,REQUEST_LOCATION_SETTINGS);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Toast.makeText(this,"Sorry locaiton Setting is not available",Toast.LENGTH_LONG).show();
                    break;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_LOCATION_SETTINGS)
        {
            switch (resultCode)
            {
                case Activity.RESULT_OK:
                    Toast.makeText(this, "Request is Working", Toast.LENGTH_SHORT).show();
                    locationProviderClient.getLastLocation();

                break;
               case Activity.RESULT_CANCELED:
                   Toast.makeText(this, "Request is Cancelled", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
