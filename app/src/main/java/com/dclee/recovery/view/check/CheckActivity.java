package com.dclee.recovery.view.check;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.pojo.OrderListBean;
import com.dclee.recovery.pojo.StaffUser;
import com.dclee.recovery.util.DensityUtil;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.view.create_order.ReceiveParam;
import com.dclee.recovery.wedget.TitleBar;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.longsh.optionframelibrary.OptionMaterialDialog;
import com.lzy.imagepicker.ImagePicker;

import org.xutils.http.RequestParams;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends BaseActivity {

    private TitleBar titleBar;
    private SpinKitView skvCheck;
    private LinearLayout llContent;
    private TextView tvStaffName;

    private int page = 1;
    private String mStaffId;
    private String mStaffName;

    private RequestUtil mRequestUtil;
    private List<OrderListBean> mList = new ArrayList<>();
    private List<CheckImageAdapter> mAdapterList = new ArrayList<>();
    private int mSelectAdpterNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_check;
    }

    @Override
    public void initView() {
        titleBar = findViewById(R.id.title);
        skvCheck = findViewById(R.id.skv_check);
        llContent = findViewById(R.id.ll_content);
        tvStaffName = findViewById(R.id.tv_staff_name);
        mRequestUtil = new RequestUtil(this);
        initLis();
    }

    @Override
    public void initData() {
        titleBar.setTitle("订单审核");
        Gson gson = new Gson();
        StaffUser staffUser = gson.fromJson(getIntent().getStringExtra("StaffData"),
                StaffUser.class);

        if (staffUser==null){
            T.showShort(mActivity,"参数异常");
            finish();
            return;
        }

        mStaffId = staffUser.getId();
        mStaffName = staffUser.getName();
        tvStaffName.setText(mStaffName);
        queryDatas();
    }

    private void queryDatas() {
        RequestParams requestParams = new RequestParams();
        //requestParams.addParameter("staff_id", mStaffId);
        requestParams.addParameter("staff_id", mStaffId);
        requestParams.addParameter("page", page);
        requestParams.addParameter("status", "4");
        mRequestUtil.postList("s1/orderLists", requestParams, OrderListBean.class, new RequestUtil.OnRequestFinishListener<List<OrderListBean>>() {

            @Override
            public void onRequestSuccess(List<OrderListBean> result) {
                mList.addAll(result);
                initContentView();
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {

            }
        });
    }

    private void initContentView() {
        llContent.removeAllViews();
        mAdapterList.clear();
        for (int i = 0; i < mList.size(); i++) {
            final int finalI = i;
            mList.get(i).setNum(i);
            OrderListBean bean = mList.get(i);
            LinearLayout ll = new LinearLayout(mActivity);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setPadding(getPx(14), getPx(14)
                    , getPx(14), getPx(14));
            LinearLayout.LayoutParams lp_ll = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp_ll.topMargin = getPx(10);
            ll.setLayoutParams(lp_ll);
            ll.setBackgroundResource(R.drawable.white_round10_bg);

            TextView tv = new TextView(mActivity);
            tv.setTextSize(16);
            tv.setText(bean.getProduct_name());
            ll.addView(tv);

            LinearLayout ll1 = new LinearLayout(mActivity);
            LinearLayout.LayoutParams lp_ll1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll1.setLayoutParams(lp_ll1);
            ll1.setGravity(Gravity.CENTER_VERTICAL);

            ImageView iv1 = new ImageView(mActivity);
            LinearLayout.LayoutParams lp_iv = new LinearLayout.LayoutParams(
                    getPx(14), getPx(14));
            lp_iv.rightMargin = getPx(8);
            iv1.setLayoutParams(lp_iv);
            iv1.setBackgroundResource(R.mipmap.icon_found_unitprice);
            ll1.addView(iv1);

            TextView tv1 = new TextView(mActivity);
            tv1.setTextSize(14);
            tv1.setText("单价");
            ll1.addView(tv1);

            final EditText et1 = new EditText(mActivity);
            LinearLayout.LayoutParams lp_et = new LinearLayout.LayoutParams(0
                    , getPx(40), 1);
            et1.setLayoutParams(lp_et);
            et1.setPadding(0, 0, getPx(12), 0);
            et1.setTextSize(14);
            et1.setBackgroundColor(getResources().getColor(R.color.transparent));
            et1.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            et1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ll1.addView(et1);

            TextView tv1unit = new TextView(mActivity);
            tv1unit.setTextSize(14);
            tv1unit.setText("元/KG");
            tv1unit.setEms(3);
            ll1.addView(tv1unit);
            ll.addView(ll1);

            LinearLayout ll2 = new LinearLayout(mActivity);
            ll2.setLayoutParams(lp_ll1);
            ll2.setGravity(Gravity.CENTER_VERTICAL);

            ImageView iv2 = new ImageView(mActivity);
            iv2.setLayoutParams(lp_iv);
            iv2.setBackgroundResource(R.mipmap.icon_found_type);
            ll2.addView(iv2);

            TextView tv2 = new TextView(mActivity);
            tv2.setTextSize(14);
            tv2.setText("重量");
            ll2.addView(tv2);

            final EditText et2 = new EditText(mActivity);
            et2.setLayoutParams(lp_et);
            et2.setPadding(0, 0, getPx(13), 0);
            et2.setBackgroundColor(getResources().getColor(R.color.transparent));
            et2.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            et2.setTextSize(14);
            et2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ll2.addView(et2);

            TextView tv2unit = new TextView(mActivity);
            tv2unit.setTextSize(14);
            tv2unit.setText("KG");
            tv2unit.setEms(3);
            ll2.addView(tv2unit);
            ll.addView(ll2);

            ReceiveParam receiveParam = new ReceiveParam();
            receiveParam.setImages(new ArrayList<String>());
            RecyclerView selectedImgRecycler = new RecyclerView(mActivity);
            selectedImgRecycler.setLayoutManager(new GridLayoutManager(this, 3));
            final CheckImageAdapter uploadImageAdapter = new CheckImageAdapter(this, mRequestUtil);
            mAdapterList.add(uploadImageAdapter);
            uploadImageAdapter.setUploadedImages(receiveParam.getImages());

            uploadImageAdapter.setOnImageClickListener(new CheckImageAdapter.OnImageClickListener() {
                @Override
                public void onImageClick(boolean isAddImage, String image, int position) {
                    mSelectAdpterNum = finalI;
//                    Log.e("xzw", "图片地址" + image);
//                    if (!isAddImage) {
//
//                    }
                }
            });

            selectedImgRecycler.setAdapter(uploadImageAdapter);
            ll.addView(selectedImgRecycler);


            final TextView tvMoney = new TextView(mActivity);
            tvMoney.setTextSize(16);
            tvMoney.setText("总计：0 元");
            tvMoney.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tvMoney.setTextColor(getResources().getColor(R.color.colorAccent));
            ll.addView(tvMoney);


            et1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    double price = 0;
                    double weight = 0;
                    try {
                        price = Double.parseDouble(et1.getText().toString());
                        weight = Double.parseDouble(et2.getText().toString());
                    } catch (Exception e) {

                    }

                    BigDecimal b = new BigDecimal(price * weight);
                    double money = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    tvMoney.setText("总计：" + money + " 元");
                    mList.get(finalI).setVerify_weight(weight);
                    mList.get(finalI).setVerify_price(money);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            et2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    double price = 0;
                    double weight = 0;
                    try {
                        price = Double.parseDouble(et1.getText().toString());
                        weight = Double.parseDouble(et2.getText().toString());
                    } catch (Exception e) {

                    }

                    BigDecimal b = new BigDecimal(price * weight);
                    double money = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    tvMoney.setText("总计：" + money + " 元");
                    mList.get(finalI).setVerify_weight(weight);
                    mList.get(finalI).setVerify_price(money);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            llContent.addView(ll);
        }
    }

    public void toCheckOrder(View view) {
        final OptionMaterialDialog mMaterialDialog = new OptionMaterialDialog(mActivity);
        mMaterialDialog.setTitle("提示")
                .setMessage("是否提交审核？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitCheck();
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        })
                .setCanceledOnTouchOutside(true).show();

    }

    private void submitCheck() {

        if (mList == null || mList.size() <= 0) {
            T.showShort(mActivity, "无品类需要审核");
            return;
        }

        boolean isHaveNotCheck = false;
        JsonArray jsProducts = new JsonArray();
        for (int i = 0; i < mList.size(); i++) {
            OrderListBean bean = mList.get(i);
            if (bean.getVerify_price() == 0 || bean.getVerify_weight() == 0) {
                isHaveNotCheck = true;
                break;
            }
            JsonObject jsProduct = new JsonObject();
            jsProduct.addProperty("product_id", bean.getProduct_id());
            jsProduct.addProperty("product_name", bean.getProduct_name());
            jsProduct.addProperty("predict_weight", bean.getProduct_weight());
            jsProduct.addProperty("verify_weight", bean.getVerify_weight());
            jsProduct.addProperty("predict_price", bean.getProduct_price());
            jsProduct.addProperty("verify_price", bean.getVerify_price());
            if (!TextUtils.isEmpty(mAdapterList.get(i).getUploadImages()))
                jsProduct.addProperty("img", mAdapterList.get(i).getUploadImages());
            jsProducts.add(jsProduct);
        }

        if (isHaveNotCheck) {
            T.showShort(mActivity, "请审核完全所有品类");
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("productInfo", jsProducts.toString());
        requestParams.addParameter("staff_id", mStaffId);
        requestParams.addParameter("staff_Name", mStaffName);
        requestParams.addParameter("verify_id", Config.userId);
        requestParams.addParameter("verify_name", Config.userName);
        mRequestUtil.doPost("s1/orderVerificated", requestParams, String.class,
                new RequestUtil.OnRequestFinishListener<String>() {

                    @Override
                    public void onRequestSuccess(String result) {
                        T.showShort(mActivity, "提交成功");
                        finish();
                    }

                    @Override
                    public void onRequestFail(int errorCode, String desc) {

                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && resultCode != ImagePicker.RESULT_CODE_ITEMS) {
            return;
        }
        mAdapterList.get(mSelectAdpterNum).onActivityResult(requestCode, resultCode, data);
    }

    private int getPx(int size) {
        return DensityUtil.dpToPx(mActivity, size);
    }

    private void initLis() {

    }
}
