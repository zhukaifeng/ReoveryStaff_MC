package com.dclee.recovery.view.orderlist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.base.CacheUtil;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.bean.event.CancelOrderEvent;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.view.LoginActivity;
import com.dclee.recovery.view.MainActivity;
import com.dclee.recovery.view.create_order.CreateOrderActivity;
import com.longsh.optionframelibrary.OptionMaterialDialog;

import java.text.SimpleDateFormat;

import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

public class OrderAdapter extends BaseAdapter<OrderBean> {
    private RequestUtil mRequestUtil;
    public static final int REQ_RECV_GOODS = 0x233;
    private SimpleDateFormat resultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat originDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Activity mContent;

    public OrderAdapter(Activity context) {
        super(context);
        mContent = context;
        mRequestUtil = new RequestUtil(context);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_order;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position, final OrderBean data) {
        View itemView = viewHolder.itemView;
        final TextView prod_names = itemView.findViewById(R.id.prod_names);
        final TextView weight = itemView.findViewById(R.id.weight);
        TextView order_num = itemView.findViewById(R.id.order_num);
        TextView appoint_time = itemView.findViewById(R.id.appoint_time);
        TextView order_time = itemView.findViewById(R.id.order_time);
        TextView real_name = itemView.findViewById(R.id.real_name);
        TextView to_dispatch_order = itemView.findViewById(R.id.to_dispatch_order);
        TextView phone = itemView.findViewById(R.id.phone);
        View diagPhone = itemView.findViewById(R.id.todiag_phone);
        final TextView address_detail = itemView.findViewById(R.id.address_detail);
        TextView order_status = itemView.findViewById(R.id.order_status);
        Button toCheckCancel = itemView.findViewById(R.id.to_check_cancel);
        final Button to_cancel_order = itemView.findViewById(R.id.to_cancel_order);
        final View to_get_order = itemView.findViewById(R.id.to_get_order);
        final View to_get_goods = itemView.findViewById(R.id.to_get_goods);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < data.getDetails().size(); i++) {
            buffer.append(data.getDetails().get(i).getProduct_name()).append(" ");
        }

