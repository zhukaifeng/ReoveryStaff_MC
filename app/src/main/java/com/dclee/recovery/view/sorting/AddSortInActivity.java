package com.dclee.recovery.view.sorting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderCreateBean;
import com.dclee.recovery.pojo.PictureBBean;
import com.dclee.recovery.pojo.SortDefaultBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.pojo.SortReqDetailBean;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.view.purchase.SortRequestBean;
import com.lzy.imagepicker.ImagePicker;
import com.sunmi.utils.DoubleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


public class AddSortInActivity extends BaseActivity {

    private TextView tv_back;
    private TextView tv_add_continue;
    private TextView tv_add;
    private TextView tv_netweight;
    private EditText edt_weight;
    private TextView tv_type;
    private Spinner spinner_operate;
    private TextView tv_weight;
    private EditText edt_buckle;
    private RequestUtil mRequestUtil;
    private String receiveId;
    private ArrayAdapter<String> adapterForSpinnerPerson;
    private List<String> listForPersonSpinner = new ArrayList<>();
    private SortDefaultBean mSortDefaultBean;
    private PopupWindow pop;
    private int mCurrentSelectIndex = 0;
    private SortDefaultBean.DataDTO.SysProductTypeParentChooseListDTO.ChildrenDTO mSelectType;
    private RecyclerView selectedImgRecycler;
    private TypeImageAdapter typeImageAdapter;
    private List<String> mPics = new ArrayList<>();
    private int index = 0;
    private SortDefaultBean.DataDTO.SysUserListDTO mOperateBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_sortin;
    }

    @Override
    public void initView() {
        tv_back = findViewById(R.id.tv_back);
        tv_add_continue = findViewById(R.id.tv_add_continue);
        tv_add = findViewById(R.id.tv_add);
        tv_netweight = findViewById(R.id.tv_netweight);
        edt_weight = findViewById(R.id.edt_weight);
        tv_type = findViewById(R.id.tv_type);
        edt_buckle = findViewById(R.id.edt_buckle);
        selectedImgRecycler = findViewById(R.id.selected_img_recycler);
        selectedImgRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        typeImageAdapter = new TypeImageAdapter(this, mRequestUtil);
        // typeImageAdapter.setUploadedImages(receiveParam.getImages());
        selectedImgRecycler.setAdapter(typeImageAdapter);
        edt_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(edt_weight.getText().toString()) &&
                        !TextUtils.isEmpty(edt_buckle.getText().toString())) {
                    double weight = Double.parseDouble(edt_weight.getText().toString());
                    double buckle = Double.parseDouble(edt_buckle.getText().toString());
//                    if (weight<buckle){
//                        Toast.
//                    }
                    double netweight = DoubleUtils.sub(weight, buckle);
                    tv_netweight.setText(String.valueOf(netweight));
                }
            }
        });
        edt_buckle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double weight = Double.parseDouble(edt_weight.getText().toString());
                double buckle = Double.parseDouble(edt_buckle.getText().toString());
