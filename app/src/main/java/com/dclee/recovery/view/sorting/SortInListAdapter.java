package com.dclee.recovery.view.sorting;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.util.RequestUtil;

import java.text.SimpleDateFormat;

public class SortInListAdapter extends BaseAdapter<SortInListBean.RowsBean> {
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
       // tv_sn.setText(data.get);





    }
}