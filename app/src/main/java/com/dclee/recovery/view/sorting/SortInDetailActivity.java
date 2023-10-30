package com.dclee.recovery.view.sorting;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidkun.PullToRefreshRecyclerView;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.wedget.TitleBar;
import com.umeng.commonsdk.debug.I;

import java.util.ArrayList;
import java.util.List;

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

        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SortInDetailActivity.this,AddSortInActivity.class);
                startActivity(intent);
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
