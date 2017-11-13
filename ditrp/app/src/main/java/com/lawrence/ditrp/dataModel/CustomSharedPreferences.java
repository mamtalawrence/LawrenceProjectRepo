package com.lawrence.ditrp.dataModel;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Anagha.Mahajan on 09-Nov-17.
 */
public class CustomSharedPreferences {

    private static CustomSharedPreferences customSharedPreferences;
    private SharedPreferences sharedPreferences;

    public static CustomSharedPreferences getInstance(Context context) {
        if (customSharedPreferences == null) {
            return customSharedPreferences = new CustomSharedPreferences(context);
        }
        return customSharedPreferences;
    }


    public CustomSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("CustomSharedPreferences", Context.MODE_PRIVATE);
    }

    public void saveStringData(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringData(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void saveIntData(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntData(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void saveBoolean(String key, boolean isTrue) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isTrue);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
