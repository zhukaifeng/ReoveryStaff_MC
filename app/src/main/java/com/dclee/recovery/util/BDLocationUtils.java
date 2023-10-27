package com.dclee.recovery.util;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BDLocationUtils {
    public Context context;
    public OnLocationGetListener onLocationGetListener;

    public void setOnLocationGetListener(OnLocationGetListener onLocationGetListener) {
        this.onLocationGetListener = onLocationGetListener;
    }

    public LocationClient mLocationClient = null;    //LocationClient类是定位SDK的核心类
    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
//            bdLocation.getLongitude();
            if (onLocationGetListener != null) {
                onLocationGetListener.onLocationGet(bdLocation, bdLocation.getLatitude(), bdLocation.getLongitude());
            }
        }
    };

    public interface OnLocationGetListener {
        void onLocationGet(BDLocation bdLocation, double lat, double lon);
    }

    public BDLocationUtils(Context context) {
        this.context = context;
    }

    public void doLocation() {
        //声明LocationClient类
        try {
            //没有它，会报错：Please recheck the setAgreePrivacy interface
            LocationClient.setAgreePrivacy(true);
            mLocationClient = new LocationClient(context.getApplicationContext());
            //注册监听函数
            mLocationClient.registerLocationListener(myListener);
            //初始化定位
            initLocation();
            mLocationClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setIsNeedAddress(true);
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        int span = 1000 * 60;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的


        mLocationClient.setLocOption(option);
    }
}