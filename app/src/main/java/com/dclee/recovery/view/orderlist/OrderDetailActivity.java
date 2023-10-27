package com.dclee.recovery.view.orderlist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.dclee.recovery.R;
import com.dclee.recovery.adapter.PicAdapter;
import com.dclee.recovery.adapter.ProductAdapter;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.bean.AppInfo;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.OrderProd;
import com.dclee.recovery.pojo.SearchLocationBean;
import com.dclee.recovery.util.CustomDatePicker;
import com.dclee.recovery.util.DateFormatUtils;
import com.dclee.recovery.util.DialogUtil;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.util.ToastUtil;
import com.dclee.recovery.view.create_order.CreateOrderActivity;
import com.dclee.recovery.view.purchase.OnSelectDateListener;

import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailActivity extends BaseActivity {
    private Button toGetOrder;
    private TextView pre_reach_time;
    private LinearLayout llReachTime;
    private TextView address_detail;
    private TextView order_code;
    private TextView appoint_time;
    private TextView create_time;
    private TextView name;
    private TextView phone;
    private int order_id;
    private RequestUtil requestUtil;
    private boolean isOrderReceive;
    private MapView mapview;
    private String cityName;
    private String addressName;
    private int orderStatus;
    private TextView realGoods;
    private View goodsDetail;
    private TextView tvWeight;
    private TextView prodNames;
    private Button toMap;
    private RecyclerView rvPic;

    private String mAddress;
    private PicAdapter mPicAdapter;
    private List<String> mPics = new ArrayList<>();

    @Override
    public void initData() {
        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("order_id", order_id);
        requestUtil.doPost("s1/orderDetail", requestParams, OrderBean.class, new RequestUtil.OnRequestFinishListener<OrderBean>() {
            @Override
            public void onRequestSuccess(final OrderBean result) {
                findViewById(R.id.todiag_phone).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri uri = Uri.parse("tel:" + result.getAddress().getPhone());
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
                goodsDetail.setVisibility(result.getOrder_state() == Config.STATE_FINISH ? View.VISIBLE : View.GONE);
                name.setText(result.getAddress().getContact());
                phone.setText(result.getAddress().getPhone());
                create_time.setText(result.getCreated_at());
                appoint_time.setText(result.getOrder_time());
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < result.getDetails().size(); i++) {
                    if (i != 0) {
                        stringBuilder.append("\n");
                    }
                    OrderProd prod = result.getDetails().get(i);
                    stringBuilder.append(prod.getProduct_name()).append(" ")
                            .append(prod.getWeight()).append(prod.getProduct_name().contains("瓶") ? "个" : "kg");
                }
                realGoods.setText(stringBuilder.toString());
                order_code.setText(String.valueOf(result.getOrder_sn()));
                if (result.getOrder_state() != Config.STATE_START && result.getOrder_state() != Config.STATE_UNRECEIVE) {
                    pre_reach_time.setText(result.getArrive_time());
                } else {pre_reach_time.setHint("请选择预约时间");
                    pre_reach_time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            toSelectDate(new OnSelectDateListener() {
                                @Override
                                public void onDateSelected(Date date, String dateStr) {
                                    pre_reach_time.setText(dateStr);
                                }
                            });
                        }
                    });
                }

                mAddress = result.getAddress().getProvinceName() + result.getAddress().getCityName()
                        + result.getAddress().getDistrictName() + result.getAddress().getAddress();

                address_detail.setText(result.getAddress().getProvinceName() + " " + result.getAddress().getCityName()
                        + " " + result.getAddress().getDistrictName() + " " + result.getAddress().getAddress());
                cityName = result.getAddress().getProvinceName() + " " + result.getAddress().getCityName();
                addressName = result.getAddress().getDistrictName() + " " + result.getAddress().getAddress();
                if (result.getOrder_state() == Config.STATE_UNRECEIVE) {
                    toGetOrder.setText("确定预约");
                } else if (result.getOrder_state() == Config.STATE_UNGETGOODS) {
                    toGetOrder.setText("确定取货");
                    //pre_reach_time.setText("已预约");
                    pre_reach_time.setText(result.getReceive_time());
                } else {
                    llReachTime.setVisibility(View.GONE);
                    toGetOrder.setVisibility(View.GONE);
                }
                orderStatus = result.getOrder_state();
                initMap();

                mPics.clear();
                if (!TextUtils.isEmpty(result.getImages())){
                    String[] images = result.getImages().split(",");
                    for (int i = 0 ; i < images.length;i++){
                        mPics.add(images[i]);
                    }
                }
                mPicAdapter.setDatas(mPics);
                mPicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {

            }
        });
    }


    private void toSelectDate(final OnSelectDateListener onSelectDateListener) {
        Calendar endDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Date currentDate = new Date();
        int hour = currentDate.getHours();
        int minute = currentDate.getMinutes();
        startDate.set(Calendar.HOUR_OF_DAY, currentDate.getHours());//控制时
        startDate.set(Calendar.MINUTE, currentDate.getMinutes());//控制分
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formatedStartTime = simpleDateFormat.format(startDate.getTimeInMillis());
        endDate.set(endDate.get(Calendar.YEAR) + 2, endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE));
        String formatedEndTime = simpleDateFormat.format(endDate.getTimeInMillis());

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        CustomDatePicker mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                onSelectDateListener.onDateSelected(new Date(timestamp), simpleDateFormat.format(new Date(timestamp)));
            }
        }, new Date().getTime(), endDate.getTimeInMillis());
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(false);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(false);
        mTimerPicker.show(System.currentTimeMillis());
