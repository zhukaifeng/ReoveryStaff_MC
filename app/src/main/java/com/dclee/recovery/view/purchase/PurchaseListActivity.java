package com.dclee.recovery.view.purchase;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.pojo.PurchaseBean;
import com.dclee.recovery.util.KeyboardUtil;
import com.dclee.recovery.util.RequestUtil;

import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class PurchaseListActivity extends BaseActivity {
    private PullToRefreshRecyclerView mRecyclerView;
    private PurchaseAdapter mAdapter;
    private String key;
    private TextView mStartDate;
    private EditText mCustomName;
    private TextView mEndDate;
    private RequestUtil requestUtil = new RequestUtil(this);
    private int page = 1;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int getLayoutId() {
        return R.layout.activity_purchase_list;
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mCustomName = findViewById(R.id.custom_name);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PurchaseAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mEndDate = findViewById(R.id.end_date);
        mStartDate = findViewById(R.id.start_date);
        Date endDate = new Date();
        Date startDate = new Date();
        startDate.setYear(endDate.getYear() - 2);
        mEndDate.setText(simpleDateFormat.format(endDate));
        mStartDate.setText(simpleDateFormat.format(startDate));
        KeyboardUtil.setOnEnterClickListener(this, mCustomName, new KeyboardUtil.OnKeyBoardEnterClickListener() {
            @Override
            public void onKeyboardEnterClick(String content) {
                key = content;
                page = 1;
                initData();
            }
        });
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(true);
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
        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("start_time", mStartDate.getText().toString() + " 00:00:00");
        requestParams.addParameter("end_time", mEndDate.getText().toString() + " 23:59:59");
        requestParams.addParameter("page", page);
        if (!TextUtils.isEmpty(key)) {
            requestParams.addParameter("username", key);
        } else {
            requestParams.addParameter("username", "");
        }
        requestUtil.postList("s1/orders", requestParams, PurchaseBean.class, new RequestUtil.OnRequestFinishListener<List<PurchaseBean>>() {
            @Override
            public void onRequestSuccess(List<PurchaseBean> result) {
                if (page == 1) {
                    mAdapter.setDatas(result);
                } else {
                    mAdapter.addDatas(result);
                }
                mRecyclerView.setRefreshComplete();
                mRecyclerView.setLoadMoreComplete();
                page++;
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {

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
}
