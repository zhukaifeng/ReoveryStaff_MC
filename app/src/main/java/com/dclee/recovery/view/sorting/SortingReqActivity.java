package com.dclee.recovery.view.sorting;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidkun.PullToRefreshRecyclerView;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.view.orderlist.OrderAdapter;
import com.dclee.recovery.view.purchase.PurchaseListActivity;
import com.dclee.recovery.wedget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SortingReqActivity extends BaseActivity {

    private PullToRefreshRecyclerView mRecyclerView;
    private SortingReqAdapter mAdapter;
    private TitleBar titleBar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_sorting_req;
    }

    @Override
    public void initView() {
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        titleBar = findViewById(R.id.titleBar);
        mAdapter = new SortingReqAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        titleBar.setOnRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {
                startActivity(new Intent(getActivity(), AddSortingReqActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        List<OrderBean> result = new ArrayList<>();
        result.add(new OrderBean());
        result.add(new OrderBean());
        mAdapter.setDatas(result);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
     //   EventBus.getDefault().unregister(this);
    }
}