//        TimePickerView datePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                onSelectDateListener.onDateSelected(date, simpleDateFormat.format(date));
//            }
//        })
//                .setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
//                .setCancelText("取消")//取消按钮文字
//                .setSubmitText("确定")//确认按钮文字
//                .setContentTextSize(16)
//                .setTitleSize(17)//标题文字大小
//                .setTitleText("")//标题文字
//                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(false)//是否循环滚动
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
//                .setCancelColor(getResources().getColor(R.color.textGrayColor))//取消按钮文字颜色
//                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
//                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(false)//是否显示为对话框样式
//                .build();
//        datePicker.show(true);
    }

    private void initMap() {
        GeoCoder search = GeoCoder.newInstance();

        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有检索到结果
                    ToastUtil.showToast(getActivity(), "没有搜索到地址，请联系用户，确认真实地址");
                    return;
                }
                double latitude = result.getLocation().latitude;
                double longitude = result.getLocation().longitude;
                MapStatus.Builder builder = new MapStatus.Builder();
                LatLng latLng = new LatLng(latitude, longitude);
                builder.target(latLng).zoom(18.0f);
                mapview.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                BitmapDescriptor searchBdA = BitmapDescriptorFactory.fromResource(R.mipmap.red_loca);
                MarkerOptions option = new MarkerOptions().icon(searchBdA).position(latLng);
//                option.
                mapview.getMap().addOverlay(option);
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    ToastUtil.showToast(getActivity(), "没有搜索到地址，请联系用户，确认真实地址");
                    return;
                }
                //获取反向地理编码结果
            }
        };
        search.setOnGetGeoCodeResultListener(listener);
        search.geocode(new GeoCodeOption()
                .city(cityName)
                .address(addressName));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void initView() {
        isOrderReceive = getIntent().getBooleanExtra("isOrderReceive", false);
        realGoods = findViewById(R.id.real_goods);
        requestUtil = new RequestUtil(this);
        goodsDetail = findViewById(R.id.goods_detail);
        toMap = findViewById(R.id.toMap);
        order_id = getIntent().getIntExtra("order_id", 0);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        create_time = findViewById(R.id.create_time);
        appoint_time = findViewById(R.id.appoint_time);
        mapview = findViewById(R.id.mapview);
        order_code = findViewById(R.id.order_code);
        address_detail = findViewById(R.id.address_detail);
        pre_reach_time = findViewById(R.id.pre_reach_time);
        llReachTime = findViewById(R.id.ll_reach_time);
        toGetOrder = findViewById(R.id.toGetOrder);
        address_detail.setText(getIntent().getStringExtra("addressDetail"));
        tvWeight = findViewById(R.id.tv_weight);
        tvWeight.setText(getIntent().getStringExtra("weightDetail"));
        prodNames = findViewById(R.id.prod_names);
        rvPic = findViewById(R.id.rv_pic);
        prodNames.setText(getIntent().getStringExtra("prodNames"));
        if (isOrderReceive) {
            toGetOrder.setVisibility(View.GONE);
        }

        String assignEnable = PreferencesUtils.getString(mActivity,"assignEnable","0");
        if(!"0".equals(assignEnable)){
            toGetOrder.setVisibility(View.GONE);
        }

        toGetOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderStatus == Config.STATE_UNRECEIVE) {
                    if (TextUtils.isEmpty(pre_reach_time.getText().toString())) {
                        toSelectDate(new OnSelectDateListener() {
                            @Override
                            public void onDateSelected(Date date, String dateStr) {
                                pre_reach_time.setText(dateStr);
                                doGetOrder(dateStr);
                            }
                        });
                    } else {
                        doGetOrder(pre_reach_time.getText().toString());
                    }
                } else {
                    startActivityForResult(new Intent(getActivity(), CreateOrderActivity.class).putExtra("isGetGoods", true).putExtra("order_id", order_id), OrderAdapter.REQ_RECV_GOODS);
                }
            }
        });
        toMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBaiduMap();
            }
        });

        rvPic.setLayoutManager(new LinearLayoutManager(this));
        mPicAdapter = new PicAdapter(this);
        mPicAdapter.setDatas(mPics);
        rvPic.setAdapter(mPicAdapter);
    }

    private void doGetOrder(final String selectTime) {
        DialogUtil.showComfirmDialog(getActivity(), "是否确认接此订单？", new DialogUtil.OnComfirmClickListener() {
            @Override
            public void onComfirmClick() {
                RequestParams requestParams = new RequestParams();
                requestParams.addParameter("order_id", order_id);
                requestParams.addParameter("arrive_time", selectTime + ":00");
                requestUtil.doPost("s1/orderCreate", requestParams, Object.class, new RequestUtil.OnRequestFinishListener<Object>() {

                    @Override
                    public void onRequestSuccess(Object result) {
                        ToastUtil.showToast(getActivity(), "确认接单成功！");
                        toGetOrder.setText("确定取货");
                        orderStatus = Config.STATE_UNGETGOODS;
                    }

                    @Override
                    public void onRequestFail(int errorCode, String desc) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OrderAdapter.REQ_RECV_GOODS && resultCode == RESULT_OK) {
            initData();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    private void openBaiduMap() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("baidumap://map/direction?origin=我的位置&destination="
                + mAddress));
        startActivity(intent);
    }

    /**
     * 检查手机上是否安装的指定的软件
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkMapAppsIsExist(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                Log.e("xzw", packName);
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
