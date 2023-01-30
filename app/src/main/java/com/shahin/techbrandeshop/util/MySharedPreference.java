package com.shahin.techbrandeshop.util;

import android.content.Context;

public class MySharedPreference {
    private static final String MY_SHARED_PREFERENCE = "MSP_DB";
    private Context context;

    public MySharedPreference(Context context) {
        this.context = context;
    }

    public void putBoolean(String key, boolean val) {
        context.getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, val)
                .apply();
    }

    public boolean getBoolean(String key) {
        return context.getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE)
                .getBoolean(key, false);
    }
}