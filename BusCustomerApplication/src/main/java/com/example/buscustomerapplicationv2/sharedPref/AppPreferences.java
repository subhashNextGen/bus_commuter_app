package com.example.buscustomerapplicationv2.sharedPref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

    static SharedPreferences preferences;
    public static void setAppPrefrences(String key, String value, Context context) {
         preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getAppPrefrences(String key, Context context) {
         preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void clearAllPreferences(){
        preferences.edit().clear().apply();
    }


}
