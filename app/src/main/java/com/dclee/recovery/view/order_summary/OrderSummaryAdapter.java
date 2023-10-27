package com.dclee.recovery.view.order_summary;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.OrderProd;
import com.dclee.recovery.pojo.OrderSummaryBean;
import com.dclee.recovery.pojo.SummaryChildItem;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class OrderSummaryAdapter extends BaseAdapter<OrderBean> {
    public OrderSummaryAdapter(Activity context) {
        super(context);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_order_summary;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, OrderBean data) {
        LinearLayout orderSummaryChild = viewHolder.itemView.findViewById(R.id.order_summary_child);
        TextView order_sn = viewHolder.itemView.findViewById(R.id.order_sn);
        order_sn.setText("订单号\n" + data.getOrder_sn());
        inflatItemChild(orderSummaryChild, position);
    }

    private void inflatItemChild(LinearLayout childContainers, int position) {
        if (childContainers != null) {
            childContainers.removeAllViews();
            List<OrderProd> items = getDatas().get(position).getDetails();
            for (int i = 0; i < items.size(); i++) {
                OrderProd item = items.get(i);
                View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_summary_child, childContainers, false);
                TextView uni_price = itemView.findViewById(R.id.uni_price);
                itemView.setSelected(i % 2 != 0);
                TextView money = itemView.findViewById(R.id.money);
                TextView name = itemView.findViewById(R.id.name);
                TextView weight = itemView.findViewById(R.id.weight);
                name.setText(item.getProduct_name());
                weight.setText(item.getWeight());
                uni_price.setText(item.getProduct_price());
                double priceVal = Double.parseDouble(item.getProduct_price());
                money.setText(priceVal * Double.parseDouble(item.getWeight()) + "");
                childContainers.addView(itemView);
            }
        }
    }
}
