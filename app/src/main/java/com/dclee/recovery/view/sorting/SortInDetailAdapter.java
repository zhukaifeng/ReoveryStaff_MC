package com.dclee.recovery.view.sorting;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.util.RequestUtil;

import java.text.SimpleDateFormat;

public class SortInDetailAdapter extends BaseAdapter<OrderBean> {
    private RequestUtil mRequestUtil;
    public static final int REQ_RECV_GOODS = 0x233;
    private SimpleDateFormat resultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat originDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Activity mContent;

    public SortInDetailAdapter(Activity context) {
        super(context);
        mContent = context;
        mRequestUtil = new RequestUtil(context);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_sort_detail;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final OrderBean data) {
        View itemView = viewHolder.itemView;

        TextView tv_name = itemView.findViewById(R.id.tv_name);
        TextView tv_type = itemView.findViewById(R.id.tv_type);
        TextView tv_weight = itemView.findViewById(R.id.tv_weight);
        TextView tv_buckle = itemView.findViewById(R.id.tv_buckle);
        TextView tv_netweight = itemView.findViewById(R.id.tv_netweight);
        TextView tv_operate = itemView.findViewById(R.id.tv_operate);









    }
}