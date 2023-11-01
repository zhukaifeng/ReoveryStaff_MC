package com.dclee.recovery.view.sorting;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.bean.db.SortInBean;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.SortReqDetailBean;
import com.dclee.recovery.util.RequestUtil;
import com.sunmi.utils.DoubleUtils;

import java.text.SimpleDateFormat;

public class SortInDetailAdapter extends BaseAdapter<SortInBean> {


    public SortInDetailAdapter(Activity context) {
        super(context);

    }

    @Override
    public int getItemLayout() {
        return R.layout.item_sort_detail;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final SortInBean data) {
        View itemView = viewHolder.itemView;

        TextView tv_name = itemView.findViewById(R.id.tv_name);
        TextView tv_type = itemView.findViewById(R.id.tv_type);
        TextView tv_weight = itemView.findViewById(R.id.tv_weight);
        TextView tv_buckle = itemView.findViewById(R.id.tv_buckle);
        TextView tv_netweight = itemView.findViewById(R.id.tv_netweight);
        TextView tv_operate = itemView.findViewById(R.id.tv_operate);


        if (!TextUtils.isEmpty(data.getSorterName())){
            tv_name.setText(data.getSorterName());
        }
        if (!TextUtils.isEmpty(data.getProductName())){
            tv_type.setText(data.getProductName());
        }
        if (!TextUtils.isEmpty(data.getWeight())){
            tv_weight.setText(data.getWeight());
        }
        if (!TextUtils.isEmpty(data.getDeductWeight())){
            tv_buckle.setText(data.getDeductWeight());
        }
        if (!TextUtils.isEmpty(data.getDeductWeight())&&
                !TextUtils.isEmpty(data.getWeight())){
            double weight = Double.parseDouble(data.getWeight().toString());
            double buckle = Double.parseDouble(data.getDeductWeight().toString());
            double netweight = DoubleUtils.sub(weight, buckle);
            tv_netweight.setText(String.valueOf(netweight));
        }
        if (!TextUtils.isEmpty(data.getPicIdStr()))
        Log.d("zkf","picurl:" + data.getPicIdStr());
    }
}