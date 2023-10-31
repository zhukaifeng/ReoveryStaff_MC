package com.dclee.recovery.view.sorting;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.bean.event.CancelOrderEvent;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.view.create_order.CreateOrderActivity;
import com.dclee.recovery.view.orderlist.OrderAdminDetailActivity;
import com.dclee.recovery.view.orderlist.OrderDetailActivity;
import com.longsh.optionframelibrary.OptionMaterialDialog;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

public class SortingReqAdapter extends BaseAdapter<SortInListBean.RowsBean> {
    private RequestUtil mRequestUtil;
    public static final int REQ_RECV_GOODS = 0x233;
    private SimpleDateFormat resultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat originDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Activity mContent;

    public SortingReqAdapter(Activity context) {
        super(context);
        mContent = context;
        mRequestUtil = new RequestUtil(context);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_sort_req;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final SortInListBean.RowsBean data) {
        View itemView = viewHolder.itemView;
        TextView tv_site = itemView.findViewById(R.id.tv_site);
        TextView tv_fenjian = itemView.findViewById(R.id.tv_fenjian);
        TextView tv_lingyong = itemView.findViewById(R.id.tv_lingyong);
        TextView tv_category = itemView.findViewById(R.id.tv_category);
        TextView tv_sn = itemView.findViewById(R.id.tv_sn);
        TextView tv_time = itemView.findViewById(R.id.tv_time);
        TextView tv_status = itemView.findViewById(R.id.tv_status);
        TextView tv_chayi = itemView.findViewById(R.id.tv_chayi);


        tv_time.setText(data.getCreateTime());
        tv_status.setText(data.getReceiveStatusText());
        tv_category.setText(data.getCategoryName());
        tv_sn.setText(data.getOrderNo());
        tv_fenjian.setText("");
        tv_lingyong.setText("");
        tv_chayi.setText("");
        tv_site.setText("");


    }
}