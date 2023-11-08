package com.dclee.recovery.view.sorting;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.DeptBean;
import com.dclee.recovery.pojo.OrderCreateBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.pojo.SorterBean;
import com.dclee.recovery.pojo.SummaryBean;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.RequestUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.xutils.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;

public class SummaryActivity extends BaseActivity {

    private TextView tv_select_person,tv_end_time,tv_start_time,tv_select_dept;
    private PopupWindow pop_person;
    private PopupWindow pop_dept;

    private RequestUtil mRequestUtil;
    private SorterBean mSorterBean;
    private SorterBean.DataDTO mSelectSorterBean;

    private DeptBean mDeptBean;
    private DeptBean.DataDTO mSelectDeptBean;
    private int pageNum = 0;
    private int pageSize = 10;
    private PullToRefreshRecyclerView mRecyclerView;
    private SummaryAdapter mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_sortin_summary;
    }

    @Override
    public void initView() {
        tv_select_person = findViewById(R.id.tv_select_person);
        tv_end_time = findViewById(R.id.tv_end_time);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_select_dept = findViewById(R.id.tv_select_dept);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRequestUtil = new RequestUtil(this);

        mAdapter = new SummaryAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
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

        tv_select_dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDept();
            }
        });
        tv_select_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPerson();
            }
        });
        tv_start_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        null,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_start_time.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                        if (!TextUtils.isEmpty(tv_end_time.getText())){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date dateEnd = sdf.parse(tv_end_time.getText().toString());
                                Date dateStart = sdf.parse(tv_start_time.getText().toString());
                                long start = dateStart.getTime();
                                long end = dateEnd.getTime();
                                if (start>end){
                                    Toast.makeText(SummaryActivity.this,"开始日期不能大于结束日期",Toast.LENGTH_SHORT).show();
                                    tv_start_time.setText("选择开始日期");
                                }else {
                                    getData();
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        });
        tv_end_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        null,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_end_time.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                        if (!TextUtils.isEmpty(tv_start_time.getText())){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date dateEnd = sdf.parse(tv_end_time.getText().toString());
                                Date dateStart = sdf.parse(tv_start_time.getText().toString());
                                long start = dateStart.getTime();
                                long end = dateEnd.getTime();
                                if (start>end){
                                    Toast.makeText(SummaryActivity.this,"开始日期不能大于结束日期",Toast.LENGTH_SHORT).show();
                                    tv_end_time.setText("选择结束日期");
                                }else {
                                    getData();
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

            }
        });
    }
    private void showSelectDept() {
        View contentView = LayoutInflater.from(SummaryActivity.this).inflate(R.layout.popup_select_person, null);
        if (null != pop_dept) {
            if (pop_dept.isShowing()) {
                return;
            }
            pop_dept.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        } else {
            pop_dept = new PopupWindow(this);
            final RecyclerView rv_category = contentView.findViewById(R.id.rv_person);
            LinearLayout linear_content = contentView.findViewById(R.id.linear_content);
            SummaryDeptAdapter summaryDeptAdapter = new SummaryDeptAdapter(this);
            rv_category.setLayoutManager(new GridLayoutManager(this, 3));
            rv_category.setAdapter(summaryDeptAdapter);
//            if (mSorterBean.getData().size() > 0) {
//                mSorterBean.getData().getSysProductTypeParentChooseList().get(0).setSelected(true);
//            }
            summaryDeptAdapter.setDatas(mDeptBean.getData());

            summaryDeptAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mSelectDeptBean = mDeptBean.getData().get(position);
                    tv_select_dept.setText(mSelectDeptBean.getDeptName());
                    pop_dept.dismiss();
                    getData();
                }
            });
            linear_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop_dept.dismiss();
                }
            });
            pop_dept.setOutsideTouchable(false);
            pop_dept.setTouchable(true);
            pop_dept.setContentView(contentView);
            DisplayMetrics dm = new DisplayMetrics();//屏幕度量
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screen_width = dm.widthPixels;//宽度
            int screen_height = dm.heightPixels;//高度
            pop_dept.setWidth(screen_width);
            pop_dept.setHeight(screen_height);
            pop_dept.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        }
    }

    private void showSelectPerson() {
        View contentView = LayoutInflater.from(SummaryActivity.this).inflate(R.layout.popup_select_person, null);
        if (null != pop_person) {
            if (pop_person.isShowing()) {
                return;
            }
            pop_person.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        } else {
            pop_person = new PopupWindow(this);
            final RecyclerView rv_category = contentView.findViewById(R.id.rv_person);
            LinearLayout linear_content = contentView.findViewById(R.id.linear_content);
            SummaryPersonAdapter personAdapter = new SummaryPersonAdapter(this);
            rv_category.setLayoutManager(new GridLayoutManager(this, 3));
            rv_category.setAdapter(personAdapter);
//            if (mSorterBean.getData().size() > 0) {
//                mSorterBean.getData().getSysProductTypeParentChooseList().get(0).setSelected(true);
//            }
            personAdapter.setDatas(mSorterBean.getData());

            personAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mSelectSorterBean = mSorterBean.getData().get(position);
                    tv_select_person.setText(mSelectSorterBean.getNickName());
                    pop_person.dismiss();
                    getData();
                }
            });
            linear_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop_person.dismiss();
                }
            });
            pop_person.setOutsideTouchable(false);
            pop_person.setTouchable(true);
            pop_person.setContentView(contentView);
            DisplayMetrics dm = new DisplayMetrics();//屏幕度量
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screen_width = dm.widthPixels;//宽度
            int screen_height = dm.heightPixels;//高度
            pop_person.setWidth(screen_width);
            pop_person.setHeight(screen_height);
            pop_person.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        }
    }


    @SuppressLint("CheckResult")
    @Override
    public void initData() {
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();

                        mRequestUtil.doGet("mobile/orderReceive/listSorter", requestParams, new RequestUtil.OnRequestFinishListener<String>() {
                            @Override
                            public void onRequestSuccess(String result) {
                                Log.d("zkf", "result222:" + result);
                                mSorterBean = FastJsonTools.get(result, SorterBean.class);
                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {
                                Log.d("zkf", "desc:" + desc);

                            }

                        });
                    }
                });

        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();

                        mRequestUtil.doGet("mobile/orderReceive/selectDeptList", requestParams, new RequestUtil.OnRequestFinishListener<String>() {
                            @Override
                            public void onRequestSuccess(String result) {
                                Log.d("zkf", "result333:" + result);
                                mDeptBean = FastJsonTools.get(result, DeptBean.class);
                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {
                                Log.d("zkf", "desc:" + desc);

                            }

                        });
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getData(){
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        requestParams.addBodyParameter("pageNum",pageNum);
                        requestParams.addBodyParameter("pageSize",pageSize);
                        if (!TextUtils.isEmpty(tv_start_time.getText().toString())){
                            requestParams.addBodyParameter("sumBeginTime",tv_start_time.getText().toString());
                        }
                        if (!TextUtils.isEmpty(tv_end_time.getText().toString())){
                            requestParams.addBodyParameter("sumEndTime",tv_end_time.getText().toString());
                        }
                        if (null != mSelectDeptBean){
                            requestParams.addBodyParameter("deptId",mSelectDeptBean.getDeptId());
                        }
                        if (null != mSelectSorterBean){
                            requestParams.addBodyParameter("sorter",mSelectSorterBean.getUserId());
                        }
                        Log.d("zkf","request:" + requestParams.toString());
                        mRequestUtil.doGet("mobile/orderReceive/sumList", requestParams, new RequestUtil.OnRequestFinishListener<String>() {
                            @Override
                            public void onRequestSuccess(String result) {
                                Log.d("zkf", "result666:" + result);
                                SummaryBean  summaryBean= FastJsonTools.get(result, SummaryBean.class);
                                mAdapter.setDatas(summaryBean.getRows());
                                mRecyclerView.setRefreshComplete();
                                mRecyclerView.setLoadMoreComplete();
                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {
                                Log.d("zkf", "desc:" + desc);

                            }

                        });
                    }
                });

    }




}
