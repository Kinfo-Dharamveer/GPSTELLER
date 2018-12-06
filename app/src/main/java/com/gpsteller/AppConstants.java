package com.gpsteller;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;
import java.util.List;
import java.util.Locale;

public class AppConstants {

    // Location updates intervals
    public static long UPDATE_INTERVAL = 10000; // 1 sec
    public static long FATEST_INTERVAL = 10000; // 1 sec
    public static String LATITUDE = "lat";
    public static String LONGITUDE = "long";
    public static Float MAP_ZOOM = 11.0f; //
    public static String IMAGE_PATH = "FilePath";
    public static String USER_NAME = "user_name";
    public static String EDIT_PLACE = "edit_place";
    public static String PHONE_NO = "phone_no";
    public static String PLACE_SAVED = "place_saved";
    public static String SPEED = "Speed";
    public static String DISTANCE = "distance";
    public static String SPIN_KEY = "spin_key";
    public static String GROUP_NAME = "group_name";
    public static final long delayTime = 2000;


    public static String getCompleteAddressString(Context m_context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(m_context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.v("My Current location add", "" + strAdd.toString());
            }
//            else {
//                Log.v("My Current location address", "No Address returned!");
//            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(m_context, "Sorry, Your location cannot be retrieved !" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.v("My Current location address", "Cannot get Address!");
        }
        return strAdd;
    }

}
