package com.dclee.recovery.adapter;

import android.content.Context;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.pojo.CheckOrderBean;

import java.util.List;

public class CheckOrderAdapter extends CommonAdapter<CheckOrderBean> {

    private Context context;
    private String clickMac;

    public CheckOrderAdapter(Context context, List<CheckOrderBean> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, CheckOrderBean bean) {
        holder.setText(R.id.tv_staff_name, "回收员：" + bean.getStaff_name())
                .setText(R.id.tv_time, "订单时间：" + bean.getCreate_at());

        TextView tv = holder.getView(R.id.tv_type);
        StringBuffer typestr = new StringBuffer();
        for (CheckOrderBean.ProductInfoBean proBean : bean.getProduct_info()) {
            typestr.append("名称："+proBean.getProduct_name()
                    +"  重量："+proBean.getVerify_weight()+" 元/KG"
                    +"  价格："+proBean.getVerify_price()+" 元\r\n");
        }
        tv.setText(typestr);
    }
}
