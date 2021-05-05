package com.example.justdraw.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPrefrence {

    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    private static final String PREFS_NAME = "sharedPreferences";

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor prefEditor = null;
    private static Context mContext = null;
    public static com.example.justdraw.Utilities.MySharedPrefrence instance = null;

    public static com.example.justdraw.Utilities.MySharedPrefrence instanceOf(Context context) {
        mContext = context;
        if (instance == null) {
            instance = new com.example.justdraw.Utilities.MySharedPrefrence();
        }
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefEditor = sharedPreferences.edit();
        return instance;
    }




    public void setLoggedIn(Boolean isLogIn) {
        prefEditor.putBoolean("login", isLogIn);
        prefEditor.commit();
    }

    public Boolean isLoggedIn() {
        return sharedPreferences.getBoolean("login", false);

    }

    public void setUserName(String name) {
        prefEditor.putString("name", name);
        prefEditor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString("name", "");

    }
    public void setUserPhone(String phone) {
        prefEditor.putString("phone", phone);
        prefEditor.commit();
    }

    public String getUserPhone() {
        return sharedPreferences.getString("phone", "");

    }
    public String getUserProfile() {
        return sharedPreferences.getString("profile", "");
    }

    public void setUserProfile(String profile) {
        prefEditor.putString("profile", profile);
        prefEditor.commit();
    }

    public void setUserId(String phone) {
        prefEditor.putString("phone", phone);
        prefEditor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString("phone", "");

    }
    public String getToken() {
        return sharedPreferences.getString("token", "");
    }

    public void setToken(String mobile) {
        prefEditor.putString("token", mobile);
        prefEditor.commit();
    }

}
