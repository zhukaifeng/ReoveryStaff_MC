package com.dclee.recovery.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.CacheUtil;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.pojo.MainPageData;
import com.dclee.recovery.service.UpdatePositionService;
import com.dclee.recovery.util.AndroidUtil;
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.util.ToastUtil;
import com.dclee.recovery.view.check.CheckActivity;
import com.dclee.recovery.view.check.CheckOrderActivity;
import com.dclee.recovery.view.create_order.CreateOrderActivity;
import com.dclee.recovery.view.device.DeviceActivity;
import com.dclee.recovery.view.order_summary.OrderSummaryActivity;
import com.dclee.recovery.view.orderlist.OrderListActivity;
import com.google.gson.JsonObject;
import com.longsh.optionframelibrary.OptionMaterialDialog;
import com.lzy.imagepicker.ImagePicker;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.xutils.http.RequestParams;

import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {
    private RequestUtil mRequestUtil;
    private ImageView mUserHead;
    private TextView mUserName;
    private TextView mDepartment;
    private TextView mNews;
    private ImageView ivQrcode;
    private ImageView ivScan;
    private LinearLayout llQrcode;
    private ImageView ivSqrcode;

    private final int REQ_SCANE_CODE = 0x200;
    private String mUserId;

    private Context mContext;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mContext = this;
        mRequestUtil = new RequestUtil(this);
        mUserHead = findViewById(R.id.iv_user_head);
        mUserName = findViewById(R.id.tv_user_name);
        mDepartment = findViewById(R.id.tv_department);
        mNews = findViewById(R.id.news);
        ivQrcode = findViewById(R.id.iv_qrcode);
        ivScan = findViewById(R.id.iv_scan);
        llQrcode = findViewById(R.id.ll_qrcode);
        ivSqrcode = findViewById(R.id.iv_sqrcode);
    }

    public void toHideQrcode(View view) {
        llQrcode.setVisibility(View.GONE);
    }

    public void toShowQrcode(View view) {
        if (!TextUtils.isEmpty(mUserId)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", mUserId);
            jsonObject.addProperty("name", mUserName.getText().toString());
            ivSqrcode.setImageBitmap(CodeUtils.createImage(jsonObject.toString(), 200, 200,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.ic_meicheng)));
            llQrcode.setVisibility(View.VISIBLE);
        } else {
            T.showShort(mActivity, "用户信息错误");
        }
    }

    public void toScan(View view) {
        new RxPermissions(this).request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        startActivityForResult(intent, REQ_SCANE_CODE);
                    }
                });
    }

    public void toSettingActivity(View view) {
        //startActivity(new Intent(this, MineActivity.class));
        final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(mContext);
        mMaterialDialog.setTitle("提示")
                .setMessage("是否退出账号？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CacheUtil.clearTokenType();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        })
                .setCanceledOnTouchOutside(true).show();
    }

    public void toOrdersActivity(View view) {
        startActivity(new Intent(this, OrderListActivity.class));
    }

    public void toCreateOrder(View view) {
        startActivity(new Intent(this, CreateOrderActivity.class));
    }

    public void toGetGoodsList(View view) {
        startActivity(new Intent(this, GetGoodsActivity.class));
    }

    public void toOrderSummary(View view) {
        startActivity(new Intent(this, OrderSummaryActivity.class));
    }

    public void toCheckOrderAct(View view) {
        if (ivScan.getVisibility() == View.VISIBLE) {
            startActivity(new Intent(this, CheckOrderActivity.class));
        } else {
            T.showShort(mActivity, "本账号未开通审核权限");
        }
    }

    public void toDeviceAct(View view) {
        startActivity(new Intent(this, DeviceActivity.class));
        //startActivity(new Intent(this, MyNfcActivity.class));
    }

    @Override
    public void initData() {
        if (TextUtils.isEmpty(CacheUtil.getAccessToken())) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        if (!AndroidUtil.isServiceExisted(this, UpdatePositionService.class.getName())) {
            new RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    new RxPermissions(MainActivity.this).request(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                                            .subscribe(new Consumer<Boolean>() {
                                                @Override
                                                public void accept(Boolean aBoolean) throws Exception {
                                                    if (aBoolean) {
                                                        getActivity().startService(new Intent(getActivity(), UpdatePositionService.class));
                                                    } else {
                                                        ToastUtil.showToast(getActivity(), "未能获取到位置权限，无法获取当前位置");
                                                    }
                                                }
                                            });
                                } else {
                                    getActivity().startService(new Intent(getActivity(), UpdatePositionService.class));
                                }
                            } else {
                                ToastUtil.showToast(getActivity(), "未能获取到位置权限，无法获取当前位置");
                            }
                        }
                    });
        }
        mRequestUtil.doPost("s1/home", new RequestParams(), MainPageData.class, new RequestUtil.OnRequestFinishListener<MainPageData>() {
            @Override
            public void onRequestFail(int errorCode, String desc) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                finish();
            }

            @Override
            public void onRequestSuccess(MainPageData result) {
                //x.image().bind(mUserHead, );
                String picUrl = getResources().getString(R.string.res_url) +
                        result.getUser().getAvatar();
                Glide
                        .with(mContext)
                        .load(picUrl)
                        .centerCrop()
                        //.placeholder(R.drawable.loading_spinner)
                        .crossFade()
                        .into(mUserHead);

                Config.userId = String.valueOf(result.getUser().getId());
                Config.userName = result.getUser().getUsername();

                mUserName.setText(result.getUser().getUsername());
                mUserId = String.valueOf(result.getUser().getId());
                mDepartment.setText(result.getUser().getDepartment());
                StringBuffer newsBuilder = new StringBuffer();
                for (int i = 0; i < result.getNotify().size(); i++) {
                    if (i != 0) {
                        newsBuilder.append("\n");
                    }
                    newsBuilder.append(result.getNotify().get(i).getContent());
                }
                mNews.setText(newsBuilder.toString());

                if (!"移动回收人员".equals(result.getAdminType())) {
                    ivScan.setVisibility(View.VISIBLE);
                    ivQrcode.setVisibility(View.GONE);
                } else {
                    ivScan.setVisibility(View.GONE);
                    ivQrcode.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && resultCode != ImagePicker.RESULT_CODE_ITEMS) {
            return;
        }
        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                if (result.contains("id") && result.contains("name")) {
                    startAction(CheckActivity.class, "StaffData", result);
                }
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
