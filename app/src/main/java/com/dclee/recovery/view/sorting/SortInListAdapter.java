package com.dclee.recovery.view.sorting;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.util.RequestUtil;

import java.text.SimpleDateFormat;

public class SortInListAdapter extends BaseAdapter<OrderBean> {
    private RequestUtil mRequestUtil;
    public static final int REQ_RECV_GOODS = 0x233;
    private SimpleDateFormat resultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat originDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Activity mContent;

    public SortInListAdapter(Activity context) {
        super(context);
        mContent = context;
        mRequestUtil = new RequestUtil(context);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_sort_in;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final OrderBean data) {
        View itemView = viewHolder.itemView;

        TextView tv_site = itemView.findViewById(R.id.tv_site);
        TextView tv_customer = itemView.findViewById(R.id.tv_customer);
        TextView tv_type = itemView.findViewById(R.id.tv_type);
        TextView tv_count = itemView.findViewById(R.id.tv_count);
        TextView tv_date = itemView.findViewById(R.id.tv_date);
        TextView tv_sort = itemView.findViewById(R.id.tv_sort);
        TextView tv_diff = itemView.findViewById(R.id.tv_diff);









    }
}