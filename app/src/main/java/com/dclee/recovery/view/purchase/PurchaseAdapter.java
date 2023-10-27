package com.dclee.recovery.view.purchase;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderProd;
import com.dclee.recovery.pojo.PurchaseBean;

import androidx.recyclerview.widget.RecyclerView;

public class PurchaseAdapter extends BaseAdapter<PurchaseBean> {
    public PurchaseAdapter(Activity context) {
        super(context);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_purchase;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, PurchaseBean data) {
        View itemView = viewHolder.itemView;
        TextView order_code = itemView.findViewById(R.id.order_code);
        TextView money = itemView.findViewById(R.id.money);
        TextView weight = itemView.findViewById(R.id.weight);
        TextView prods = itemView.findViewById(R.id.prods);
        TextView statue = itemView.findViewById(R.id.statue);
        TextView create_time = itemView.findViewById(R.id.create_time);
        TextView real_name = itemView.findViewById(R.id.real_name);
        real_name.setText(data.getUsername());
        create_time.setText("("+data.getCreated_at()+")");
        statue.setText(data.getOrder_status()==0?"未结算":"已结算");
        StringBuffer buffer=new StringBuffer();
        for (OrderProd prod:
             data.getDetails()) {
            buffer.append(prod.getProduct_name()+" ");
        }
        prods.setText(buffer.toString());
        weight.setText(data.getWeight()+"kg");
        money.setText(data.getOrder_money());
        order_code.setText(String.valueOf(data.getOrder_sn()));
    }
}
