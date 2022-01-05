package com.sunnytech.task.eshop_admin;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_ID = "admin";
    private static final String KEY_ID = "adminid";

    private SharedPrefManager(Context context) { mCtx = context; }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if(mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }

    public boolean adminLogin(String id)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID,id);
        editor.apply();
        return true;
    }

    public boolean isLoggedIN() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID, null) != null;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getKeyId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID, null);
    }

}
