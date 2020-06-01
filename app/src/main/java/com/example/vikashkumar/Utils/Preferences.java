package com.example.vikashkumar.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Preferences(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences("Vikash Kumar", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserID(String email) {
        editor.putString(Constant.EMAILID, email);
        editor.apply();
    }
    public void setPassword(String password) {
        editor.putString(Constant.PASSWORD, password);
        editor.apply();
    }
    public void setToken(String token) {
        editor.putString(Constant.TOKEN, token);
        editor.apply();
    }

    public String getUserID(){
        return sharedPreferences.getString(Constant.EMAILID, "");
    }
    public String getToken(){
        return sharedPreferences.getString(Constant.TOKEN, "");
    }

    public String getPassword(){
        return sharedPreferences.getString(Constant.PASSWORD, "");
    }


}
