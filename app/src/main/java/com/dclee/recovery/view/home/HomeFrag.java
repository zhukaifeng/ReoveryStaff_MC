package com.dclee.recovery.view.home;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dclee.recovery.Constant;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseFragment;
import com.dclee.recovery.base.CacheUtil;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.pojo.MainPageData;
import com.dclee.recovery.service.UpdatePositionService;
import com.dclee.recovery.util.AndroidUtil;
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.util.ToastUtil;
import com.dclee.recovery.view.GetGoodsActivity;
import com.dclee.recovery.view.LoginActivity;
import com.dclee.recovery.view.MainActivity;
import com.dclee.recovery.view.check.CheckOrderActivity;
import com.dclee.recovery.view.create_order.CreateOrderActivity;
import com.dclee.recovery.view.device.DeviceActivity;
import com.dclee.recovery.view.order_summary.OrderSummaryActivity;
import com.dclee.recovery.view.orderlist.OrderAdminListActivity;
import com.dclee.recovery.view.orderlist.OrderListActivity;
import com.dclee.recovery.view.sorting.SortInListActivity;
import com.dclee.recovery.view.sorting.SortingReqActivity;
import com.google.gson.JsonObject;
import com.longsh.optionframelibrary.OptionMaterialDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.xutils.http.RequestParams;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HomeFrag extends BaseFragment {
    private RequestUtil mRequestUtil;
    private ImageView mUserHead;
    private TextView mUserName;
    private TextView mDepartment;
    private TextView mNews;
    private ImageView ivQrcode;
    private ImageView ivScan;
    private LinearLayout llQrcode;
    private ImageView ivSqrcode;

    private LinearLayout llOrders;
    private LinearLayout llGetGoodsList;
    private LinearLayout llCreateOrder;
    private LinearLayout llOrderSummary;
    private LinearLayout llCheckOrderAct;
    private LinearLayout llDeviceAct;

    private LinearLayout ll_sort_req;
    private LinearLayout ll_sort_in;


    private final int REQ_SCANE_CODE = 0x200;
    private String mUserId;

    @Override
    protected int setLayout() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView(View view) {
        mRequestUtil = new RequestUtil(getActivity());
    }

    public void toHideQrcode(View view) {
        llQrcode.setVisibility(View.GONE);
    }

    private void toShowQrcode() {
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

    private void toScan() {
        new RxPermissions(this).request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        startActivityForResult(intent, REQ_SCANE_CODE);
                    }
                });
    }

    private void toSettingActivity() {
        //startActivity(new Intent(this, MineActivity.class));
        final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(mActivity);
        mMaterialDialog.setTitle("提示")
                .setMessage("是否退出账号？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CacheUtil.clearTokenType();
                        toClass(mActivity, LoginActivity.class);
                        getActivity().finish();
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

    private void toOrdersActivity() {
        String assignEnable = PreferencesUtils.getString(mActivity,"assignEnable","0");
        if("0".equals(assignEnable)){
            toClass(mActivity, OrderListActivity.class);
        } else {
            toClass(mActivity, OrderAdminListActivity.class);
        }
    }

    private void toCreateOrder() {
        toClass(mActivity, CreateOrderActivity.class);
    }

    private void toGetGoodsList() {
        toClass(mActivity, GetGoodsActivity.class);
    }

    private void toOrderSummary() {
        toClass(mActivity, OrderSummaryActivity.class);
    }

    private void toCheckOrderAct() {
        if (ivScan.getVisibility() == View.VISIBLE) {
            toClass(mActivity, CheckOrderActivity.class);
        } else {
            T.showShort(mActivity, "本账号未开通审核权限");
        }
    }

    private void toDeviceAct() {
        toClass(mActivity, DeviceActivity.class);
        //startActivity(new Intent(this, MyNfcActivity.class));
    }

    @Override
    protected void initListener() {
        mUserHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSettingActivity();
            }
        });

        ivQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toShowQrcode();
            }
        });

        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toScan();
            }
        });

        llQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_sort_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClass(mActivity, SortInListActivity.class);
            }
        });

        ll_sort_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClass(mActivity, SortingReqActivity.class);
            }
        });

        llOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOrdersActivity();
            }
        });

        llGetGoodsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGetGoodsList();
            }
        });

        llCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCreateOrder();
            }
        });

        llOrderSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOrderSummary();
            }
        });

        llCheckOrderAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCheckOrderAct();
            }
        });

        llDeviceAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDeviceAct();
            }
        });
    }

    @Override
    protected void onFindView(View view) {
        mUserHead = view.findViewById(R.id.iv_user_head);
        mUserName = view.findViewById(R.id.tv_user_name);
        mDepartment = view.findViewById(R.id.tv_department);
        mNews = view.findViewById(R.id.news);
        ivQrcode = view.findViewById(R.id.iv_qrcode);
        ivScan = view.findViewById(R.id.iv_scan);
        llQrcode = view.findViewById(R.id.ll_qrcode);
        ivSqrcode = view.findViewById(R.id.iv_sqrcode);
        llOrders = view.findViewById(R.id.ll_orders);
        llGetGoodsList = view.findViewById(R.id.ll_getGoodsList);
        llCreateOrder = view.findViewById(R.id.ll_createOrder);
        llOrderSummary = view.findViewById(R.id.ll_orderSummary);
        llCheckOrderAct = view.findViewById(R.id.ll_checkOrderAct);
        llDeviceAct = view.findViewById(R.id.ll_deviceAct);
        ll_sort_req = view.findViewById(R.id.ll_sort_req);
        ll_sort_in = view.findViewById(R.id.ll_sort_in);
        mUserName.setText(CacheUtil.getDeptName());
    }

    @Override
    protected void initData() {
        Log.d("zkf",CacheUtil.getAccessToken());
        if (TextUtils.isEmpty(CacheUtil.getAccessToken())) {
            toClass(mActivity, LoginActivity.class);
            getActivity().finish();
            return;
        }
/*
        if (!AndroidUtil.isServiceExisted(getActivity(), UpdatePositionService.class.getName())) {

            new RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    new RxPermissions(HomeFrag.this).request(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
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
*/


       /* mRequestUtil.doPost("s1/home", new RequestParams(), MainPageData.class, new RequestUtil.OnRequestFinishListener<MainPageData>() {
            @Override
            public void onRequestFail(int errorCode, String desc) {
                Log.e("requestParams", "errorCode " + errorCode + " desc " + desc);
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//                getActivity().finish();
            }

            @Override
            public void onRequestSuccess(MainPageData result) {
                //x.image().bind(mUserHead, );
                String picUrl = getResources().getString(R.string.res_url) +
                        result.getUser().getAvatar();
                Glide
                        .with(getActivity())
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

                PreferencesUtils.putString(mActivity,"assignEnable",result.getAssignEnable());
            }
        });*/
    }

    @Override
    public void onClick(View v) {

    }
}
