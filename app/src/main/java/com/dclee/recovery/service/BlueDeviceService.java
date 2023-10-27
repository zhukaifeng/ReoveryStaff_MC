package com.dclee.recovery.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.calypso.bluelib.bean.SearchResult;
import com.calypso.bluelib.listener.OnConnectListener;
import com.calypso.bluelib.listener.OnReceiveMessageListener;
import com.calypso.bluelib.listener.OnSearchDeviceListener;
import com.calypso.bluelib.listener.OnSendMessageListener;
import com.calypso.bluelib.manage.BlueManager;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.bean.event.BlueTeenEvent;
import com.dclee.recovery.bean.event.BlueTeenOperaEvent;
import com.dclee.recovery.util.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.dclee.recovery.util.HexUtils.hexStringToString;

public class BlueDeviceService extends Service {

    private BlueManager bluemanage;
    private StringBuilder stringBuilder;
    private List<SearchResult> mDevices;
    private OnConnectListener onConnectListener;
    private OnSendMessageListener onSendMessageListener;
    private OnSearchDeviceListener onSearchDeviceListener;
    private OnReceiveMessageListener onReceiveMessageListener;
    private String mBlueMac;
    private Context mContext;

    private String mWeightData;
    private int mParmTag;

    private boolean isAutoScran;
    private boolean isAutoConnect;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = msg.obj.toString();
            switch (msg.what) {
                case 3:
                    //e("接收完成！");
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append(message);
                    getWeightParm(hexStringToString(stringBuilder.toString()));
                    break;
            }
        }
    };

    public BlueDeviceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mDevices = new ArrayList<>();
        stringBuilder = new StringBuilder();
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mBlueMac = PreferencesUtils.getString(mContext, "blue_mac", "");
        isAutoScran = intent.getBooleanExtra("auto_scran", false);
        isAutoConnect = intent.getBooleanExtra("auto_connect", false);
        initBlueManager();
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onBlueTeenOperaEvent(BlueTeenOperaEvent blueTeenOperaEvent) {
        e("BlueTeenOpera " + blueTeenOperaEvent.getCode());
        if (bluemanage == null) {
            initBlueManager();
            e("BlueTeenOpera reset");
        }

        switch (blueTeenOperaEvent.getCode()) {
            case Config.BLUETEEN_OPERA_START_SCAN:
                bluemanage.setReadVersion(false);
                bluemanage.searchDevices();
                break;

            case Config.BLUETEEN_OPERA_STOP_SCAN:
                bluemanage.stopScan();
                break;
        }
    }

    private void getWeightParm(String data) {
        data = data.replaceAll("\n", "e").replaceAll("\r", "e");
        if (mParmTag < 3) {
            mParmTag++;
            mWeightData = mWeightData + data;
        } else {
            mWeightData = mWeightData.substring(mWeightData.indexOf("ee") + 4, mWeightData.indexOf("ee") + 13);
            e("mWeightData：" + mWeightData);
            mParmTag = 0;
            mWeightData = "";
        }
    }

    /**
     * 初始化蓝牙管理，设置监听
     */
    public void initBlueManager() {
        onSearchDeviceListener = new OnSearchDeviceListener() {
            @Override
            public void onStartDiscovery() {
                e("onStartDiscovery()");
                EventBus.getDefault().post(new BlueTeenEvent(Config.BLUETEEN_SCAN,
                        "正在搜索设备", mDevices));
            }

            @Override
            public void onNewDeviceFound(BluetoothDevice device) {
                e("new device: " + device.getName() + " " + device.getAddress());
            }

            @Override
            public void onSearchCompleted(List<SearchResult> bondedList, List<SearchResult> newList) {
                e("bondedList "+bondedList.toString());
                e("newList "+newList.toString());
                mDevices.clear();
                mDevices.addAll(bondedList);
                mDevices.addAll(newList);

                EventBus.getDefault().post(new BlueTeenEvent(Config.BLUETEEN_SCAN_SUC,
                        "搜索完成", mDevices));
            }

            @Override
            public void onError(Exception e) {
                EventBus.getDefault().post(new BlueTeenEvent(Config.BLUETEEN_SCAN_FAILED,
                        "搜索失败", mDevices));
            }
        };
        onConnectListener = new OnConnectListener() {
            @Override
            public void onConnectStart() {
                //开始连接
                e("onConnectStart");
            }

            @Override
            public void onConnectting() {
                //正在连接..;
                e("onConnectting");
            }

            @Override
            public void onConnectFailed() {
               //连接失败
                e("onConnectFailed");

            }

            @Override
            public void onConectSuccess(String mac) {
                //连接成功 MAC
                e("onConectSuccess");
            }

            @Override
            public void onError(Exception e) {
                //连接异常！;
                e("onError");
            }
        };
        onSendMessageListener = new OnSendMessageListener() {
            @Override
            public void onSuccess(int status, String response) {
                //发送成功！
                e("send message is success ! ");
            }

            @Override
            public void onConnectionLost(Exception e) {
                //连接断开！
                e("send message is onConnectionLost ! ");
            }

            @Override
            public void onError(Exception e) {
                //发送失败！
                e("send message is onError ! ");
            }
        };

        onReceiveMessageListener = new OnReceiveMessageListener() {
            @Override
            public void onProgressUpdate(String what, int progress) {

            }

            @Override
            public void onDetectDataUpdate(String what) {
                sendMessage(3, what);
            }

            @Override
            public void onDetectDataFinish() {
                e("receive message is onDetectDataFinish");
            }

            @Override
            public void onNewLine(String s) {
                sendMessage(3, s);
            }

            @Override
            public void onConnectionLost(Exception e) {
                e("receive message is onConnectionLost ! ");
            }

            @Override
            public void onError(Exception e) {
                e("receive message is onError ! ");
            }
        };
        bluemanage = BlueManager.getInstance(getApplicationContext());
        bluemanage.setOnSearchDeviceListener(onSearchDeviceListener);
        bluemanage.setOnConnectListener(onConnectListener);
        bluemanage.setOnSendMessageListener(onSendMessageListener);
        bluemanage.setOnReceiveMessageListener(onReceiveMessageListener);
        bluemanage.requestEnableBt();
        if (isAutoScran) {
            bluemanage.setReadVersion(false);
            bluemanage.searchDevices();
        }

        if (isAutoConnect) {

        }
    }


    /**
     * @param type    0 修改状态  1 更新进度  2 体检完成  3 体检数据进度
     * @param context
     */
    public void sendMessage(int type, String context) {
        if (handler != null) {
            Message message = handler.obtainMessage();
            message.what = type;
            message.obj = context;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        e("stop service");
        if (bluemanage != null) {
            bluemanage.close();
            bluemanage = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        EventBus.getDefault().unregister(this);
    }

    private void e(String msg) {
        Log.e("BlueDeviceService", msg);
    }
}
