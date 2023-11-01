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
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.util.RequestUtil;

import java.text.SimpleDateFormat;

public class PopupCategoryAdapter extends BaseAdapter<SortDefaultBean.DataDTO.SysProductTypeParentChooseListDTO> {
    private Activity mContent;

    public PopupCategoryAdapter(Activity context) {
        super(context);
        mContent = context;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_for_popup;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final SortDefaultBean.DataDTO.SysProductTypeParentChooseListDTO data) {
        View itemView = viewHolder.itemView;

        TextView tv_name = itemView.findViewById(R.id.tv_name);


        if (!TextUtils.isEmpty(data.getProductTypeName())) {
            tv_name.setText(data.getProductTypeName());
        }
        if (data.isSelected()){
            tv_name.setTextColor(getContext().getColor(R.color.white));
            tv_name.setBackgroundColor(getContext().getColor(R.color.textGrayColor));
        }else {
            tv_name.setTextColor(getContext().getColor(R.color.black));
            tv_name.setBackgroundColor(getContext().getColor(R.color.white));
        }

    }
}