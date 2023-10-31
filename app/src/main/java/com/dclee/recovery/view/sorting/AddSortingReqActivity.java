package com.dclee.recovery.view.sorting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;

import com.dclee.recovery.base.Response;
import com.dclee.recovery.pojo.OrderCreateBean;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.RequestUtil;
import com.lzy.imagepicker.ImagePicker;
import com.tbruyelle.rxpermissions2.RxPermissions;


import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class AddSortingReqActivity extends BaseActivity {

    private RecyclerView selectedImgRecycler;
    private TypeImageAdapter typeImageAdapter;
    private RequestUtil requestUtil;
    private Spinner spinner_order;
    private EditText edt_count;
    private EditText edt_remark;
    private TextView tv_department;
    private Spinner spinner_category;
    private Spinner spinner_product;
    private Spinner spinner_position;
    private Spinner spinner_inventory;
    private Spinner spinner_type;
    private CheckBox cb_yes;
    private CheckBox cb_no;
    private OrderCreateBean mOrders;
    private RequestUtil mRequestUtil;
    private ArrayAdapter<String> adapterForSpinnerOrder;
    private List<String> listForSpinner = new ArrayList<>();
    private List<OrderCreateBean.DataDTO> orderData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_sorting_req;
    }

    @Override
    public void initView() {
        spinner_order = findViewById(R.id.spinner_order);
        edt_count = findViewById(R.id.edt_count);
        edt_remark = findViewById(R.id.edt_remark);
        cb_yes = findViewById(R.id.cb_yes);
        cb_no = findViewById(R.id.cb_no);
        tv_department = findViewById(R.id.tv_department);
        spinner_category = findViewById(R.id.spinner_category);
        spinner_product = findViewById(R.id.spinner_product);
        spinner_position = findViewById(R.id.spinner_position);
        spinner_inventory = findViewById(R.id.spinner_inventory);
        spinner_type = findViewById(R.id.spinner_type);
        mRequestUtil = new RequestUtil(this);


        requestUtil = new RequestUtil(this);
        selectedImgRecycler = findViewById(R.id.selected_img_recycler);
        selectedImgRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        typeImageAdapter = new TypeImageAdapter(this, requestUtil);
        // typeImageAdapter.setUploadedImages(receiveParam.getImages());
        selectedImgRecycler.setAdapter(typeImageAdapter);

        listForSpinner.add("请选择");
        adapterForSpinnerOrder = new ArrayAdapter<>(AddSortingReqActivity.this, R.layout.item_for_custom_spinner, listForSpinner);
        spinner_order.setAdapter(adapterForSpinnerOrder);
        spinner_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (orderData.size() > 0)
                    tv_department.setText(orderData.get(position).getDeptName());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                                mOrders = FastJsonTools.get(result, OrderCreateBean.class);
                                orderData.addAll(mOrders.getData());
                                listForSpinner.clear();
                                for (OrderCreateBean.DataDTO beanData : mOrders.getData()) {
                                    listForSpinner.add(beanData.getOrderNo());
                                }
                                adapterForSpinnerOrder.notifyDataSetChanged();

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
