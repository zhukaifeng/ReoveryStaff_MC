package com.dclee.recovery.adapter;

import android.app.Activity;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderProd;

public class ProductAdapter extends BaseAdapter<OrderProd> {

    public ProductAdapter(Activity context) {
        super(context);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_product;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, OrderProd data) {

        TextView tvName = viewHolder.itemView.findViewById(R.id.tv_name);
        TextView tvPrice = viewHolder.itemView.findViewById(R.id.tv_price);
        TextView tvWeight = viewHolder.itemView.findViewById(R.id.tv_weight);
        TextView tvTotal = viewHolder.itemView.findViewById(R.id.tv_total);
        TextView tvDelete = viewHolder.itemView.findViewById(R.id.tv_delete);

        tvName.setText(data.getProduct_name());
        tvPrice.setText(data.getProduct_price());
        tvTotal.setText(data.getTotal());
        tvWeight.setText(data.getWeight());

    }

}
