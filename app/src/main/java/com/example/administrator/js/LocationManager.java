package com.example.administrator.js;

import android.preference.PreferenceManager;

/**
 * Created by tangming on 2018/5/22.
 */

public class LocationManager {

    public String longitude;
    public String latitude;
    public String cityCode;
    public String city;

    private LocationManager() {

    }

    private static class SingletonHolder {
        private static final LocationManager instance = new LocationManager();
    }

    public static final LocationManager getInsatance() {
        return LocationManager.SingletonHolder.instance;
    }


}
