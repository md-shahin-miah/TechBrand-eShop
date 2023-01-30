package com.shahin.techbrandeshop.util;

import android.content.Context;

public class LocalDataManager {

    private static final String FIRST_TIME_INSTALL = "first_time_install_app";
    private static LocalDataManager instance;
    private MySharedPreference mySharedPreference;

    public static void init(Context context) {
        instance = new LocalDataManager();
        instance.mySharedPreference = new MySharedPreference(context);
    }

    private static LocalDataManager getInstance() {
        if (instance == null)
            instance = new LocalDataManager();
        return instance;
    }

    public static void setFirstTimeInstall(boolean isFirstTimeInstall) {
        LocalDataManager.getInstance().mySharedPreference.putBoolean(FIRST_TIME_INSTALL, isFirstTimeInstall);
    }

    public static boolean getFirstTimeInstall() {
        return LocalDataManager.getInstance().mySharedPreference.getBoolean(FIRST_TIME_INSTALL);
    }
}