//                    if (weight<buckle){
//                        Toast.
//                    }
                double netweight = DoubleUtils.sub(weight, buckle);
                tv_netweight.setText(String.valueOf(netweight));
            }
        });
        listForPersonSpinner.add("请选择");
        spinner_operate = findViewById(R.id.spinner_operate);
        tv_weight = findViewById(R.id.tv_weight);
        mRequestUtil = new RequestUtil(this);
        receiveId = getIntent().getStringExtra("id");

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapterForSpinnerPerson = new ArrayAdapter<>(AddSortInActivity.this, R.layout.item_for_custom_spinner, listForPersonSpinner);
        spinner_operate.setAdapter(adapterForSpinnerPerson);
        spinner_operate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mOperateBean = null;
                } else {
                    mOperateBean = mSortDefaultBean.getData().getSysUserList().get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();


            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOperateBean == null) {
                    Toast.makeText(AddSortInActivity.this, "请选择员工", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mSelectType == null) {
                    Toast.makeText(AddSortInActivity.this, "请选择品类", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_weight.getText().toString())) {
                    Toast.makeText(AddSortInActivity.this, "请输入重量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_buckle.getText().toString())) {
                    Toast.makeText(AddSortInActivity.this, "请输入扣重", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitData();
            }
        });
    }

    private void submitData() {

        if (typeImageAdapter.getUploadedImages().size() > 0) {
            uploadFile();
        } else {
            createOrder();
        }


    }

    @SuppressLint("CheckResult")
    private void createOrder() {

        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        SortRequestBean sortRequestBean = new SortRequestBean();
                        if (!TextUtils.isEmpty(edt_weight.getText().toString())) {
                           // requestParams.addBodyParameter("weight", edt_weight.getText().toString());
                            sortRequestBean.setWeight(edt_weight.getText().toString());
                        }
                        if (!TextUtils.isEmpty(edt_buckle.getText().toString())) {
                          //  requestParams.addBodyParameter("deductWeight", edt_buckle.getText().toString());
                            sortRequestBean.setDeductWeight( edt_buckle.getText().toString());
                        }
                        if (mPics.size() > 0) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (String s : mPics) {
                                stringBuffer.append(s).append(",");
                            }
                            String value = stringBuffer.toString().substring(0,stringBuffer.toString().length()-1);
                            sortRequestBean.setPicIdStr(value);
                        }
                        sortRequestBean.setSorter(mOperateBean.getUserId());
                        sortRequestBean.setProductId(mSelectType.getProductId());
                        List<SortRequestBean> list = new ArrayList<>();
                        list.add(sortRequestBean);
                        requestParams.addBodyParameter("orderReceiveInVoList",list);
                        requestParams.addBodyParameter("receiveId", receiveId);


                        final LoadingDialog loadingDialog = new LoadingDialog(AddSortInActivity.this)
                                .setLoadingText("保存中...");
                        mRequestUtil.doPostWithToken("/mobile/orderReceive/add", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<SortInListBean>() {

                            @Override
                            public void onRequestSuccess(SortInListBean result) {
                                loadingDialog.close();
                                Toast.makeText(AddSortInActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {

                            }
                        });
                    }
                });

    }

    private void uploadFile() {
        Log.d("zkf", "index:" + index);
        File file = new File(typeImageAdapter.getUploadedImages().get(index));
        RequestParams requestParams = new RequestParams();
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        final LoadingDialog loadingDialog = new LoadingDialog(AddSortInActivity.this)
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

    private void showSelect() {
        if (null != pop) {
            if (pop.isShowing()) {
                return;
            }
            pop.showAsDropDown(tv_type);
        } else {
            pop = new PopupWindow(this);
            View contentView = LayoutInflater.from(AddSortInActivity.this).inflate(R.layout.popup_select, null);
            final RecyclerView rv_category = contentView.findViewById(R.id.rv_category);
            final RecyclerView rv_type = contentView.findViewById(R.id.rv_type);
            PopupCategoryAdapter categoryAdapter = new PopupCategoryAdapter(this);
            rv_category.setLayoutManager(new LinearLayoutManager(this));
            rv_category.setAdapter(categoryAdapter);
            if (mSortDefaultBean.getData().getSysProductTypeParentChooseList().size() > 0) {
                mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(0).setSelected(true);
            }
            categoryAdapter.setDatas(mSortDefaultBean.getData().getSysProductTypeParentChooseList());

            PopupTypeAdapter typeAdapter = new PopupTypeAdapter(this);
            rv_type.setLayoutManager(new LinearLayoutManager(this));
            rv_type.setAdapter(typeAdapter);
//            if (mSortDefaultBean.getData().getSysProductTypeParentChooseList().size() > 0) {
//                typeAdapter.setDatas(mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(0).getChildren());
//                mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(0).getChildren().get(0).setSelected(true);
//
//            }
            categoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    for (SortDefaultBean.DataDTO.SysProductTypeParentChooseListDTO bean : mSortDefaultBean.getData().getSysProductTypeParentChooseList()) {
                        bean.setSelected(false);
                    }
                    mCurrentSelectIndex = position;
                    mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(position).setSelected(true);
                    typeAdapter.setDatas(mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(position).getChildren());
                    categoryAdapter.notifyDataSetChanged();
                    typeAdapter.notifyDataSetChanged();
                }
            });
            typeAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    for (SortDefaultBean.DataDTO.SysProductTypeParentChooseListDTO.ChildrenDTO bean : mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(mCurrentSelectIndex).getChildren()) {
                        bean.setSelected(false);
                    }
                    mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(mCurrentSelectIndex).getChildren().get(position).setSelected(true);
                    mSelectType = mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(mCurrentSelectIndex).getChildren().get(position);
                    typeAdapter.notifyDataSetChanged();
                    tv_type.setText(mSelectType.getProductTypeName());
                    pop.dismiss();
                }
            });
            pop.setOutsideTouchable(false);
            pop.setTouchable(true);
            pop.setContentView(contentView);
            pop.setWidth(tv_type.getWidth());
            pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            pop.showAsDropDown(tv_type);
        }
    }

    @Override
    public void initData() {
        getDataDetail();
    }

    @SuppressLint("CheckResult")
    private void getDataDetail() {
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParameter("receiveId", receiveId);
                        mRequestUtil.doPostWithToken2("mobile/orderReceive/defaultAddInfo", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<String>() {


                            @Override
                            public void onRequestSuccess(String result) {
                                Log.d("zkf", "result:" + result);
                                mSortDefaultBean = FastJsonTools.get(result, SortDefaultBean.class);

                                for (SortDefaultBean.DataDTO.SysUserListDTO beanData : mSortDefaultBean.getData().getSysUserList()) {
                                    listForPersonSpinner.add(beanData.getUserIdText());
                                }
                                adapterForSpinnerPerson.notifyDataSetChanged();

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
        if (resultCode != RESULT_OK && resultCode != ImagePicker.RESULT_CODE_ITEMS) {
            return;
        }


        typeImageAdapter.onActivityResult(requestCode, resultCode, data);
    }


}
