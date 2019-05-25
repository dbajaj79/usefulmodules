package com.example.permissionhandling;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.permissionhandling.databinding.ActivityMainBinding;
import com.example.permissionhandling.util.PermissionUtil;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    private String[] requiredPermissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
    private int PERMISSION_REQUEST_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    }

    public void onPermissionClick(View v)
    {
       if(PermissionUtil.isPermissionRequired())
       {
           askForPermission(this,requiredPermissions,PERMISSION_REQUEST_CODE);
       }
    }


    private void askForPermission(Activity context, String[] permissions, int requestCode) {
        if (ContextCompat.checkSelfPermission(context, permissions[0]) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[0]) || ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[1])) {
                Toast.makeText(context, "App Required Following", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(context,permissions,requestCode);
            } else {
                ActivityCompat.requestPermissions(context, permissions, requestCode);
            }
        } else {

            goAfterPermission();
        }


    }

    private void goAfterPermission()
    {
        Toast.makeText(this,"Location Persmission Granted Go Ahead",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_REQUEST_CODE)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED)
            {
                goAfterPermission();
            }
            else
            {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }
}
