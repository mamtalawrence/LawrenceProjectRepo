package com.lawrence.ditrp.dataModel;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreferences {
    private static CustomSharedPreferences customSharedPreferences;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor mEditor;

    public static CustomSharedPreferences getInstance(Context context) {
        if (customSharedPreferences == null) {
            return customSharedPreferences = new CustomSharedPreferences(context);
        }
        return customSharedPreferences;
    }

    /**
     * Constructor
     *
     * @param context context
     */
    private CustomSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("CustomSharedPreferences", Context.MODE_PRIVATE);
        mEditor = sharedPreferences.edit();
    }

    /**
     * save string value by key
     *
     * @param key   for save the value
     * @param value which needs to be save
     */
    public void saveStringData(String key, String value) {
        mEditor.putString(key, value).apply();
    }

    /**
     * get string value by key
     *
     * @param key which value needed
     * @return value by key
     */
    public String getStringData(String key) {
        return sharedPreferences.getString(key, "");
    }

    /**
     * save int value by key
     *
     * @param key   for save the value
     * @param value which needs to be save
     */
    /*public void saveIntData(String key, int value) {
        mEditor.putInt(key, value).apply();
    }*/

    /**
     * get int value by key
     *
     * @param key which value needed
     * @return value by key
     */
    /*public int getIntData(String key) {
        return sharedPreferences.getInt(key, 0);
    }*/

    /**
     * save Boolean value by key
     *
     * @param key    for save the value
     * @param isTrue which needs to be save
     */
    public void saveBoolean(String key, boolean isTrue) {
        mEditor.putBoolean(key, isTrue).apply();
    }

    /**
     * get Boolean value by key
     *
     * @param key which value needed
     * @return value by key
     */
    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * Clear stored user data from the shared preference
     */
    public void clearSharedPreferenceData() {
        if (sharedPreferences != null && mEditor != null) {
            mEditor.clear();
            mEditor.commit();
        }
    }
}
