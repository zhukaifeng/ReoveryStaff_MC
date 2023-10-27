package com.dclee.recovery.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity {

    protected Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mActivity = this;
        initView();
        initData();
    }

    public Activity getActivity() {
        return this;
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    /**
     * 跳转
     */
    public void startAction(@SuppressWarnings("rawtypes") Class cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("mydata", bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * 跳转
     */
    public void startActionWithFinish(@SuppressWarnings("rawtypes") Class cls) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }

    /**
     * 跳转
     */
    public void startAction(@SuppressWarnings("rawtypes") Class cls) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * 跳转
     */
    public void startAction(@SuppressWarnings("rawtypes") Class cls, String key, String id) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(key, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    /**
     * 跳转
     */
    public void startAction(@SuppressWarnings("rawtypes") Class cls, String key, int id) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(key, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
