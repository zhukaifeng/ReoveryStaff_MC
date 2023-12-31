package com.dclee.recovery.view.sorting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;

import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.base.Response;
import com.dclee.recovery.bean.event.UploadPic;
import com.dclee.recovery.pojo.DepartBean;
import com.dclee.recovery.pojo.DeptInfoBean;
import com.dclee.recovery.pojo.ErrorMsgBean;
import com.dclee.recovery.pojo.OrderCreateBean;
import com.dclee.recovery.pojo.PictureBBean;
import com.dclee.recovery.pojo.SortDefaultBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.pojo.SortReqDetailBean;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.RequestUtil;
import com.lzy.imagepicker.ImagePicker;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class AddSortingReqActivity extends BaseActivity {

    private RecyclerView selectedImgRecycler;
    private TypeImageAdapter typeImageAdapter;
    private RequestUtil requestUtil;
    private TextView tv_order;
    private EditText tv_count;
    private EditText edt_remark;
    private TextView tv_department;
    private TextView tv_category;
    private TextView tv_product;
    private TextView tv_position;
    private TextView tv_inventory;
    private TextView tv_type;
    private TextView tv_save;
    private CheckBox cb_yes;
    private CheckBox cb_no;
    private OrderCreateBean mOrders;
    private RequestUtil mRequestUtil;
    private List<OrderCreateBean.DataDTO> orderData = new ArrayList<>();

    private List<String> listForSpinnerType = new ArrayList<>();
    private ArrayAdapter<String> adapterForSpinnerType;
    private int type = 0;
    private OrderCreateBean.DataDTO mData;
    private List<String> mPics = new ArrayList<>();
    private int index = 0;
    private int selectType = 0;
    private String receiveId;
    private PopupWindow pop_no;
    private PopupWindow pop_depart;
    private PopupWindow pop_category;
    private PopupWindow pop_product;

    private int mCreateMode = 0;

    private List<DepartBean.DataBean> mDepartDataList = new ArrayList<>();
    private DepartBean.DataBean mSelectDepart;
    private DeptInfoBean mDeptInfoBean;
    private DeptInfoBean.DataDTO mSelectDeptInfoData;
    private DeptInfoBean.DataDTO.ChildrenDTO mSelectProductData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_sorting_req;
    }

    @Override
    public void initView() {
        tv_order = findViewById(R.id.tv_order);
        tv_count = findViewById(R.id.tv_count);
        edt_remark = findViewById(R.id.edt_remark);
        cb_yes = findViewById(R.id.cb_yes);
        cb_no = findViewById(R.id.cb_no);
        tv_department = findViewById(R.id.tv_department);
        tv_category = findViewById(R.id.tv_category);
        tv_product = findViewById(R.id.tv_product);
        tv_position = findViewById(R.id.tv_position);
        tv_inventory = findViewById(R.id.tv_inventory);
        tv_type = findViewById(R.id.tv_type);
        tv_save = findViewById(R.id.tv_save);
        receiveId = getIntent().getStringExtra("id");
        mRequestUtil = new RequestUtil(this);
        tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectSn();
            }
        });
        tv_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCreateMode == 0) {
                    showSelectDepart();
                }
            }
        });
        tv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCreateMode == 0) {
                    if (mSelectDepart == null) {
                        Toast.makeText(AddSortingReqActivity.this, "请先选择部门", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (mCreateMode == 0 && null != mSelectDepart) {
                    showSelectCategory();
                }
            }
        });
        tv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCreateMode == 0) {
                    if (mSelectDepart == null) {
                        Toast.makeText(AddSortingReqActivity.this, "请先选择部门", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mSelectDeptInfoData == null) {
                        Toast.makeText(AddSortingReqActivity.this, "请先选择类别", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (mCreateMode == 0 && null != mSelectDepart && null != mSelectDeptInfoData) {
                    showSelectProduct();
                }
            }
        });

        requestUtil = new RequestUtil(this);
        selectedImgRecycler = findViewById(R.id.selected_img_recycler);
        selectedImgRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        typeImageAdapter = new TypeImageAdapter(this, requestUtil);
        // typeImageAdapter.setUploadedImages(receiveParam.getImages());
        selectedImgRecycler.setAdapter(typeImageAdapter);


        listForSpinnerType.add("请选择领用类型");
        listForSpinnerType.add("库存领用");
        listForSpinnerType.add("来货分拣");
        adapterForSpinnerType = new ArrayAdapter<>(AddSortingReqActivity.this, R.layout.item_for_custom_spinner, listForSpinnerType);
//        spinner_type.setAdapter(adapterForSpinnerType);
//        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                type = position;
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        cb_yes.setChecked(true);
        selectType = 1;
        cb_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectType = 1;
                    cb_yes.setChecked(true);
                    cb_no.setChecked(false);
                }
            }
        });
        cb_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectType = 2;
                    cb_yes.setChecked(false);
                    cb_no.setChecked(true);
                }
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mCreateMode == 0) {

                    if (mSelectDepart == null) {
                        Toast.makeText(AddSortingReqActivity.this, "请选择部门", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mSelectDeptInfoData == null) {
                        Toast.makeText(AddSortingReqActivity.this, "请选择类别", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mSelectProductData == null) {
                        Toast.makeText(AddSortingReqActivity.this, "请选择品名", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(tv_count.getText().toString())) {
                        Toast.makeText(AddSortingReqActivity.this, "请填写领用数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (type == 0) {
                        Toast.makeText(AddSortingReqActivity.this, "请选择领用类型", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!cb_no.isChecked() && !cb_yes.isChecked()) {
                        Toast.makeText(AddSortingReqActivity.this, "请选择是否实时出库", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Integer.parseInt(tv_count.getText().toString()) >
                            Integer.parseInt(tv_inventory.getText().toString())) {
                        Toast.makeText(AddSortingReqActivity.this, "领用数量不能大于库存", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    createOrder2();

                } else {
                    if (mData == null) {
                        Toast.makeText(AddSortingReqActivity.this, "请选择订单", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (type == 0) {
                        Toast.makeText(AddSortingReqActivity.this, "请选择领用类型", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!cb_no.isChecked() && !cb_yes.isChecked()) {
                        Toast.makeText(AddSortingReqActivity.this, "请选择是否实时出库", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (typeImageAdapter.getUploadedImages().size() > 0) {
                        uploadFile();
                    } else {
                        createOrder();
                    }
                }


            }
        });
        tv_type.setText("库存分拣");
        type = 1;
    }


    private PopupSelectProductAdapter proAdapter;

    private void showSelectProduct() {
        View contentView = LayoutInflater.from(AddSortingReqActivity.this).inflate(R.layout.popup_select_depart, null);
        if (null != pop_product) {
            if (pop_product.isShowing()) {
                return;
            }
            if (null != mSelectDeptInfoData) {
                proAdapter.setDatas(mSelectDeptInfoData.getChildren());
                proAdapter.notifyDataSetChanged();
            }
            pop_product.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        } else {
            pop_product = new PopupWindow(this);
            final RecyclerView rv_category = contentView.findViewById(R.id.rv_person);
            LinearLayout linear_content = contentView.findViewById(R.id.linear_content);
            proAdapter = new PopupSelectProductAdapter(this);
            rv_category.setLayoutManager(new LinearLayoutManager(this));
            rv_category.setAdapter(proAdapter);

            proAdapter.setDatas(mSelectDeptInfoData.getChildren());


            proAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mSelectProductData = mSelectDeptInfoData.getChildren().get(position);
                    tv_product.setText(mSelectProductData.getProductName());
                    tv_position.setText(mSelectProductData.getStockName());
                    tv_inventory.setText(mSelectProductData.getStockWeight());
                    pop_product.dismiss();
                }
            });
            linear_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop_product.dismiss();
                }
            });
            pop_product.setOutsideTouchable(false);
            pop_product.setTouchable(true);
            pop_product.setContentView(contentView);
            DisplayMetrics dm = new DisplayMetrics();//屏幕度量
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screen_width = dm.widthPixels;//宽度
            int screen_height = dm.heightPixels;//高度
            pop_product.setWidth(screen_width);
            pop_product.setHeight(screen_height);
            pop_product.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        }


    }

    private PopupSelectCategoryAdapter catAdapter;

    private void showSelectCategory() {
        View contentView = LayoutInflater.from(AddSortingReqActivity.this).inflate(R.layout.popup_select_depart, null);
        if (null != pop_category) {
            if (pop_category.isShowing()) {
                return;
            }
            if (null != mDeptInfoBean) {
                catAdapter.setDatas(mDeptInfoBean.getData());
                catAdapter.notifyDataSetChanged();
            }

            pop_category.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        } else {
            pop_category = new PopupWindow(this);
            catAdapter = new PopupSelectCategoryAdapter(this);
            final RecyclerView rv_category = contentView.findViewById(R.id.rv_person);
            LinearLayout linear_content = contentView.findViewById(R.id.linear_content);
            rv_category.setLayoutManager(new LinearLayoutManager(this));
            rv_category.setAdapter(catAdapter);

            catAdapter.setDatas(mDeptInfoBean.getData());


            catAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mSelectDeptInfoData = mDeptInfoBean.getData().get(position);
                    tv_category.setText(mSelectDeptInfoData.getCategoryName());
                    Log.d("zkf", "ssss:" + mSelectDeptInfoData.getCategoryName());

                    mSelectProductData = null;
                    tv_product.setText("");
                    tv_position.setText("");
                    tv_inventory.setText("");

                    pop_category.dismiss();
                }
            });
            linear_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop_category.dismiss();
                }
            });
            pop_category.setOutsideTouchable(false);
            pop_category.setTouchable(true);
            pop_category.setContentView(contentView);
            DisplayMetrics dm = new DisplayMetrics();//屏幕度量
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screen_width = dm.widthPixels;//宽度
            int screen_height = dm.heightPixels;//高度
            pop_category.setWidth(screen_width);
            pop_category.setHeight(screen_height);
            pop_category.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        }


    }

    private PopupDepartAdapter departAdapter;

    private void showSelectDepart() {
        View contentView = LayoutInflater.from(AddSortingReqActivity.this).inflate(R.layout.popup_select_depart, null);
        if (null != pop_depart) {
            if (pop_depart.isShowing()) {
                return;
            }
            pop_depart.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        } else {
            pop_depart = new PopupWindow(this);
            final RecyclerView rv_category = contentView.findViewById(R.id.rv_person);
            LinearLayout linear_content = contentView.findViewById(R.id.linear_content);
            departAdapter = new PopupDepartAdapter(this);
            rv_category.setLayoutManager(new LinearLayoutManager(this));
            rv_category.setAdapter(departAdapter);

            departAdapter.setDatas(mDepartDataList);


            departAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mSelectDepart = mDepartDataList.get(position);
                    tv_department.setText(mSelectDepart.getDeptName());
                    getDepartInfo(mSelectDepart.getDeptId());

                    mSelectDeptInfoData = null;
                    mSelectProductData = null;
                    tv_category.setText("");
                    tv_product.setText("");
                    tv_position.setText("");
                    tv_inventory.setText("");

                    pop_depart.dismiss();
                }
            });
            linear_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop_depart.dismiss();
                }
            });
            pop_depart.setOutsideTouchable(false);
            pop_depart.setTouchable(true);
            pop_depart.setContentView(contentView);
            DisplayMetrics dm = new DisplayMetrics();//屏幕度量
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screen_width = dm.widthPixels;//宽度
            int screen_height = dm.heightPixels;//高度
            pop_depart.setWidth(screen_width);
            pop_depart.setHeight(screen_height);
            pop_depart.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        }
    }

    private void getDepartInfo(String id) {
        final LoadingDialog loadingDialog = new LoadingDialog(AddSortingReqActivity.this)
                .setLoadingText("");
        loadingDialog.show();
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParameter("deptId", id);
                        mRequestUtil.doPostWithToken2("mobile/orderReceive/productTreeListByDept", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<String>() {


                            @Override
                            public void onRequestSuccess(String result) {
                                Log.d("zkf", "result222:" + result);
                                if (result.contains("操作成功")) {
                                    mDeptInfoBean = FastJsonTools.get(result, DeptInfoBean.class);
                                }
                                loadingDialog.close();

                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {
                                loadingDialog.close();
                            }
                        });
                    }
                });


    }


    private void showSelectSn() {
        View contentView = LayoutInflater.from(AddSortingReqActivity.this).inflate(R.layout.popup_select_sn, null);
        if (null != pop_no) {
            if (pop_no.isShowing()) {
                return;
            }
            pop_no.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        } else {
            pop_no = new PopupWindow(this);
            final RecyclerView rv_category = contentView.findViewById(R.id.rv_person);
            LinearLayout linear_content = contentView.findViewById(R.id.linear_content);
            PopupSnAdapter snAdapter = new PopupSnAdapter(this);
            rv_category.setLayoutManager(new LinearLayoutManager(this));
            rv_category.setAdapter(snAdapter);

            snAdapter.setDatas(orderData);


            snAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mData = orderData.get(position);
                    tv_order.setText(orderData.get(position).getOrderNo());
                    tv_department.setText(orderData.get(position).getDeptName());
                    tv_category.setText(orderData.get(position).getCategoryName());
                    tv_product.setText(orderData.get(position).getProductName());
                    tv_position.setText(orderData.get(position).getStockAddressName());
                    tv_inventory.setText(String.valueOf(orderData.get(position).getStock()));
                    tv_count.setText(orderData.get(position).getNetWeight());
                    tv_type.setText("来货分拣");
                    type = 2;
                    mCreateMode = 1;
                    tv_count.setEnabled(false);
                    pop_no.dismiss();
                }
            });
            linear_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop_no.dismiss();
                }
            });
            pop_no.setOutsideTouchable(false);
            pop_no.setTouchable(true);
            pop_no.setContentView(contentView);
            DisplayMetrics dm = new DisplayMetrics();//屏幕度量
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screen_width = dm.widthPixels;//宽度
            int screen_height = dm.heightPixels;//高度
            pop_no.setWidth(screen_width);
            pop_no.setHeight(screen_height);
            pop_no.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        }
    }

    private void getDataDetail() {
//        new RxPermissions(this).request(Manifest.permission.INTERNET)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        RequestParams requestParams = new RequestParams();
//                        requestParams.addParameter("receiveId", receiveId);
//                        mRequestUtil.doPostWithToken2("mobile/orderReceive/detail", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<String>() {
//
//
//                            @Override
//                            public void onRequestSuccess(String result) {
//                               Log.d("zkf","result:" + result);
//                                SortReqDetailBean data = FastJsonTools.get(result, SortReqDetailBean.class);
//                                tv_department.setText(data.getData().getDeptName());
//                                tv_category.setText(data.getData().getCategoryName());
//                                tv_product.setText(data.getData().getProductName());
//                                tv_position.setText(data.getData().getStockAddressName());
////                                tv_inventory.setText(String.valueOf(data.getData().getStock()));
////                                tv_count.setText(data.getData().getNetWeight());
//                               // edt_remark.setText();
//
//                            }
//
//                            @Override
//                            public void onRequestFail(int errorCode, String desc) {
//
//                            }
//                        });
//                    }
//                });

    }

    private void uploadFile() {
        Log.d("zkf", "index:" + index);
        File file = new File(typeImageAdapter.getUploadedImages().get(index));
        RequestParams requestParams = new RequestParams();
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        final LoadingDialog loadingDialog = new LoadingDialog(AddSortingReqActivity.this)
                .setLoadingText("图片上传中...");
        loadingDialog.show();
        mRequestUtil.postFile("app/common/uploadPic", requestParams, PictureBBean.class, new RequestUtil.OnRequestFinishListener<PictureBBean>() {
            @Override
            public void onRequestSuccess(PictureBBean result) {
                Log.d("zkf", "pic url:" + result.getUrl());
                Log.d("zkf", "post UploadPic");
                loadingDialog.close();
                mPics.add(result.getId());
                index++;
                if (index < typeImageAdapter.getUploadedImages().size()) {
                    uploadFile();
                } else {
                    Log.d("zkf", "index1:" + index);
                    Log.d("zkf", "post complete");
                    createOrder();
                }

                // EventBus.getDefault().post(new UploadPic(result));
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {
                loadingDialog.close();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void createOrder2() {
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParameter("receiveType", type);
                        requestParams.addParameter("realTimeOutBound", selectType);
                        if (!TextUtils.isEmpty(edt_remark.getText().toString())) {
                            requestParams.addParameter("remark", edt_remark.getText().toString());
                        }
                        requestParams.addParameter("deptId", mSelectDepart.getDeptId());
                        requestParams.addParameter("categoryTypeId", mSelectDeptInfoData.getCategoryTypeId());
                        requestParams.addParameter("productTypeId", mSelectProductData.getProductTypeId());
                        requestParams.addParameter("stockAddressId", mSelectProductData.getStockAddressId());
                        requestParams.addParameter("receiveWeight", tv_count.getText().toString());


                        final LoadingDialog loadingDialog = new LoadingDialog(AddSortingReqActivity.this)
                                .setLoadingText("保存中...");
                        mRequestUtil.doPostWithToken2("mobile/orderReceive/addOrderReceive", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<String>() {

                            @SuppressLint("CheckResult")
                            @Override
                            public void onRequestSuccess(String result) {
                                loadingDialog.close();
                                if (result.contains("操作成功")) {
                                    Toast.makeText(AddSortingReqActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    ErrorMsgBean msgBean = FastJsonTools.get(result, ErrorMsgBean.class);
                                    String errorMsg = msgBean.getMsg();
                                    if (TextUtils.isEmpty(errorMsg)) {
                                        errorMsg = "保存失败";
                                    }
                                    Toast.makeText(AddSortingReqActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {

                            }
                        });
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void createOrder() {

        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParameter("receiveType", type);
                        requestParams.addParameter("realTimeOutBound", selectType);
                        requestParams.addParameter("orderId", mData.getOrderId());
                        if (!TextUtils.isEmpty(edt_remark.getText().toString())) {
                            requestParams.addParameter("remark", edt_remark.getText().toString());
                        }

                        if (mPics.size() > 0) {

                        }
                        final LoadingDialog loadingDialog = new LoadingDialog(AddSortingReqActivity.this)
                                .setLoadingText("保存中...");
                        mRequestUtil.doPostWithToken2("mobile/orderReceive/addOrderReceive", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<String>() {

                            @Override
                            public void onRequestSuccess(String result) {
                                loadingDialog.close();
                                if (result.contains("操作成功")) {
                                    Toast.makeText(AddSortingReqActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    ErrorMsgBean msgBean = FastJsonTools.get(result, ErrorMsgBean.class);
                                    String errorMsg = msgBean.getMsg();
                                    if (TextUtils.isEmpty(errorMsg)) {
                                        errorMsg = "保存失败";
                                    }
                                    Toast.makeText(AddSortingReqActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {

                            }
                        });
                    }
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void initData() {
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();

                        mRequestUtil.doGet("mobile/order/listWithAdd", requestParams, new RequestUtil.OnRequestFinishListener<String>() {
                            @Override
                            public void onRequestSuccess(String result) {
                                Log.d("zkf", "result222:" + result);
                                mOrders = FastJsonTools.get(result, OrderCreateBean.class);
                                orderData.addAll(mOrders.getData());
                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {
                                Log.d("zkf", "desc:" + desc);

                            }

                        });
                    }
                });

        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();

                        mRequestUtil.doGet("mobile/orderReceive/selectDeptList", requestParams, new RequestUtil.OnRequestFinishListener<String>() {
                            @Override
                            public void onRequestSuccess(String result) {
                                Log.d("zkf", "result 111:" + result);
                                DepartBean data = FastJsonTools.get(result, DepartBean.class);
                                mDepartDataList.addAll(data.getData());
//                                orderData.addAll(mOrders.getData());
                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {
                                Log.d("zkf", "desc:" + desc);

                            }

                        });
                    }
                });


    }

    public static <T> List<T> getDatas(String jsonStriung, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonStriung, cls);
        } catch (Exception E) {
            // TODO: handle exception
            Log.d("zkf", "ee:" + E.getMessage());
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && resultCode != ImagePicker.RESULT_CODE_ITEMS) {
            return;
        }


        typeImageAdapter.onActivityResult(requestCode, resultCode, data);
    }

}
