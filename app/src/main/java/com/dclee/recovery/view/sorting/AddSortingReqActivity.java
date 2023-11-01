package com.dclee.recovery.view.sorting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.dclee.recovery.bean.event.UploadPic;
import com.dclee.recovery.pojo.OrderCreateBean;
import com.dclee.recovery.pojo.PictureBBean;
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
    private Spinner spinner_order;
    private TextView tv_count;
    private EditText edt_remark;
    private TextView tv_department;
    private TextView tv_category;
    private TextView tv_product;
    private TextView tv_position;
    private TextView tv_inventory;
    private Spinner spinner_type;
    private TextView tv_save;
    private CheckBox cb_yes;
    private CheckBox cb_no;
    private OrderCreateBean mOrders;
    private RequestUtil mRequestUtil;
    private ArrayAdapter<String> adapterForSpinnerOrder;
    private List<String> listForSpinner = new ArrayList<>();
    private List<OrderCreateBean.DataDTO> orderData = new ArrayList<>();

    private List<String> listForSpinnerType = new ArrayList<>();
    private ArrayAdapter<String> adapterForSpinnerType;
    private int type = 0;
    private OrderCreateBean.DataDTO mData;
    private List<String> mPics = new ArrayList<>();
    private  int index = 0;
    private  int selectType = 0;
    private boolean isEdit =false;
    private  String receiveId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_sorting_req;
    }

    @Override
    public void initView() {
        spinner_order = findViewById(R.id.spinner_order);
        tv_count = findViewById(R.id.tv_count);
        edt_remark = findViewById(R.id.edt_remark);
        cb_yes = findViewById(R.id.cb_yes);
        cb_no = findViewById(R.id.cb_no);
        tv_department = findViewById(R.id.tv_department);
        tv_category = findViewById(R.id.tv_category);
        tv_product = findViewById(R.id.tv_product);
        tv_position = findViewById(R.id.tv_position);
        tv_inventory = findViewById(R.id.tv_inventory);
        spinner_type = findViewById(R.id.spinner_type);
        tv_save = findViewById(R.id.tv_save);
        isEdit = getIntent().getBooleanExtra("isEdit",false);
        receiveId = getIntent().getStringExtra("id");
        mRequestUtil = new RequestUtil(this);


        requestUtil = new RequestUtil(this);
        selectedImgRecycler = findViewById(R.id.selected_img_recycler);
        selectedImgRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        typeImageAdapter = new TypeImageAdapter(this, requestUtil);
        // typeImageAdapter.setUploadedImages(receiveParam.getImages());
        selectedImgRecycler.setAdapter(typeImageAdapter);

        if (isEdit){
         //   getDataDetail();

        }else{
            listForSpinner.add("请选择");
            adapterForSpinnerOrder = new ArrayAdapter<>(AddSortingReqActivity.this, R.layout.item_for_custom_spinner, listForSpinner);
            spinner_order.setAdapter(adapterForSpinnerOrder);
            spinner_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (orderData.size() > 1){
                        if (position ==0){
                            mData = null;
                            tv_department.setText("");
                            tv_category.setText("");
                            tv_product.setText("");
                            tv_position.setText("");
                            tv_inventory.setText("");
                            tv_count.setText("");
                        }else {
                            Log.d("zkf","posiiiii:" + position);
                            mData = orderData.get(position-1);
                            tv_department.setText(orderData.get(position-1).getDeptName());
                            tv_category.setText(orderData.get(position-1).getCategoryName());
                            tv_product.setText(orderData.get(position-1).getProductName());
                            tv_position.setText(orderData.get(position-1).getStockAddressName());
                            tv_inventory.setText(String.valueOf(orderData.get(position-1).getStock()));
                            tv_count.setText(orderData.get(position-1).getNetWeight());
                        }

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            listForSpinnerType.add("请选择领用类型");
            listForSpinnerType.add("库存领用");
            listForSpinnerType.add("来货分拣");
            adapterForSpinnerType = new ArrayAdapter<>(AddSortingReqActivity.this, R.layout.item_for_custom_spinner, listForSpinnerType);
            spinner_type.setAdapter(adapterForSpinnerType);
            spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    type = position;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            cb_yes.setChecked(true);
            selectType = 1;
            cb_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        selectType = 1;
                        cb_yes.setChecked(true);
                        cb_no.setChecked(false);
                    }
                }
            });
            cb_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        selectType = 2;
                        cb_yes.setChecked(false);
                        cb_no.setChecked(true);
                    }
                }
            });

            tv_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mData == null){
                        Toast.makeText(AddSortingReqActivity.this,"请选择订单",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (type == 0){
                        Toast.makeText(AddSortingReqActivity.this,"请选择领用类型",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!cb_no.isChecked()&&!cb_yes.isChecked()){
                        Toast.makeText(AddSortingReqActivity.this,"请选择是否实时出库",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (typeImageAdapter.getUploadedImages().size()>0){
                        uploadFile();
                    }else {
                        createOrder();
                    }


                }
            });
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
        Log.d("zkf","index:" + index);
        File file =  new File(typeImageAdapter.getUploadedImages().get(index));
        RequestParams requestParams = new RequestParams();
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        final LoadingDialog loadingDialog = new LoadingDialog(AddSortingReqActivity.this)
                .setLoadingText("图片上传中...");
        loadingDialog.show();
        mRequestUtil.postFile("app/common/uploadPic", requestParams, PictureBBean.class, new RequestUtil.OnRequestFinishListener<PictureBBean>() {
            @Override
            public void onRequestSuccess(PictureBBean result) {
                Log.d("zkf","pic url:" + result.getUrl());
                Log.d("zkf","post UploadPic");
                loadingDialog.close();
                mPics.add(result.getId());
                index++;
                if (index <typeImageAdapter.getUploadedImages().size()){
                    uploadFile();
                }else {
                    Log.d("zkf","index1:" + index);
                    Log.d("zkf","post complete");
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

    private void createOrder() {

        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParameter("receiveType", type);
                        requestParams.addParameter("realTimeOutBound", selectType);
                        requestParams.addParameter("orderId",mData.getOrderId());
                        if (!TextUtils.isEmpty(edt_remark.getText().toString())){
                            requestParams.addParameter("remark",edt_remark.getText().toString());
                        }

                        if (mPics.size()>0){

                        }
                        final LoadingDialog loadingDialog = new LoadingDialog(AddSortingReqActivity.this)
                                .setLoadingText("保存中...");
                        mRequestUtil.doPostWithToken("mobile/orderReceive/addOrderReceive", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<SortInListBean>() {

                            @Override
                            public void onRequestSuccess(SortInListBean result) {
                                loadingDialog.close();
                                Toast.makeText(AddSortingReqActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                                finish();
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
                                mOrders = FastJsonTools.get(result, OrderCreateBean.class);
                                orderData.addAll(mOrders.getData());
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
