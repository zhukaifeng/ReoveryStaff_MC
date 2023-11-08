package com.dclee.recovery.view.sorting;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.pojo.SummaryBean;
import com.dclee.recovery.util.RequestUtil;

import java.text.SimpleDateFormat;

public class SummaryAdapter extends BaseAdapter<SummaryBean.RowsDTO> {
    private RequestUtil mRequestUtil;
    public static final int REQ_RECV_GOODS = 0x233;
    private SimpleDateFormat resultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat originDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Activity mContent;

    public SummaryAdapter(Activity context) {
        super(context);
        mContent = context;
        mRequestUtil = new RequestUtil(context);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_summary;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final SummaryBean.RowsDTO data) {
        View itemView = viewHolder.itemView;

        TextView tv_person = itemView.findViewById(R.id.tv_person);
        TextView tv_category = itemView.findViewById(R.id.tv_category);
        TextView tv_count = itemView.findViewById(R.id.tv_count);
        TextView tv_pay = itemView.findViewById(R.id.tv_pay);


        if (!TextUtils.isEmpty(data.getSorterText())) {
            tv_person.setText(data.getSorterText());

        }


        if (!TextUtils.isEmpty(data.getNetWeight())) {
            tv_count.setText(data.getNetWeight());

        }

        if (!TextUtils.isEmpty(data.getPieceAmount())) {
            tv_pay.setText(data.getPieceAmount());
        }

        if (!TextUtils.isEmpty(data.getProductIdText())) {
            tv_category.setText(data.getProductIdText());
        }

    }
}