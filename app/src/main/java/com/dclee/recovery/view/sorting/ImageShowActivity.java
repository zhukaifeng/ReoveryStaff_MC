package com.dclee.recovery.view.sorting;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;

public class ImageShowActivity extends BaseActivity {

    private ImageView iv_pic;


    @Override
    public int getLayoutId() {
        return R.layout.activity_show_pic;
    }

    @Override
    public void initView() {
        iv_pic = findViewById(R.id.iv_pic);
        String picUrl = getIntent().getStringExtra("picUrl");
        Glide.with(this).load(picUrl).centerCrop().into(iv_pic);

    }

    @Override
    public void initData() {

    }
}
