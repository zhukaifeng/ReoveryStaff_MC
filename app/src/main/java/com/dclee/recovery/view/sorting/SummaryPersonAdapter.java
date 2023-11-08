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
import com.dclee.recovery.pojo.SortDefaultBean;
import com.dclee.recovery.pojo.SorterBean;

public class SummaryPersonAdapter extends BaseAdapter<SorterBean.DataDTO> {
    private Activity mContent;

    public SummaryPersonAdapter(Activity context) {
        super(context);
        mContent = context;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_for_popup;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final SorterBean.DataDTO data) {
        View itemView = viewHolder.itemView;

        TextView tv_name = itemView.findViewById(R.id.tv_name);


        if (!TextUtils.isEmpty(data.getNickName())) {
            tv_name.setText(data.getNickName());
        }


    }
}