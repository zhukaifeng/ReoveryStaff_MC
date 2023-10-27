package com.dclee.recovery.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.dclee.recovery.R;
import com.dclee.recovery.base.CacheUtil;
import com.dclee.recovery.util.BDLocationUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import androidx.annotation.Nullable;

public class UpdatePositionService extends IntentService {
    public UpdatePositionService(String name) {
        super(name);
    }

    public UpdatePositionService() {
        super("UpdatePosition");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e("updatePosition","aaa");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("updatePosition","bbb");
        BDLocationUtils bdLocationUtils = new BDLocationUtils(this);
        bdLocationUtils.setOnLocationGetListener(new BDLocationUtils.OnLocationGetListener() {
            @Override
            public void onLocationGet(BDLocation bdLocation, double lat, double lon) {
                Log.e("updatePosition", bdLocation.getAddress().countryCode + "|" + bdLocation.getAddress().cityCode + "||" + bdLocation.getAddress().adcode);
                RequestParams requestParams = new RequestParams(getResources().getString(R.string.base_url) + "s1/position");
                requestParams.addHeader("Authorization", CacheUtil.getTokenType() + " " + CacheUtil.getAccessToken());
                requestParams.addParameter("lat", lat);
                requestParams.addParameter("lon", lon);
                Log.e("updatePosition", "request:" + requestParams.toString());
                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("updatePosition", "result:" + result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e("updatePosition", "result error:" + ex.getLocalizedMessage());
                        ex.printStackTrace();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
        bdLocationUtils.doLocation();
    }

    private void updatePosition() {

    }
}
