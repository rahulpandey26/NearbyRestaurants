package com.example.nearbyrestaurants.common.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {

    private static AppSharedPreference sInstance;
    private static final String APP_SETTINGS = "APP_SETTINGS";
    private static final String KEY_DEVICE_LAT = "device_lat";
    private static final String KEY_DEVICE_LAN = "device_lan";

    public static AppSharedPreference getInstance(){
        if(sInstance == null){
            sInstance = new AppSharedPreference();
            return sInstance;
        } else {
            return sInstance;
        }
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public void setDeviceLat(Context context, double deviceLat){
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(KEY_DEVICE_LAT ,  Double.doubleToLongBits(deviceLat));
        editor.apply();
    }

    public double getDeviceLat(Context context){
        return Double.doubleToRawLongBits(getSharedPreferences(context).getLong(KEY_DEVICE_LAT ,
                0));
    }

    public void setDeviceLan(Context context, double deviceLat){
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(KEY_DEVICE_LAN ,  Double.doubleToLongBits(deviceLat));
        editor.apply();
    }

    public double getDeviceLan(Context context){
        return Double.doubleToRawLongBits(getSharedPreferences(context).getLong(KEY_DEVICE_LAN ,
                0));
    }
}
