package com.example.administrator.js.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.js.LocationManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tangming on 2018/5/22.
 */

public class LocationService extends Service {

    private static final String TAG = "LocationService";
    public Double longitude;
    public Double latitude;

    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "start LocationService!");
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "StartCommand LocationService!");
        getPosition();
        return super.onStartCommand(intent, flags, startId);

    }


    public void getPosition() {
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation == null) {
                Log.i(TAG, "amapLocation is null!");
                return;
            }
            if (amapLocation.getErrorCode() != 0) {
                Log.i(TAG, "amapLocation has exception errorCode:" + amapLocation.getErrorCode());
                return;
            }
            longitude = amapLocation.getLongitude();//获取经度
            latitude = amapLocation.getLatitude();//获取纬度
            LocationManager.getInsatance().latitude = String.valueOf(latitude);
            LocationManager.getInsatance().longitude = String.valueOf(longitude);
            LocationManager.getInsatance().cityCode =amapLocation.getCity();
            LocationManager.getInsatance().city=amapLocation.getCity();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(amapLocation.getTime());
            String timestr = df.format(date);
            Log.i(TAG, "longitude,latitude:" + longitude + "," + latitude);
            Log.i(TAG, "Nowtime:" + timestr);
        }

    };

}
