package com.dclee.recovery.adapter;

import android.app.Activity;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderProd;

import androidx.recyclerview.widget.RecyclerView;

public class SelectedProdAdapter extends BaseAdapter<OrderProd> {

    public SelectedProdAdapter(Activity context) {
        super(context);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_order_prod;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, OrderProd data) {
        TextView prodName = viewHolder.itemView.findViewById(R.id.prod_name);
        if(data.getType()==1){
            prodName.setText(data.getProduct_name());
        } else{
            prodName.setText(data.getProduct_name());
        }

        prodName.setSelected(data.isSelected());
    }

}
