package com.aait.oms.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationData {

    Context context;

    public ApplicationData(Context context) {
        this.context = context;
    }

    public  void savePageState(String key, String value){
        SharedPreferences sharedPre = context.getSharedPreferences("PageState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPre.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getPageState(String key) {
        SharedPreferences sharedPre = context.getSharedPreferences("PageState", Context.MODE_PRIVATE);
        return sharedPre.getString(key, null);
    }

    public void saveLoginInfo(String key, String value) {

        SharedPreferences sharedPre = context.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPre.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getLoginInfo(String key) {

        SharedPreferences sharedPre = context.getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        return sharedPre.getString(key, null);
    }


}
