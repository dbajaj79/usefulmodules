package com.example.permissionhandling.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class PermissionUtil {
    public static boolean isPermissionRequired() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return true;
        } else {
            return false;
        }
    }

    public static void askForPermission(Activity context, String[] permissions,int requestCode) {
        if (ContextCompat.checkSelfPermission(context, permissions[0]) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[0]) || ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[1])) {
                Toast.makeText(context, "App Required Following", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(context,permissions,requestCode);
            } else {
                ActivityCompat.requestPermissions(context, permissions, requestCode);
            }
        } else {


        }


    }




}
