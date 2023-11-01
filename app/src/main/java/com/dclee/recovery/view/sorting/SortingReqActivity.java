package com.dclee.recovery.view.sorting;

import android.Manifest;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.view.orderlist.OrderAdapter;
import com.dclee.recovery.view.purchase.PurchaseListActivity;
import com.dclee.recovery.wedget.TitleBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class SortingReqActivity extends BaseActivity {

    private PullToRefreshRecyclerView mRecyclerView;
    private SortingReqAdapter mAdapter;
    private TitleBar titleBar;
    private ImageView iv_add;
    private RequestUtil mRequestUtil;
    private int pageNum = 0;
    private int pageSize = 10;

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
        iv_add = findViewById(R.id.iv_add);
        mRequestUtil = new RequestUtil(this);

        mAdapter = new SortingReqAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        titleBar.setOnRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {
                startActivity(new Intent(getActivity(), AddSortingReqActivity.class));
            }
        });
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddSortingReqActivity.class));

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
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Intent intent = new Intent(getActivity(), AddSortingReqActivity.class);
//                intent.putExtra("isEdit",true);
//                intent.putExtra("id",mAdapter.getDatas().get(position).getReceiveId());
//                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
//        List<OrderBean> result = new ArrayList<>();
//        result.add(new OrderBean());
//        result.add(new OrderBean());
//        mAdapter.setDatas(result);



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
                                SortInListBean  data = FastJsonTools.get(result, SortInListBean.class);
                                mAdapter.setDatas(data.getRows());
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
