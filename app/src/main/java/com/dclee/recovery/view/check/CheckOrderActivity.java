package com.dclee.recovery.view.check;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.adapter.CheckOrderAdapter;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.pojo.CheckOrderBean;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.wedget.TitleBar;
import com.github.ybq.android.spinkit.SpinKitView;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CheckOrderActivity extends BaseActivity {
    private TitleBar titleBar;
    private SpinKitView skvCheck;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private ListView lvCheckOrder;

    private int page = 1;

    private RequestUtil mRequestUtil;

    private boolean isStartDate;
    private Calendar cEnd;
    private Calendar cStart;

    private List<CheckOrderBean> mList = new ArrayList<>();
    private CheckOrderAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check_order;
    }

    @Override
    public void initView() {
        titleBar = findViewById(R.id.title);
        skvCheck = findViewById(R.id.skv_check);
        tvStartDate = findViewById(R.id.tv_start_date);
        tvEndDate = findViewById(R.id.tv_end_date);
        lvCheckOrder = findViewById(R.id.lv_check_order);
        titleBar.setTitle("已审核");
        mRequestUtil = new RequestUtil(this);
        initLis();
    }

    @Override
    public void initData() {
        cEnd = Calendar.getInstance();
        cStart = Calendar.getInstance();
        cStart.setTimeInMillis(cEnd.getTimeInMillis() - (6 * 24 * 60 * 60 * 1000));
        tvStartDate.setText(cStart.get(Calendar.YEAR) + "-" + (cStart.get(Calendar.MONTH) + 1) +
                "-" + cStart.get(Calendar.DAY_OF_MONTH));

        tvEndDate.setText(cEnd.get(Calendar.YEAR) + "-" + (cEnd.get(Calendar.MONTH) + 1) +
                "-" + cEnd.get(Calendar.DAY_OF_MONTH));
        queryDatas();
    }

    private void queryDatas() {
        mList.clear();
        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("verify_id", Config.userId);
        requestParams.addParameter("start_date", tvStartDate.getText().toString() + " 00:00:00");
        requestParams.addParameter("end_date", tvEndDate.getText().toString() + " 24:00:00");

        mRequestUtil.postList("s1/orderVerifyLists", requestParams, CheckOrderBean.class,
                new RequestUtil.OnRequestFinishListener<List<CheckOrderBean>>() {
                    @Override
                    public void onRequestSuccess(List<CheckOrderBean> result) {
                        if (result == null || result.size() == 0) {
                            T.showShort(mActivity, "无数据");
                        } else {
                            mList.addAll(result);
                        }
                        initListView();
                    }

                    @Override
                    public void onRequestFail(int errorCode, String desc) {

                    }
                });
    }

    private void initListView() {
        if (mAdapter == null) {
            mAdapter = new CheckOrderAdapter(mActivity, mList, R.layout.item_check_order);
            lvCheckOrder.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initLis() {
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = true;
                showDialogPick();
            }
        });

        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartDate = false;
                showDialogPick();
            }
        });
    }

    public void toSearchCheckOrder(View view) {
        if ((cEnd.getTimeInMillis() - cStart.getTimeInMillis()) <= 0) {
            T.showShort(mActivity, "结束时间必须大于起始时间");
            return;
        } else if ((cEnd.getTimeInMillis() - cStart.getTimeInMillis()) >
                (6 * 24 * 60 * 60 * 1000)) {
            T.showShort(mActivity, "时间范围最大为一周");
            return;
        }
        queryDatas();
    }

    //将两个选择时间的dialog放在该函数中
    private void showDialogPick() {
        final StringBuffer time = new StringBuffer();
        //获取Calendar对象，用于获取当前时间
        Calendar calendar = Calendar.getInstance();
        if (isStartDate) {
            calendar = cStart;
        } else {
            calendar = cEnd;
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //实例化DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            //选择完日期后会调用该回调函数
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                time.append(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                if (isStartDate) {
                    tvStartDate.setText(time);
                    cStart.set(year, monthOfYear, dayOfMonth);
                } else {
                    tvEndDate.setText(time);
                    cEnd.set(year, monthOfYear, dayOfMonth);
                }
            }
        }, year, month, day);
        //弹出选择日期对话框
        datePickerDialog.show();
    }

}
