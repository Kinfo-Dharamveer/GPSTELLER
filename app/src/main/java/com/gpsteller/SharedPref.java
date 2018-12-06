package com.gpsteller;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    static SharedPreferences sharedPreferences;
    Context context;
    private String imageString;


    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    }


    public String getImageString() {
        imageString = sharedPreferences.getString("imageString",imageString);
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
        sharedPreferences.edit().putString("imageString",imageString).commit();

    }
}
