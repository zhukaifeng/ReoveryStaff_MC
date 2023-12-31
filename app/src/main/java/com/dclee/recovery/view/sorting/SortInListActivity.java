package com.dclee.recovery.view.sorting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.base.CacheUtil;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.OrderCreateBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.ToastUtil;
import com.dclee.recovery.view.home.HomeActivity;
import com.dclee.recovery.wedget.TitleBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class SortInListActivity extends BaseActivity {

    private PullToRefreshRecyclerView mRecyclerView;
    private SortInListAdapter mAdapter;
    private TitleBar titleBar;

    private RequestUtil mRequestUtil;
    private int pageNum = 0;
    private int pageSize = 10;
    private SortInListBean  mData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sortin_list;
    }

    @Override
    public void initView() {
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        titleBar = findViewById(R.id.titleBar);
        mRequestUtil = new RequestUtil(this);

        mAdapter = new SortInListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mData.getRows().get(position).getReceiveStatus().equals("3")){
                    return;
                }
                Intent intent = new Intent(getActivity(), SortInDetailActivity.class);
                intent.putExtra("id",mAdapter.getDatas().get(position).getReceiveId());
                if (!TextUtils.isEmpty(mAdapter.getDatas().get(position).getOrderNo())){
                    intent.putExtra("snNum",mAdapter.getDatas().get(position).getOrderNo());
                }
                startActivity(intent);
            }
        });
        mRecyclerView.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 0;
                getData();
            }

            @Override
            public void onLoadMore() {
                pageSize++;
                getData();
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void initData() {
//        List<OrderBean> result = new ArrayList<>();
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        result.add(new OrderBean());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Log.d("zkf","getdata");
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParameter("pageNum", pageNum);
                        requestParams.addParameter("pageSize", pageSize);
                        mRequestUtil.doPostWithToken2("mobile/orderReceive/orderReceive", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<String>() {


                            @Override
                            public void onRequestSuccess(String result) {
                                mData = FastJsonTools.get(result, SortInListBean.class);
                                List<SortInListBean.RowsBean> tmp = new ArrayList<>();
                                tmp.addAll(mData.getRows());
                                List<SortInListBean.RowsBean> addList = new ArrayList<>();
                                for (SortInListBean.RowsBean rowsBean:tmp){
                                    if (rowsBean.getReceiveStatus().equals("4")){
                                        addList.add(rowsBean);
                                    }
                                }
                                mAdapter.setDatas(addList);
                                mRecyclerView.setRefreshComplete();
                                mRecyclerView.setLoadMoreComplete();
                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {

                            }
                        });
                    }
                });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //   EventBus.getDefault().unregister(this);
    }
}
