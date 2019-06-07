package com.example.captureimage.util;

import android.os.Build;

public class OSVersionUtils
{
    public static boolean isLollipopAndAbove()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isKitkatAndBelow()
    {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isKitkatAndAbove()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isNoughatAndAbove()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    public static boolean isAboveNoughat()
    {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1;
    }
}
