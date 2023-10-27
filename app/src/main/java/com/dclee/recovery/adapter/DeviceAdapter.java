package com.dclee.recovery.adapter;

import android.content.Context;
import android.widget.TextView;

import com.calypso.bluelib.bean.SearchResult;
import com.dclee.recovery.R;

import java.util.List;

public class DeviceAdapter extends CommonAdapter<SearchResult> {

    private Context context;
    private String clickMac;

    public DeviceAdapter(Context context, List<SearchResult> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
    }

    public void clickDevice(String clickMac) {
        this.clickMac = clickMac;
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolder holder, SearchResult bean) {
        TextView textView = holder.getView(R.id.tv_item_device);
        textView.setText(bean.getName());

        if (clickMac != null && clickMac.equals(bean.getAddress())) {
            textView.setBackgroundResource(R.drawable.bg_device_item_click);
            textView.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            textView.setBackgroundResource(R.drawable.bg_device_item);
            textView.setTextColor(context.getResources().getColor(R.color.txt_black));
        }
    }
}
