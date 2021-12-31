package com.sunnytech.task.shop_owner;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefmanager {
    private static SharedPrefmanager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "useremail";
    private static final String KEY_ID = "userid";
    private static final String KEY_SHOP_NAME = "shop";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATUS = "status";
    private static final String KEY_SHOP_CAT = "category";
    private static final String KEY_DEL_SERVICE = "service";

    private SharedPrefmanager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefmanager getInstance(Context context) {
        if(mInstance == null)
            mInstance = new SharedPrefmanager(context);
        return mInstance;
    }

    public boolean userLogin(int id, String name, String email, String shop, String date, String status, String category, String service)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID,id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_SHOP_NAME, shop);
        editor.putString(KEY_DATE, date);
        editor.putString(KEY_STATUS, status);
        editor.putString(KEY_SHOP_CAT, category);
        editor.putString(KEY_DEL_SERVICE, service);
        editor.apply();
        return true;
    }

    public boolean isLoggedIN() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getKeyShopName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SHOP_NAME, null);
    }

    public int getKeyId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, 0);
    }

    public String getKeyStatus() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_STATUS, null);
    }

    public String getKeyShopCat() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_SHOP_CAT, null);
    }

    public String getKeyDelService() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DEL_SERVICE, null);
    }
}
