package com.dclee.recovery.view;

import android.content.Intent;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.view.orderlist.OrderAdapter;

import org.xutils.http.RequestParams;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class GetGoodsActivity extends BaseActivity {
    private OrderAdapter mAdapter;
    private PullToRefreshRecyclerView mRecyclerView;
    private RequestUtil mRequestUtil;
    private int page = 1;
    private final String CREATE_TIME = "created_at";
    private String sortType = CREATE_TIME;

    @Override
    public int getLayoutId() {
        return R.layout.activity_get_goods;
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new OrderAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRequestUtil = new RequestUtil(this);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
    }

    @Override
    public void initData() {
        queryDatas();
    }

    private void queryDatas() {
        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("page", page);
        requestParams.addParameter("order", sortType);
        requestParams.addParameter("type", 1);
        mRequestUtil.postList("s1/order", requestParams, OrderBean.class, new RequestUtil.OnRequestFinishListener<List<OrderBean>>() {

            @Override
            public void onRequestSuccess(List<OrderBean> result) {
                if (page == 1) {
                    mAdapter.setDatas(result);
                } else {
                    mAdapter.addDatas(result);
                }
                mRecyclerView.setRefreshComplete();
                mRecyclerView.setLoadMoreComplete();
                if (result.size() != 0)
                    page++;
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OrderAdapter.REQ_RECV_GOODS) {
            page = 1;
            initData();
        }
    }
}
