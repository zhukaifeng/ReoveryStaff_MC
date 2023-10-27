package com.dclee.recovery.view.orderlist;

import android.content.Intent;
import android.util.Log;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.bean.event.CancelOrderEvent;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.http.RequestParams;

import java.util.List;

public class OrderAdminListActivity extends BaseActivity {
    private OrderAdapter mAdapter;
    private PullToRefreshRecyclerView mRecyclerView;
    private RequestUtil mRequestUtil;
    private int state = Config.STATE_START;
    private int page = Config.STATE_START;
    private RadioGroup rgSortType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_orders;
    }

    @Override
    public void initView() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new OrderAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRequestUtil = new RequestUtil(this);
        rgSortType = findViewById(R.id.sort_type);
        rgSortType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.user_orders:
                        state = Config.STATE_START;
                        break;
                    case R.id.unreceive_orders:
                        state = Config.STATE_UNRECEIVE;
                        break;
                    case R.id.ungetgoods_order:
                        state = Config.STATE_UNGETGOODS;
                        break;
                    case R.id.finish_orders:
                        state = Config.STATE_FINISH;
                        break;
                    case R.id.check_cancel:
                        state = Config.STATE_CANCEL;
                        break;
                }
                page = 1;
                initData();
            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCancelOrderEvent(CancelOrderEvent event) {
        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("order_id", event.getId());
        requestParams.addParameter("reason", event.getMemo());
        mRequestUtil.doPost("s1/rejectOrder", requestParams, OrderBean.class, new RequestUtil.OnRequestFinishListener<OrderBean>() {
            @Override
            public void onRequestSuccess(OrderBean result) {
                T.showShort(mActivity,"取消订单成功");
                queryDatas();
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {
                T.showShort(mActivity,desc);
            }
        });
    }

    private void queryDatas() {
        RequestParams requestParams = new RequestParams();
        if(state==Config.STATE_CANCEL){
            mRequestUtil.postList("s1/rejectOrderCheckingList", requestParams, OrderBean.class, new RequestUtil.OnRequestFinishListener<List<OrderBean>>() {
                @Override
                public void onRequestSuccess(List<OrderBean> result) {
                    Log.e("requestParams","size "+result.size());
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
        } else {
            requestParams.addParameter("page", page);
            requestParams.addParameter("state", state);
            requestParams.addParameter("user_id", Config.userId);
//        requestParams.addParameter("type", 0);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == OrderAdapter.REQ_RECV_GOODS) {
            page = 1;
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
