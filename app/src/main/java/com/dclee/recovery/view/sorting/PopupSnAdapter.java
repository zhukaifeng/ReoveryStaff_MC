package com.dclee.recovery.view.sorting;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderCreateBean;
import com.dclee.recovery.pojo.SortDefaultBean;

public class PopupSnAdapter extends BaseAdapter<OrderCreateBean.DataDTO> {
    private Activity mContent;

    public PopupSnAdapter(Activity context) {
        super(context);
        mContent = context;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_sn_popup;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final OrderCreateBean.DataDTO data) {
        View itemView = viewHolder.itemView;

        TextView tv_sn = itemView.findViewById(R.id.tv_sn);
        TextView tv_type = itemView.findViewById(R.id.tv_type);
        TextView tv_cat = itemView.findViewById(R.id.tv_cat);
        TextView tv_count = itemView.findViewById(R.id.tv_count);

        if (!TextUtils.isEmpty(data.getOrderNo())){
            tv_sn.setText(data.getOrderNo());
        }
        if (!TextUtils.isEmpty(data.getCategoryName())){
            tv_type.setText(data.getCategoryName());
        }
        if (!TextUtils.isEmpty(data.getProductName())){
            tv_cat.setText(data.getProductName());
        }
        if (!TextUtils.isEmpty(data.getNetWeight())){
            tv_count.setText(data.getNetWeight());
        }


    }
}