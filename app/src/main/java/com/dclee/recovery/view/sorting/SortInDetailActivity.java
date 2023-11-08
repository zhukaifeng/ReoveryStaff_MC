package com.dclee.recovery.view.sorting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidkun.PullToRefreshRecyclerView;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.base.DbHelper;
import com.dclee.recovery.bean.db.SortInBean;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.pojo.SortReqDetailBean;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.view.purchase.SortRequestBean;
import com.dclee.recovery.wedget.TitleBar;
import com.sunmi.utils.DoubleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.commonsdk.debug.I;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class SortInDetailActivity extends BaseActivity {

    private PullToRefreshRecyclerView mRecyclerView;
    private SortInDetailAdapter mAdapter;
    private TitleBar titleBar;
    private TextView tv_site;
    //private TextView tv_customer;
    private TextView tv_type;
    private TextView tv_sn;
    private TextView tv_count;
    private TextView tv_date;
    private TextView tv_sort;
    private TextView tv_diff;
    private TextView tv_add;
    private TextView tv_submit;
    private TextView tv_no_data;
    private RequestUtil mRequestUtil;
    private String receiveId;
    private DbHelper dbHelper = new DbHelper();
    private List<SortInBean> mDataList = new ArrayList<>();
    private SortReqDetailBean mData;
    public SortInDetailActivity() throws DbException {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sortin_detail;
    }

    @Override
    public void initView() {
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        titleBar = findViewById(R.id.titleBar);
        tv_site = findViewById(R.id.tv_site);
        tv_sn = findViewById(R.id.tv_sn);
        tv_type = findViewById(R.id.tv_type);
        tv_count = findViewById(R.id.tv_count);
        tv_date = findViewById(R.id.tv_date);
        tv_sort = findViewById(R.id.tv_sort);
        tv_diff = findViewById(R.id.tv_diff);
        tv_add = findViewById(R.id.tv_add);
        tv_no_data = findViewById(R.id.tv_no_data);
        tv_submit = findViewById(R.id.tv_submit);
        mAdapter = new SortInDetailAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRequestUtil = new RequestUtil(this);
        receiveId = getIntent().getStringExtra("id");

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        mAdapter.setOnLongClickListener(new BaseAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View view, int position) {
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(SortInDetailActivity.this);
                normalDialog.setIcon(R.mipmap.ic_launcher);
                normalDialog.setTitle("提示");
                normalDialog.setMessage("确定要删除吗?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean success =dbHelper.dbDeleteId(mDataList.get(position).getId(),receiveId);
                                if (success){
                                    mDataList.remove(position);
                                    mAdapter.notifyDataSetChanged();
                                    if (null != mData && null != mData.getData().getReceiveWeight()){
                                        double allNetWeight =0;
                                        for (SortInBean sortInBean:mDataList){
                                            double weight = Double.parseDouble(sortInBean.getWeight().toString());
                                            double buckle = Double.parseDouble(sortInBean.getDeductWeight().toString());
                                            double netweight = DoubleUtils.sub(weight, buckle);
                                            allNetWeight = netweight + allNetWeight;
                                        }
                                        double showWeight = DoubleUtils.sub(Double.parseDouble(mData.getData().getReceiveWeight()), allNetWeight);
                                        tv_diff.setText(String.valueOf(showWeight));
                                    }

                                    Toast.makeText(SortInDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                normalDialog.show();// 显示
            }
        });


        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SortInDetailActivity.this, CreateSortInActivity.class);
                intent.putExtra("id", receiveId);
                startActivity(intent);
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

        String sn = getIntent().getStringExtra("snNum");
        if (!TextUtils.isEmpty(sn)){
            tv_sn.setText(sn);
        }
    }

    @Override
    public void initData() {
        getDataDetail();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();

    }
    private void initList() {
        mDataList.clear();
        if (null != dbHelper.dbFindSortInBeanById(receiveId)){
            tv_no_data.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mDataList.addAll(dbHelper.dbFindSortInBeanById(receiveId));
            if (null != mData && null != mData.getData().getReceiveWeight()){
                double allNetWeight =0;
                for (SortInBean sortInBean:mDataList){
                    double weight = Double.parseDouble(sortInBean.getWeight().toString());
                    double buckle = Double.parseDouble(sortInBean.getDeductWeight().toString());
                    double netweight = DoubleUtils.sub(weight, buckle);
                    allNetWeight = netweight + allNetWeight;
                }
                double showWeight = DoubleUtils.sub(Double.parseDouble(mData.getData().getReceiveWeight()), allNetWeight);
                tv_diff.setText(String.valueOf(showWeight));
            }
        }
        mAdapter.setDatas(mDataList);

        if (mDataList.size()==0){
            tv_no_data.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //   EventBus.getDefault().unregister(this);
    }

    @SuppressLint("CheckResult")
    private void getDataDetail() {
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParameter("receiveId", receiveId);
                        mRequestUtil.doPostWithToken2("mobile/orderReceive/detail", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<String>() {


                            @Override
                            public void onRequestSuccess(String result) {
                                Log.d("zkf", "result:" + result);
                                mData = FastJsonTools.get(result, SortReqDetailBean.class);

                                if (!TextUtils.isEmpty(mData.getData().getDeptIdText())) {
                                    tv_site.setText(mData.getData().getDeptIdText());
                                }
//                                if (!TextUtils.isEmpty(mData.getData().getSn())) {
//                                    tv_customer.setText(mData.getData().getCustomerName());
//                                }
                                if (!TextUtils.isEmpty(mData.getData().getCategoryIdText())) {
                                    tv_type.setText(mData.getData().getCategoryIdText());
                                }

                                if (!TextUtils.isEmpty(mData.getData().getReceiveWeight())) {
                                    tv_count.setText(mData.getData().getReceiveWeight());
                                }
                                if (!TextUtils.isEmpty(mData.getData().getCreateTime())) {
                                    tv_date.setText(mData.getData().getCreateTime());
                                }
                                if (!TextUtils.isEmpty(mData.getData().getIntoStorehouseWeight())) {
                                    tv_sort.setText(mData.getData().getIntoStorehouseWeight());
                                }

                                if (null != mData && null != mData.getData().getReceiveWeight()){
                                    double allNetWeight =0;
                                    for (SortInBean sortInBean:mDataList){
                                        double weight = Double.parseDouble(sortInBean.getWeight().toString());
                                        double buckle = Double.parseDouble(sortInBean.getDeductWeight().toString());
                                        double netweight = DoubleUtils.sub(weight, buckle);
                                        allNetWeight = netweight + allNetWeight;
                                    }
                                    double showWeight = DoubleUtils.sub(Double.parseDouble(mData.getData().getReceiveWeight()), allNetWeight);
                                    tv_diff.setText(String.valueOf(showWeight));
                                }
//                                if (!TextUtils.isEmpty(mData.getData().getDifferenceWeight())) {
//                                    tv_diff.setText(mData.getData().getDifferenceWeight());
//                                }
//                                tv_inventory.setText(String.valueOf(data.getData().getStock()));
//                                tv_count.setText(data.getData().getNetWeight());
                                // edt_remark.setText();

                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {

                            }
                        });
                    }
                });

    }

    @SuppressLint("CheckResult")
    private void submitData() {
        double showWeight = Double.parseDouble(tv_diff.getText().toString());
        if (showWeight>150||showWeight< -150){
            Toast.makeText(SortInDetailActivity.this, "差异大于150", Toast.LENGTH_SHORT).show();
            return;
        }

        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        SortRequestBean sortRequestBean = new SortRequestBean();

                        List<SortRequestBean> list = new ArrayList<>();
                        for (SortInBean sortInBean : mDataList) {
                            sortRequestBean.setWeight(sortInBean.getWeight());
                            sortRequestBean.setDeductWeight(sortInBean.getDeductWeight());
                            sortRequestBean.setProductId(sortInBean.getProductId());
                            sortRequestBean.setSorter(sortInBean.getSorter());
                            if (!TextUtils.isEmpty(sortInBean.getPicIdStr())) {
                                sortRequestBean.setPicIdStr(sortInBean.getPicIdStr());
                            }
                            list.add(sortRequestBean);
                        }
                        requestParams.addBodyParameter("orderReceiveInVoList", list);
                        requestParams.addBodyParameter("receiveId", receiveId);

                        final LoadingDialog loadingDialog = new LoadingDialog(SortInDetailActivity.this)
                                .setLoadingText("保存中...");
                        mRequestUtil.doPostWithToken2("/mobile/orderReceive/add", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<String>() {

                            @Override
                            public void onRequestSuccess(String result) {
                                loadingDialog.close();
                                if (result.contains("操作成功")){
                                    Toast.makeText(SortInDetailActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                    dbHelper.dbDeleteAll(receiveId);
                                }

                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {

                            }
                        });
                    }
                });
    }

}
