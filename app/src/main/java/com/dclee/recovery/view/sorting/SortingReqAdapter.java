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
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.view.create_order.CreateOrderActivity;
import com.dclee.recovery.view.orderlist.OrderAdminDetailActivity;
import com.dclee.recovery.view.orderlist.OrderDetailActivity;
import com.longsh.optionframelibrary.OptionMaterialDialog;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

public class SortingReqAdapter extends BaseAdapter<OrderBean> {
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
        return R.layout.item_order;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final OrderBean data) {
        View itemView = viewHolder.itemView;
        final TextView prod_names = itemView.findViewById(R.id.prod_names);
        final TextView weight = itemView.findViewById(R.id.weight);
        TextView order_num = itemView.findViewById(R.id.order_num);
        TextView appoint_time = itemView.findViewById(R.id.appoint_time);
        TextView order_time = itemView.findViewById(R.id.order_time);
        TextView real_name = itemView.findViewById(R.id.real_name);
        TextView to_dispatch_order = itemView.findViewById(R.id.to_dispatch_order);
        TextView phone = itemView.findViewById(R.id.phone);
        View diagPhone = itemView.findViewById(R.id.todiag_phone);
        final TextView address_detail = itemView.findViewById(R.id.address_detail);
        TextView order_status = itemView.findViewById(R.id.order_status);
        Button toCheckCancel = itemView.findViewById(R.id.to_check_cancel);
        final Button to_cancel_order = itemView.findViewById(R.id.to_cancel_order);
        final View to_get_order = itemView.findViewById(R.id.to_get_order);
        final View to_get_goods = itemView.findViewById(R.id.to_get_goods);
        StringBuffer buffer = new StringBuffer();








    }
}