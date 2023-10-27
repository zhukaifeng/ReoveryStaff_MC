package com.dclee.recovery.view.device;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.calypso.bluelib.bean.SearchResult;
import com.dclee.recovery.R;
import com.dclee.recovery.adapter.DeviceAdapter;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.bean.event.BlueTeenEvent;
import com.dclee.recovery.bean.event.BlueTeenOperaEvent;
import com.dclee.recovery.service.BlueDeviceService;
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.wedget.TitleBar;
import com.github.ybq.android.spinkit.SpinKitView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DeviceActivity extends BaseActivity {

    private Activity mActivity;

    private TitleBar titleBar;
    private GridView gvDevice;
    private SpinKitView skvDevice;
    private SmartRefreshLayout srlDevice;

    private RefreshLayout mRefreshlayout;

    private String mBlueMac;
    private List<SearchResult> mDevices = new ArrayList<>();
    private DeviceAdapter mAdapter;

    private boolean isStartScan;

    @Override
    public int getLayoutId() {
        return R.layout.activity_device;
    }

    @Override
    public void initView() {
        titleBar = findViewById(R.id.title);
        gvDevice = findViewById(R.id.gv_device);
        skvDevice = findViewById(R.id.skv_device);
        srlDevice = findViewById(R.id.srl_device);
        titleBar.setTitle("设备管理");
        mActivity = this;
        EventBus.getDefault().register(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(DeviceActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                initStart();
            } else {
                ActivityCompat.requestPermissions(DeviceActivity.this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
            }
        } else {
            initStart();
        }
    }


    private void initStart() {
        startBlueTeen();
        gvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posi, long l) {
                mBlueMac = mDevices.get(posi).getAddress();
                PreferencesUtils.putString(mActivity, "blue_mac", mBlueMac);
                mAdapter.clickDevice(mBlueMac);
            }
        });

        srlDevice.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mRefreshlayout = refreshlayout;
                if (!isStartScan) {
                    mDevices.clear();
                    mAdapter.notifyDataSetChanged();
                    EventBus.getDefault().post(new BlueTeenOperaEvent(Config.BLUETEEN_OPERA_START_SCAN));
                }
                mRefreshlayout.finishRefresh(true);
            }
        });
    }

    private void startBlueTeen() {
        mBlueMac = PreferencesUtils.getString(mActivity, "blue_mac", "");
        Intent intent = new Intent();
        intent.setClass(DeviceActivity.this, BlueDeviceService.class);
        intent.putExtra("auto_scran", true);
        intent.putExtra("auto_connect", false);
        startService(intent);
    }

    @Override
    public void initData() {

    }

    private void initDeviceView() {

        List<SearchResult> newdevices = new ArrayList<>();

        if (mDevices != null && mDevices.size() > 1) {
            for (SearchResult searchResult : mDevices) {
                boolean isHave = false;

                if (TextUtils.isEmpty(searchResult.getName()) || "NULL".equals(searchResult.getName())) {
                    break;
                }

                for (int i = 0; i < newdevices.size(); i++) {
                    if (!TextUtils.isEmpty(searchResult.getAddress()) && searchResult.getAddress().equals(newdevices.get(i).getAddress())) {
                        isHave = true;
                        break;
                    }
                }

                if (!isHave) {
                    newdevices.add(searchResult);
                }
            }
            mDevices.clear();
            mDevices.addAll(newdevices);
        }

        if (mAdapter == null) {
            mAdapter = new DeviceAdapter(mActivity, mDevices, R.layout.item_device);
            gvDevice.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.clickDevice(mBlueMac);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 2) {
            if (permissions[0].equals(Manifest.permission.ACCESS_COARSE_LOCATION) && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                initStart();
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.
                        permission.ACCESS_COARSE_LOCATION)) {
                    return;
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onBlueTeenEvent(BlueTeenEvent blueTeenEvent) {
        switch (blueTeenEvent.getCode()) {
            case Config.BLUETEEN_SCAN:
                skvDevice.setVisibility(View.VISIBLE);
                isStartScan = true;
                break;

            case Config.BLUETEEN_SCAN_SUC:
                skvDevice.setVisibility(View.GONE);
                mDevices.clear();
                mDevices.addAll(blueTeenEvent.getResults());
                initDeviceView();
                isStartScan = false;
                EventBus.getDefault().post(new BlueTeenOperaEvent(Config.BLUETEEN_OPERA_STOP_SCAN));
                break;

            case Config.BLUETEEN_SCAN_FAILED:
                skvDevice.setVisibility(View.GONE);
                isStartScan = false;
                EventBus.getDefault().post(new BlueTeenOperaEvent(Config.BLUETEEN_OPERA_STOP_SCAN));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Intent intent = new Intent();
        intent.setClass(DeviceActivity.this, BlueDeviceService.class);
        stopService(intent);
    }
}
