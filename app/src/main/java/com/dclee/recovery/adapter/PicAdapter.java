package com.dclee.recovery.adapter;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.pojo.OrderProd;

public class PicAdapter extends BaseAdapter<String> {

    private Activity context;
    public PicAdapter(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_pic;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, String data) {
        ImageView ivUser = viewHolder.itemView.findViewById(R.id.iv_user);
        Glide.with(context).load(data).into(ivUser);
        Log.e("requestParams",data);
    }

}
