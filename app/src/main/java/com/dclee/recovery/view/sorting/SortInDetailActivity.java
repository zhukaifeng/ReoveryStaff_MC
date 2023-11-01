package com.dclee.recovery.view.sorting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidkun.PullToRefreshRecyclerView;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.pojo.SortReqDetailBean;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.wedget.TitleBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.commonsdk.debug.I;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class SortInDetailActivity extends BaseActivity {

    private PullToRefreshRecyclerView mRecyclerView;
    private SortInDetailAdapter mAdapter;
    private TitleBar titleBar;
    private TextView tv_site;
    private TextView tv_customer;
    private TextView tv_type;
    private TextView tv_count;
    private TextView tv_date;
    private TextView tv_sort;
    private TextView tv_diff;
    private TextView tv_add;
    private TextView tv_submit;
    private RequestUtil mRequestUtil;
    private  String receiveId;

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
        tv_customer = findViewById(R.id.tv_customer);
        tv_type = findViewById(R.id.tv_type);
        tv_count = findViewById(R.id.tv_count);
        tv_date = findViewById(R.id.tv_date);
        tv_sort = findViewById(R.id.tv_sort);
        tv_diff = findViewById(R.id.tv_diff);
        tv_add = findViewById(R.id.tv_add);
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

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SortInDetailActivity.this,AddSortInActivity.class);
                intent.putExtra("id",receiveId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
//        List<OrderBean> result = new ArrayList<>();
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        mAdapter.setDatas(result);


        //getDataDetail();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataDetail();
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
                                Log.d("zkf","result:" + result);
                                SortReqDetailBean data = FastJsonTools.get(result, SortReqDetailBean.class);

                                if (!TextUtils.isEmpty(data.getData().getDeptIdText())){
                                    tv_site.setText(data.getData().getDeptIdText());
                                }
                                if (!TextUtils.isEmpty(data.getData().getCustomerName())){
                                    tv_customer.setText(data.getData().getCustomerName());
                                }
                                if (!TextUtils.isEmpty(data.getData().getCategoryIdText())){
                                    tv_type.setText(data.getData().getCategoryIdText());
                                }

                                if (!TextUtils.isEmpty(data.getData().getReceiveWeight())){
                                    tv_count.setText(data.getData().getReceiveWeight());
                                }
                                if (!TextUtils.isEmpty(data.getData().getCreateTime())){
                                    tv_date.setText(data.getData().getCreateTime().substring(0,10));
                                }
                                if (!TextUtils.isEmpty(data.getData().getIntoStorehouseWeight())){
                                    tv_sort.setText(data.getData().getIntoStorehouseWeight());
                                }
                                if (!TextUtils.isEmpty(data.getData().getDifferenceWeight())){
                                    tv_diff.setText(data.getData().getDifferenceWeight());
                                }
//                                tv_inventory.setText(String.valueOf(data.getData().getStock()));
//                                tv_count.setText(data.getData().getNetWeight());
                                // edt_remark.setText();
                                mAdapter.setDatas(data.getData().getOrderReceiveInVoList());

                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {

                            }
                        });
                    }
                });

    }

}
