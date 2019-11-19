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
        editor.putString(KEY_DEVICE_LAT ,  String.valueOf(deviceLat));
        editor.apply();
    }

    public String getDeviceLat(Context context){
        return getSharedPreferences(context).getString(KEY_DEVICE_LAT , "");
    }

    public void setDeviceLan(Context context, double deviceLat){
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_DEVICE_LAN ,  String.valueOf(deviceLat));
        editor.apply();
    }

    public String getDeviceLan(Context context){
        return getSharedPreferences(context).getString(KEY_DEVICE_LAN , "");
    }
}
