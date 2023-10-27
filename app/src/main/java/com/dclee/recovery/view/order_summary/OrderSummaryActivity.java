package com.dclee.recovery.view.order_summary;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.view.purchase.OnSelectDateListener;
import com.dclee.recovery.view.purchase.PurchaseListActivity;
import com.dclee.recovery.wedget.TitleBar;
import com.github.nukc.LoadMoreWrapper.LoadMoreAdapter;
import com.github.nukc.LoadMoreWrapper.LoadMoreWrapper;

import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderSummaryActivity extends BaseActivity {
    private OrderSummaryAdapter mAdapter;
    private PullToRefreshRecyclerView mRecyclerView;
    private RequestUtil requestUtil;
    private int page = 1;
    private TextView mStartDate;
    private TitleBar titleBar;
    private TextView mEndDate;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_summary;
    }

    @Override
    public void initView() {
        mStartDate = findViewById(R.id.start_date);
        titleBar = findViewById(R.id.titleBar);
        mEndDate = findViewById(R.id.end_date);
        Date endDate = new Date();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.MONTH, -6);
        Date startDate = startCalendar.getTime();
        mEndDate.setText(simpleDateFormat.format(endDate));
        mStartDate.setText(simpleDateFormat.format(startDate));
        requestUtil = new RequestUtil(this);
        mAdapter = new OrderSummaryAdapter(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        titleBar.setOnRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {
                startActivity(new Intent(getActivity(), PurchaseListActivity.class));
            }
        });
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

    public void toSelectStartDate(View view) {
        toSelectDate(new OnSelectDateListener() {
            @Override
            public void onDateSelected(Date date, String dateStr) {
                mStartDate.setText(dateStr);
                page = 1;
                initData();
            }
        });
    }

    public void toSelectEndDate(View view) {
        toSelectDate(new OnSelectDateListener() {
            @Override
            public void onDateSelected(Date date, String dateStr) {
                mEndDate.setText(dateStr);
                page = 1;
                initData();
            }
        });
    }

    private void toSelectDate(final OnSelectDateListener onSelectDateListener) {
        Calendar endDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startDate.set(endDate.get(Calendar.YEAR) - 3, endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE));
        TimePickerView datePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                onSelectDateListener.onDateSelected(date, simpleDateFormat.format(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)
                .setTitleSize(17)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.textGrayColor))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        datePicker.show(true);
    }

    @Override
    public void initData() {
        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("start_time", mStartDate.getText().toString() + " 00:00:00");
        requestParams.addParameter("end_time", mEndDate.getText().toString() + " 00:00:00");
        requestParams.addParameter("page", page);
        requestUtil.postList("s1/summary", requestParams, OrderBean.class, new RequestUtil.OnRequestFinishListener<List<OrderBean>>() {
            @Override
            public void onRequestSuccess(List<OrderBean> result) {
                if (page == 1) {
                    mAdapter.setDatas(result);
                } else {
                    mAdapter.addDatas(result);
                }
                mRecyclerView.setRefreshComplete();
                mRecyclerView.setLoadMoreComplete();
                if (result.size() > 0)
                    page++;
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {

            }
        });
    }
}
