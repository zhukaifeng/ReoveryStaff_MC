package com.dclee.recovery.view.sorting;

import android.content.Intent;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidkun.PullToRefreshRecyclerView;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.wedget.TitleBar;

import java.util.ArrayList;
import java.util.List;

public class SortInListActivity extends BaseActivity {

    private PullToRefreshRecyclerView mRecyclerView;
    private SortInListAdapter mAdapter;
    private TitleBar titleBar;


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

        mAdapter = new SortInListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(SortInListActivity.this, SortInDetailActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        List<OrderBean> result = new ArrayList<>();
        result.add(new OrderBean());
        result.add(new OrderBean());
        result.add(new OrderBean());
        result.add(new OrderBean());
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