        toCheckCancel.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(data.getReject_check_status())) {
            if ("0".equals(data.getReject_check_status())) {
                order_status.setText("待审核");
                toCheckCancel.setVisibility(View.VISIBLE);
            } else if ("1".equals(data.getReject_check_status())) {
                order_status.setText("已审核");
            } else {
                order_status.setText("未通过");
            }
        } else {
            if (data.getOrder_state() == Config.STATE_START) {
                order_status.setText("待派单");
            } else if (data.getOrder_state() == Config.STATE_UNRECEIVE) {
                order_status.setText("已预约");
            } else if (data.getOrder_state() == Config.STATE_ERROR) {
                order_status.setText("订单异常");
            } else if (data.getOrder_state() == Config.STATE_USER_CANCEL) {
                order_status.setText("订单取消");
            } else if (data.getOrder_state() == Config.STATE_FINISH) {
                order_status.setText("已取货");
            } else if (data.getOrder_state() == Config.STATE_CHECK) {
                order_status.setText("已审核");
            } else if (data.getOrder_state() == Config.STATE_CANCEL) {
                order_status.setText("取消预约");
            } else if (data.getOrder_state() == Config.STATE_UNGETGOODS) {
                order_status.setText("待取货");
            } else {
                order_status.setText("已核验");
            }
        }

        String assignEnable = PreferencesUtils.getString(getContext(), "assignEnable", "0");
        if ("0".equals(assignEnable)) {
            to_get_goods.setVisibility(data.getOrder_state() == 7 ? View.VISIBLE : View.GONE);
            to_get_order.setVisibility(data.getOrder_state() == 1 ? View.VISIBLE : View.GONE);
            to_cancel_order.setVisibility(data.getOrder_state() == 1 ? View.VISIBLE : View.GONE);
        } else {
            to_get_goods.setVisibility(View.GONE);
            to_get_order.setVisibility(View.GONE);
            to_cancel_order.setVisibility(View.GONE);
        }

        to_dispatch_order.setVisibility(data.getOrder_state() == 0 ? View.VISIBLE : View.GONE);
        prod_names.setText(buffer.toString());
        weight.setText(data.getWeight() + "kg");
        order_num.setText("订单编号:" + data.getOrder_sn());
        try {
            order_time.setText(resultDateFormat.format(originDateFormat.parse(data.getCreated_at())));
        } catch (Exception e) {
            order_time.setText(data.getCreated_at());
        }
        try {
            appoint_time.setText(resultDateFormat.format(originDateFormat.parse(data.getOrder_time())));
        } catch (Exception e) {
            appoint_time.setText(data.getOrder_time());
        }
        if (data.getAddress() != null) {
            diagPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri uri = Uri.parse("tel:" + data.getAddress().getPhone());
                    intent.setData(uri);
                    getContext().startActivity(intent);
                }
            });
            real_name.setText(data.getAddress().getContact());
            phone.setText(data.getAddress().getPhone());
            address_detail.setText(data.getAddress().getProvince() + " " + data.getAddress().getCity() + " " + data.getAddress().getDistrict() + " " + data.getAddress().getAddress());
        }

        to_dispatch_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(),
                        OrderAdminDetailActivity.class).putExtra("order_id",
                        data.getId()).putExtra("addressDetail",
                        address_detail.getText().toString()).putExtra("weightDetail",
                        weight.getText().toString()).putExtra("prodNames",
                        prod_names.getText().toString()));
            }
        });

        toCheckCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(),
                        OrderAdminDetailActivity.class).putExtra("order_id",
                        data.getId()).putExtra("addressDetail",
                        address_detail.getText().toString()).putExtra("weightDetail",
                        weight.getText().toString()).putExtra("prodNames",
                        prod_names.getText().toString()));
            }
        });

        View.OnClickListener onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(data.getReject_check_status()) ||
                        data.getOrder_state() == Config.STATE_START ||
                        data.getOrder_state() == Config.STATE_USER_CANCEL) {
                    getContext().startActivity(new Intent(getContext(),
                            OrderAdminDetailActivity.class).putExtra("order_id",
                            data.getId()).putExtra("addressDetail",
                            address_detail.getText().toString()).putExtra("weightDetail",
                            weight.getText().toString()).putExtra("prodNames",
                            prod_names.getText().toString()));
                } else {
                    getContext().startActivity(new Intent(getContext(),
                            OrderDetailActivity.class).putExtra("order_id",
                            data.getId()).putExtra("addressDetail",
                            address_detail.getText().toString()).putExtra("weightDetail",
                            weight.getText().toString()).putExtra("prodNames",
                            prod_names.getText().toString()));
                }
            }
        };

        viewHolder.itemView.setOnClickListener(onItemClickListener);
        to_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View ll_dialog = LayoutInflater.from(mContent).inflate(R.layout.layout_dialog_input, null, false);
                final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(mContent);
                mMaterialDialog.setContentView(ll_dialog);
                mMaterialDialog.setTitle("提示")
                        .setMessage("是否取消该订单？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText et_memo = ll_dialog.findViewById(R.id.et_memo);
                                if (et_memo != null) {
                                    if (TextUtils.isEmpty(et_memo.getText().toString().trim())) {
                                        T.showShort(mContent, "请将信息输入完整");
                                        return;
                                    }
                                    EventBus.getDefault().post(new CancelOrderEvent(data.getId(), et_memo.getText().toString()));
                                }

                                mMaterialDialog.dismiss();
                            }
                        })
                        .setCanceledOnTouchOutside(true).show();
            }
        });

        to_get_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivityForResult(new Intent(getContext(), CreateOrderActivity.class).putExtra("isGetGoods", true).putExtra("order_id", data.getId()), REQ_RECV_GOODS);
//                DialogUtil.showComfirmDialog(getContext(), "确认已取到货品？？", new DialogUtil.OnComfirmClickListener() {
//                    @Override
//                    public void onComfirmClick() {
//                        RequestParams requestParams = new RequestParams();
//                        requestParams.addParameter("order_id", data.getId());
//                        mRequestUtil.doPost("s1/orderCheck", requestParams, Object.class, new RequestUtil.OnRequestFinishListener<Object>() {
//
//                            @Override
//                            public void onRequestSuccess(Object result) {
//                                ToastUtil.showToast(getContext(), "订单确认取货成功！");
//                                to_get_goods.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void onRequestFail(int errorCode, String desc) {
//
//                            }
//                        });
//                    }
//                });
            }
        });
        to_get_order.setOnClickListener(onItemClickListener);
//                DialogUtil.showComfirmDialog(getContext(), "是否确认接此订单？", new DialogUtil.OnComfirmClickListener() {
//                    @Override
//                    public void onComfirmClick() {
//                        RequestParams requestParams = new RequestParams();
//                        requestParams.addParameter("order_id", data.getId());
//                        long arriveTImeMillures = System.currentTimeMillis() + 60 * 1000 * 60 * 2;
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        requestParams.addParameter("arrive_time", simpleDateFormat.format(arriveTImeMillures));
//                        mRequestUtil.doPost("s1/orderCreate", requestParams, Object.class, new RequestUtil.OnRequestFinishListener<Object>() {
//
//                            @Override
//                            public void onRequestSuccess(Object result) {
//                                ToastUtil.showToast(getContext(), "派单成功！");
//                                to_get_order.setVisibility(View.GONE);
//                                to_get_goods.setVisibility(View.VISIBLE);
//                            }
//
//                            @Override
//                            public void onRequestFail(int errorCode, String desc) {
//
//                            }
//                        });
//                    }
//                });
    }
}