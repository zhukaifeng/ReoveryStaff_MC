package com.dclee.recovery.view.create_order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.bumptech.glide.Glide;
import com.calypso.bluelib.bean.MessageBean;
import com.calypso.bluelib.bean.SearchResult;
import com.calypso.bluelib.listener.OnConnectListener;
import com.calypso.bluelib.listener.OnReceiveMessageListener;
import com.calypso.bluelib.listener.OnSearchDeviceListener;
import com.calypso.bluelib.listener.OnSendMessageListener;
import com.calypso.bluelib.manage.BlueManager;
import com.calypso.bluelib.utils.TypeConversion;
import com.dclee.recovery.MyApp;
import com.dclee.recovery.R;
import com.dclee.recovery.adapter.ProductAdapter;
import com.dclee.recovery.adapter.SelectedProdAdapter;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.base.CacheUtil;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.bean.event.UploadPic;
import com.dclee.recovery.bean.net.OrderCheckEntity;
import com.dclee.recovery.pojo.CardNetBean;
import com.dclee.recovery.pojo.NameBean;
import com.dclee.recovery.pojo.OrderBean;
import com.dclee.recovery.pojo.OrderProd;
import com.dclee.recovery.pojo.PositionDetail;
import com.dclee.recovery.pojo.Product;
import com.dclee.recovery.pojo.QRParseBean;
import com.dclee.recovery.util.BDLocationUtils;
import com.dclee.recovery.util.DialogUtil;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.FileUtil;
import com.dclee.recovery.util.LogUtils;
import com.dclee.recovery.util.NfcUtils;
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.util.ToastUtil;
import com.dclee.recovery.view.SelectOrderProdActivity;
import com.dclee.recovery.view.device.DeviceActivity;
import com.dclee.recovery.view.nfc.MyNfcActivity;
import com.dclee.recovery.view.purchase.OnSelectDateListener;
import com.dclee.recovery.wedget.TitleBar;
import com.lzy.imagepicker.ImagePicker;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.functions.Consumer;

import static com.baidu.mapapi.BMapManager.getContext;
import static com.dclee.recovery.util.HexUtils.hexStringToString;
import static com.dclee.recovery.util.NfcUtils.ByteArrayToHexString;
import static com.dclee.recovery.util.NfcUtils.flipHexStr;

public class CreateOrderActivity extends BaseActivity {
    private TitleBar titleBar;
    private RequestUtil requestUtil;
    private TagFlowLayout mProdList;
    private UploadImageAdapter uploadImageAdapter;
    private int orderId;
    private RecyclerView selectedImgRecycler;
    private TextView num_unit;
    private ReceiveParam receiveParam;
    private EditText totalCount;
    private View scaneCode;
    private RequestUtil mRequestUtil;
    private TextView prod_total_price;
    private TextView total_price;
    private TextView user_name;
    private EditText uni_price;
    private ProdTagAdapter prodAdapter;
    private View addProd;
    private RelativeLayout rlCard;
    private EditText etCard;
    private TextView tvWeightList;
    private RecyclerView rvProduct;

    private double mWeightValue;
    private String mWeightData;
    private int mParmTag;
    private BlueManager bluemanage;
    private StringBuilder stringBuilder;
    private List<SearchResult> mDevices;
    private OnConnectListener onConnectListener;
    private OnSendMessageListener onSendMessageListener;
    private OnSearchDeviceListener onSearchDeviceListener;
    private OnReceiveMessageListener onReceiveMessageListener;
    private String mBlueMac;
    private Context mContext;
    private final int REQ_SCANE_CODE = 0x233;
    private final int REQ_ADD_PROD = 0x234;
    private int customUserId;

    private boolean isGetGoods;

    private ProductAdapter mProductAdapter;
    private List<OrderProd> mProducts = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    T.showShort(mContext, message);
                    //tvOrderWeight.setText(message);
                    break;

                case 3:
                    //e("接收完成！");
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append(message);
                    getWeightParm(hexStringToString(stringBuilder.toString()));
                    break;
            }
        }
    };

    // 录音功能相关
    private MediaRecorder mMediaRecorder; // MediaRecorder 实例
    private boolean isRecording; // 录音状态
    private String fileName; // 录音文件的名称
    private String filePath; // 录音文件存储路径

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_order;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        receiveParam = new ReceiveParam();
        receiveParam.setImages(new ArrayList<String>());
        mRequestUtil = new RequestUtil(this);
        orderId = getIntent().getIntExtra("order_id", 0);
        receiveParam.setOrder_id(orderId);
        totalCount = findViewById(R.id.totalCount);
        num_unit = findViewById(R.id.num_unit);
        uni_price = findViewById(R.id.uni_price);
        total_price = findViewById(R.id.total_price);
        user_name = findViewById(R.id.user_name);
        scaneCode = findViewById(R.id.to_scane_code);
        addProd = findViewById(R.id.btn_add_prod);
        prod_total_price = findViewById(R.id.prod_total_price);
        titleBar = findViewById(R.id.title);
        mProdList = findViewById(R.id.prod_names);
        rlCard = findViewById(R.id.rl_card);
        etCard = findViewById(R.id.et_card);
        tvWeightList = findViewById(R.id.tv_weight_list);
        rvProduct = findViewById(R.id.rv_product);

        rvProduct.setLayoutManager(new LinearLayoutManager(this));
        mProductAdapter = new ProductAdapter(this);
        mProductAdapter.setDatas(mProducts);
        rvProduct.setAdapter(mProductAdapter);
        mProductAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<OrderProd> newProducts = new ArrayList<>();
                for (int i = 0; i < mProducts.size(); i++) {
                    if (i != position) {
                        newProducts.add(mProducts.get(i));
                    }
                }
                mProducts.clear();
                mProducts.addAll(newProducts);
                mProductAdapter.notifyDataSetChanged();

                double totalNum = 0;
                for (int i = 0; i < mProducts.size(); i++) {
                    totalNum = totalNum + Double.parseDouble(mProducts.get(i).getTotal());
                }
                total_price.setText(doubleToString(totalNum));
            }
        });

        isGetGoods = getIntent().getBooleanExtra("isGetGoods", false);
        requestUtil = new RequestUtil(this);
        if (isGetGoods) {
            titleBar.setTitle("取货");
            addProd.setVisibility(View.VISIBLE);
        } else {
            scaneCode.setVisibility(View.VISIBLE);
            addProd.setVisibility(View.VISIBLE);
        }
        titleBar.setOnRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {
                rlCard.setVisibility(View.VISIBLE);
                etCard.setFocusable(true);
                etCard.setFocusableInTouchMode(true);
                etCard.requestFocus();
//                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
//                        .hideSoftInputFromWindow(CreateOrderActivity.this
//                                .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        selectedImgRecycler = findViewById(R.id.selected_img_recycler);
        selectedImgRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        uploadImageAdapter = new UploadImageAdapter(this, requestUtil);
        uploadImageAdapter.setUploadedImages(receiveParam.getImages());
        selectedImgRecycler.setAdapter(uploadImageAdapter);
        totalCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (receiveParam == null || receiveParam.getProduct() == null) {
                        return;
                    }
                    if (totalCount.isEnabled()) {
                        if (TextUtils.isEmpty(charSequence.toString())) {
                            mWeightValue = 0;
                        } else {
                            mWeightValue = Double.parseDouble(charSequence.toString());
                        }
                        setProdWeight();
                    } else {
                        calculateWeightValue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        uni_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] numbers = charSequence.toString().split("\\.");
                if (numbers.length > 1 && numbers[1].length() > 2) {
                    uni_price.setText(numbers[0] + "." + numbers[1].substring(0, 2));
                    return;
                }
                calculateProdPrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        uni_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    checkPrice();
                }
            }
        });

        mContext = this;
        mDevices = new ArrayList<>();
        stringBuilder = new StringBuilder();

        etCard.setOnEditorActionListener(new OnEditorActionListenerImpl());
        etCard.setCursorVisible(false);//隐藏光标
        rlCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCard.setVisibility(View.GONE);
                etCard.setFocusable(false);
                etCard.setFocusableInTouchMode(false);
            }
        });

        PackageManager packageManager = this.getPackageManager();
        boolean isNfcEnable = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC);

        //nfc初始化设置
        if (isNfcEnable) {
            NfcUtils nfcUtils = new NfcUtils(this);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                startRecord();
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onUploadPicEvent(UploadPic event) {
        receiveParam.setImages(event.getPics());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启前台调度系统
        if (NfcUtils.mNfcAdapter != null)
            NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            //获取 Tag 读取 ID 得到字节数组  转字符串 转码  得到卡号（默认16进制 这请自便）
            Long cardNo = Long.parseLong(flipHexStr(ByteArrayToHexString(tag.getId())), 16);
            String num = cardNo.toString();
            if (num.length() == 9) {
                num = "0" + num;
            } else if (num.length() == 8) {
                num = "00" + num;
            }
            queryUserNameByCard(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class OnEditorActionListenerImpl implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
            String cardNum = etCard.getText().toString()
                    .replaceAll("\r", "").replaceAll("\n", "");
            LogUtils.write("读到到卡号 " + cardNum + " length " + cardNum.length());
            try {
                //findPerson(cardNum);
                queryUserNameByCard(cardNum);
            } catch (Exception e) {
                LogUtils.write("读卡异常 " + e.getMessage());
            }
            etCard.setText("");
            rlCard.setVisibility(View.GONE);
            etCard.setFocusable(false);
            etCard.setFocusableInTouchMode(false);
            return false;
        }
    }

    private void setProdWeight() {
//        receiveParam.getProduct().get(prodAdapter.getSelectedItem()).
//                setCount(Double.valueOf(doubleToString(mWeightValue)));
//        calculatePrice();
    }

    public void toScaneQrCode(View view) {
        new RxPermissions(this).request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        startActivityForResult(intent, REQ_SCANE_CODE);
                    }
                });
    }

    //TODO 在这里计算稳定重量，注意计算到稳定值后，调用setProdWeight将稳定值保存到商品参数内
    private void calculateWeightValue() {
        setProdWeight();
    }

    public void toAddProds(View view) {
        startActivityForResult(new Intent(this, SelectOrderProdActivity.class), REQ_ADD_PROD);
    }


    private void checkNewPrice() {
        if (prodAdapter == null) {
            return;
        }
        OrderProd item = prodAdapter.getItem(prodAdapter.getSelectedItem());
        double minPrice = item.getMin_price();
        double maxPrice = item.getMax_price();
        ReceiveParam.MkOrderProd prod = receiveParam.getProduct().get(prodAdapter.getSelectedItem());
        double price = prod.getPrice();
        if (price == 0) {
            uni_price.setText("");
            return;
        }
        if (price < minPrice) {
            prod.setPrice((int) minPrice);
            uni_price.setText(String.valueOf(minPrice));
        } else if (price > maxPrice) {
            prod.setPrice((int) maxPrice);
            uni_price.setText(String.valueOf(maxPrice));
        }
    }


    private void checkPrice() {
        if (prodAdapter == null) {
            return;
        }
        OrderProd item = prodAdapter.getItem(prodAdapter.getSelectedItem());
        double minPrice = item.getMin_price();
        double maxPrice = item.getMax_price();
        ReceiveParam.MkOrderProd prod = receiveParam.getProduct().get(prodAdapter.getSelectedItem());
        double price = prod.getPrice();
        if (price == 0) {
            uni_price.setText("");
            return;
        }
        if (price < minPrice) {
            prod.setPrice((int) minPrice);
            uni_price.setText(String.valueOf(minPrice));
        } else if (price > maxPrice) {
            prod.setPrice((int) maxPrice);
            uni_price.setText(String.valueOf(maxPrice));
        }
    }

    private void calculateProdPrice() {
        if (prodAdapter != null) {
            try {
                OrderProd item = prodAdapter.getItem(prodAdapter.getSelectedItem());
                prod_total_price.setText(doubleToString(Double.parseDouble(item.getWeight()) * Double.parseDouble(uni_price.getText().toString())));
            } catch (Exception e) {

            }
        }
    }

    private void calculatePrice() {
        if (prodAdapter != null && receiveParam.getProduct() != null) {
            try {
                receiveParam.getProduct().get(prodAdapter.getSelectedItem()).setPrice(TextUtils.isEmpty(uni_price.getText().toString()) ? 0 : Double.parseDouble(uni_price.getText().toString()));
                double weight = receiveParam.getProduct().get(prodAdapter.getSelectedItem()).getCount();
                prod_total_price.setText(doubleToString(weight * receiveParam.getProduct().get(prodAdapter.getSelectedItem()).getPrice()));
                double totalPrice = 0;
                for (int index = 0; index < receiveParam.getProduct().size(); index++) {
                    ReceiveParam.MkOrderProd item = receiveParam.getProduct().get(index);
                    totalPrice += item.getCount() * item.getPrice();
                }
                total_price.setText(doubleToString(totalPrice));

                OrderProd item = prodAdapter.getItem(prodAdapter.getSelectedItem());
                item.setTotal(prod_total_price.getText().toString());

            } catch (Exception e) {

            }
        }
    }

    private String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    private void loadProdDatas(List<OrderProd> prods) {
        List<ReceiveParam.MkOrderProd> temp = new ArrayList<>();
        for (OrderProd orderProd : prods) {
            ReceiveParam.MkOrderProd item = new ReceiveParam.MkOrderProd();
            item.setProduct_id(orderProd.getProduct_id());
            item.setProduct_name(orderProd.getProduct_name());
            item.setCount(0);
            item.setPrice(0);
            temp.add(item);
        }
        receiveParam.setProducts(temp);
        prodAdapter = new ProdTagAdapter(prods);
        mProdList.setAdapter(prodAdapter);
    }

    @Override
    public void initData() {
        if (orderId != 0) {
            RequestParams requestParams = new RequestParams();
            requestParams.addParameter("order_id", orderId);
            requestUtil.doPost("s1/orderDetail", requestParams, OrderBean.class, new RequestUtil.OnRequestFinishListener<OrderBean>() {
                @Override
                public void onRequestSuccess(OrderBean result) {
                    Log.i("queryResultOrder", JSON.toJSONString(result));
                    loadProdDatas(result.getDetails());
                    user_name.setText(result.getAddress().getContact());
                    calculatePrice();
                }

                @Override
                public void onRequestFail(int errorCode, String desc) {

                }
            });
        }

        mBlueMac = PreferencesUtils.getString(getActivity(), "blue_mac", "");
        if (TextUtils.isEmpty(mBlueMac)) {
            T.showShort(mContext, "清先选择蓝牙设备");
            //startActionWithFinish(DeviceActivity.class);
        } else {
            initBlueManager();
        }
    }

    private void uploadAudio(String order_id) {
        if (TextUtils.isEmpty(fileName) || !new File(fileName).exists()) {
            e("not have audio file");
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", new File(fileName));
        requestParams.addBodyParameter("order_id", order_id);
        final LoadingDialog loadingDialog = new LoadingDialog(mActivity)
                .setLoadingText("上传中...");
        loadingDialog.show();

        requestUtil.doNewPost("s1/recording", requestParams, new RequestUtil.OnRequestFinishListener2() {
            @Override
            public void onRequestSuccess(String result) {
                loadingDialog.close();
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {
                T.showShort(mActivity, "上传失败");
                loadingDialog.close();
            }
        });
    }

    private void getWeightParm(String data) {
        if (TextUtils.isEmpty(data))
            return;
        data = data.replaceAll("\n", "e").replaceAll("\r", "e");
        if (mParmTag < 3) {
            mParmTag++;
            mWeightData = mWeightData + data;
        } else {
            mWeightData = mWeightData.substring(mWeightData.indexOf("ee") + 4, mWeightData.indexOf("ee") + 13);
            //这里是重量
            e("mWeightData：" + mWeightData);
            if (mWeightData.contains("kg")) {
                try {
                    mWeightData = mWeightData.substring(0, mWeightData.length() - 2);
                    mWeightValue = Double.valueOf(mWeightData);
                    totalCount.setText(doubleToString(mWeightValue));
                } catch (Exception e) {
                    e("getWeightParm error " + e.getMessage());
                    totalCount.setText("0.0");
                }

            }
            //tvOrderWeight.setText(mWeightData);
            mParmTag = 0;
            mWeightData = "";
        }
    }

    public void doBackWeight(View view) {
        if (prodAdapter == null) {
            T.showShort(mContext, "请先选择品类");
            return;
        }
        OrderProd item = prodAdapter.getItem(prodAdapter.getSelectedItem());
        if (item.getWeightList() != null && item.getWeightList().size() > 0) {
            List<Double> newList = item.getWeightList().subList(0, item.getWeightList().size() - 1);
            //item.getWeightList().clear();
            item.setWeightList(newList);
            //item.getWeightList().addAll(newList);
        }

        String wListStr = "";
        if (item.getWeightList() != null && item.getWeightList().size() > 0) {
            for (int i = 0; i < item.getWeightList().size(); i++) {
                if (!TextUtils.isEmpty(wListStr)) {
                    wListStr = wListStr + "+";
                }
                wListStr = wListStr + item.getWeightList().get(i);
            }
        }

        tvWeightList.setText(wListStr);
        prod_total_price.setText(doubleToString(Double.parseDouble(item.getWeight()) * Double.parseDouble(uni_price.getText().toString())));
    }

    public void doSaveWeight(View view) {
        if (TextUtils.isEmpty(totalCount.getText().toString())) {
            T.showShort(mContext, "请先称重");
            return;
        }
        if (TextUtils.isEmpty(uni_price.getText().toString())) {
            T.showShort(mContext, "请先设置单价");
            return;
        }
        OrderProd item = prodAdapter.getItem(prodAdapter.getSelectedItem());
        item.addWeight(Double.parseDouble(totalCount.getText().toString()));

        String wListStr = "";
        if (item.getWeightList() != null && item.getWeightList().size() > 0) {
            for (int i = 0; i < item.getWeightList().size(); i++) {
                if (!TextUtils.isEmpty(wListStr)) {
                    wListStr = wListStr + "+";
                }
                wListStr = wListStr + item.getWeightList().get(i);
            }
        }

        tvWeightList.setText(wListStr);
        prod_total_price.setText(doubleToString(Double.parseDouble(item.getWeight()) * Double.parseDouble(uni_price.getText().toString())));
        //calculateProdPrice();
    }

    public void doSaveOrder(View view) {
        if (TextUtils.isEmpty(uni_price.getText().toString())) {
            T.showShort(mContext, "请先设置单价");
            return;
        }

        OrderProd item = prodAdapter.getItem(prodAdapter.getSelectedItem());
        item.setProduct_price(uni_price.getText().toString());
        item.setTotal(prod_total_price.getText().toString());

        ReceiveParam.MkOrderProd prod = receiveParam.getProduct().get(prodAdapter.getSelectedItem());
        if (!TextUtils.isEmpty(uni_price.getText())) {
            prod.setPrice(Double.parseDouble(uni_price.getText().toString()));
        } else {
            prod.setPrice(0);
        }

        if (!TextUtils.isEmpty(item.getWeight())) {
            prod.setCount(Double.parseDouble(item.getWeight()));
        } else {
            prod.setCount(0);
        }

        mProducts.add(item);
        mProductAdapter.notifyDataSetChanged();

        double totalNum = 0;
        for (int i = 0; i < mProducts.size(); i++) {
            totalNum = totalNum + Double.parseDouble(mProducts.get(i).getTotal());
        }
        total_price.setText(doubleToString(totalNum));
    }


    public void doCheckOrder(View view) {
        //printer();
        DialogUtil.showComfirmDialog(this, "确认已取到货品？", new DialogUtil.OnComfirmClickListener() {
            @Override
            public void onComfirmClick() {
                final JSONArray jsonArray = new JSONArray();

                if (mProducts == null || mProducts.size() <= 0) {
                    T.showShort(getActivity(), "请先选择货品");
                    return;
                }

                List<ReceiveParam.MkOrderProd> product = new ArrayList<>();
                for (OrderProd prod : mProducts) {
                    if (orderId == 0) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", prod.getProduct_id());
                        jsonObject.put("price", prod.getProduct_price());
                        jsonObject.put("count", prod.getWeight());
                        jsonArray.add(jsonObject);
                    } else {
                        product.add(new ReceiveParam.MkOrderProd(prod.getProduct_id(),
                                prod.getProduct_name(), Double.parseDouble(prod.getProduct_price()),
                                Double.parseDouble(prod.getWeight())));
                    }
                }

                receiveParam.getProduct().clear();
                receiveParam.getProduct().addAll(product);

                final RequestParams requestParams = new RequestParams();
                requestParams.setAsJsonContent(true);
                if (orderId != 0) {
                    receiveParam.setUser_id(Config.userId);
                    requestParams.setBodyContent(JSON.toJSONString(receiveParam));
                    Log.e("requestParams", JSON.toJSONString(receiveParam));

                    mRequestUtil.doPost("s1/orderCheck", requestParams, OrderCheckEntity.class, new RequestUtil.OnRequestFinishListener<OrderCheckEntity>() {

                        @Override
                        public void onRequestSuccess(OrderCheckEntity result) {

                            if (orderId != 0) {
                                uploadAudio(String.valueOf(orderId));
                            } else {
                                uploadAudio(result.order_id);
                            }

                            ToastUtil.showToast(getActivity(), orderId == 0 ? "创建订单成功！" : "确定取货成功！");
                            setResult(RESULT_OK);
                            printer();
                            finish();
                        }

                        @Override
                        public void onRequestFail(int errorCode, String desc) {

                        }
                    });
                } else {
                    if (customUserId == 0) {
                        ToastUtil.showToast(getContext(), "请扫描用户二维码绑定用户");
                        return;
                    }
                    final JSONObject jsonObject = new JSONObject();
                    BDLocationUtils bdLocationUtils = new BDLocationUtils(getActivity());
                    bdLocationUtils.setOnLocationGetListener(new BDLocationUtils.OnLocationGetListener() {
                        @Override
                        public void onLocationGet(BDLocation bdLocation, double lat, double lon) {

                            final RequestParams locationParams = new RequestParams("http://apis.map.qq.com/ws/geocoder/v1/");
                            locationParams.addParameter("location", lat + "," + lon);
                            locationParams.addParameter("key", "3DHBZ-32DCW-DADRQ-RMHAP-VXUAV-AZFSD");
                            locationParams.addParameter("et_poi", 1);
                            x.http().get(locationParams, new Callback.CommonCallback<String>() {

                                @Override
                                public void onSuccess(String result) {
                                    PositionDetail positionDetail = FastJsonTools.get(result, PositionDetail.class);
                                    if (positionDetail != null && positionDetail.getStatus() == 0) {
                                        jsonObject.put("city", positionDetail.getResult().getAd_info().getAdcode());
                                        jsonObject.put("province", "100000");
                                        String townNumber = positionDetail.getResult().getAddress_reference().getTown().getId();
                                        jsonObject.put("district", townNumber.length() == 6 ? townNumber : positionDetail.getResult().getAd_info().getAdcode());
                                        jsonObject.put("order_user", customUserId);
                                        jsonObject.put("items", jsonArray);
                                        jsonObject.put("contact", user_name.getText().toString());
                                        jsonObject.put("phone", "123456");
                                        jsonObject.put("address", positionDetail.getResult().getAddress_component().getStreet_number());
                                        requestParams.setBodyContent(jsonObject.toJSONString());

                                        mRequestUtil.doPost("s1/orderMake", requestParams, Object.class, new RequestUtil.OnRequestFinishListener<Object>() {

                                            @Override
                                            public void onRequestSuccess(Object result) {
                                                ToastUtil.showToast(getActivity(), orderId == 0 ? "创建订单成功！" : "确定取货成功！");
                                                setResult(RESULT_OK);
                                                printer();
                                                finish();
                                            }

                                            @Override
                                            public void onRequestFail(int errorCode, String desc) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    ex.printStackTrace();
                                    Log.i("getLocationResult", "error:" + ex.getLocalizedMessage());
                                }

                                @Override
                                public void onCancelled(CancelledException cex) {

                                }

                                @Override
                                public void onFinished() {

                                }
                            });
                        }
                    });
                    bdLocationUtils.doLocation();
                }

            }
        });
    }

    private class ProdTagAdapter extends TagAdapter<OrderProd> {

        private int selectedItem = 0;

        public ProdTagAdapter(List<OrderProd> datas) {
            super(datas);
        }

        public int getSelectedItem() {
            return selectedItem;
        }

        @Override
        public View getView(FlowLayout parent, final int position, final OrderProd orderProd) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_tag, parent, false);
            TextView tagText = itemView.findViewById(R.id.tag_text);
            tagText.setBackgroundResource(selectedItem == position ? R.drawable.tag_selected : R.drawable.tag_normal);
            tagText.setText(orderProd.getProduct_name() + "(" + orderProd.getMin_price() + "-" + orderProd.getMax_price() + ")");
            tagText.setTextColor(selectedItem == position ? getResources().getColor(R.color.colorPrimary) : getResources().getColor(R.color.textGrayColor));
            return itemView;
        }

        public void setSelectedItem(int selectedItem) {
            this.selectedItem = selectedItem;
            notifyDataChanged();
        }

        @Override
        public void onSelected(final int position, View view) {
            super.onSelected(position, view);
            setSelectedItem(position);
            final OrderProd oriProd = getItem(position);
            checkPrice();

            if (oriProd.getProduct_name() != null && oriProd.getProduct_name().contains("瓶")) {
                totalCount.setEnabled(true);
                num_unit.setText("(个)");
            } else {
                totalCount.setEnabled(false);
                num_unit.setText("(kg)");
            }
            ReceiveParam.MkOrderProd prod = receiveParam.getProduct().get(position);
            uni_price.setText(prod.getPrice() == 0 ? "" : doubleToString(prod.getPrice()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && resultCode != ImagePicker.RESULT_CODE_ITEMS) {
            return;
        }
        if (requestCode == REQ_SCANE_CODE) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    queryUserNameById(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
            return;
        } else if (requestCode == REQ_ADD_PROD) {
            List<OrderProd> prods = FastJsonTools.getList(data.getStringExtra("datas"), OrderProd.class);
            loadProdDatas(prods);
            tvWeightList.setText("");
            uni_price.setText("");
            prod_total_price.setText("");
        }
        uploadImageAdapter.onActivityResult(requestCode, resultCode, data);
    }

    private void queryUserNameByCard(String card) {
        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("ic_no", card);
        requestUtil.doPost("s1/userInfoNew", requestParams, CardNetBean.class, new RequestUtil.OnRequestFinishListener<CardNetBean>() {
            @Override
            public void onRequestSuccess(CardNetBean result) {
                user_name.setText(result.getName());
                customUserId = result.getId();
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {
                ToastUtil.showToast(getActivity(), desc);
            }
        });
    }

    private void queryUserNameById(final String result) {
        final QRParseBean qrParseBean = FastJsonTools.get(result, QRParseBean.class);
        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("order_user", qrParseBean.getId());
        requestUtil.doPost("s1/userInfo", requestParams, NameBean.class, new RequestUtil.OnRequestFinishListener<NameBean>() {
            @Override
            public void onRequestSuccess(NameBean result) {
                user_name.setText(result.getName());
                customUserId = qrParseBean.getId();
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {
                ToastUtil.showToast(getActivity(), desc);
            }
        });
    }

    /**
     * 初始化蓝牙管理，设置监听
     */
    public void initBlueManager() {
        onSearchDeviceListener = new OnSearchDeviceListener() {
            @Override
            public void onStartDiscovery() {
                e("onStartDiscovery()");
            }

            @Override
            public void onNewDeviceFound(BluetoothDevice device) {
                e("new device: " + device.getName() + " " + device.getAddress());
            }

            @Override
            public void onSearchCompleted(List<SearchResult> bondedList, List<SearchResult> newList) {
                e("bondedList " + bondedList.toString());
                e("newList " + newList.toString());
                mDevices.clear();
                mDevices.addAll(bondedList);
                mDevices.addAll(newList);
                e("搜索完成");
            }

            @Override
            public void onError(Exception e) {
                e("搜索失败");
            }
        };
        onConnectListener = new OnConnectListener() {
            @Override
            public void onConnectStart() {
                //开始连接
                e("onConnectStart");
                sendMessage(0, "开始连接");
            }

            @Override
            public void onConnectting() {
                //正在连接..;
                e("onConnectting");
                sendMessage(0, "正在连接");
            }

            @Override
            public void onConnectFailed() {
                //连接失败
                e("onConnectFailed");
                sendMessage(0, "连接失败");
            }

            @Override
            public void onConectSuccess(String mac) {
                //连接成功 MAC
                e("onConectSuccess");
                sendMessage(0, "连接成功");
                bluemanage.setReadVersion(false);
                stringBuilder.delete(0, stringBuilder.length());
                MessageBean item = new MessageBean(TypeConversion.startDetect());
                bluemanage.sendMessage(item, true);
            }

            @Override
            public void onError(Exception e) {
                //连接异常！;
                e("onError" + e.getMessage());
                sendMessage(0, "连接异常");
            }
        };
        onSendMessageListener = new OnSendMessageListener() {
            @Override
            public void onSuccess(int status, String response) {
                //发送成功！
                e("send message is success ! ");
            }

            @Override
            public void onConnectionLost(Exception e) {
                //连接断开！
                e("send message is onConnectionLost ! ");
            }

            @Override
            public void onError(Exception e) {
                //发送失败！
                e("send message is onError ! ");
            }
        };

        onReceiveMessageListener = new OnReceiveMessageListener() {
            @Override
            public void onProgressUpdate(String what, int progress) {

            }

            @Override
            public void onDetectDataUpdate(String what) {
                sendMessage(3, what);
            }

            @Override
            public void onDetectDataFinish() {
                e("receive message is onDetectDataFinish");
            }

            @Override
            public void onNewLine(String s) {
                sendMessage(3, s);
            }

            @Override
            public void onConnectionLost(Exception e) {
                e("receive message is onConnectionLost ! ");
            }

            @Override
            public void onError(Exception e) {
                e("receive message is onError ! ");
            }
        };
        bluemanage = BlueManager.getInstance(getApplicationContext());
        bluemanage.setOnSearchDeviceListener(onSearchDeviceListener);
        bluemanage.setOnConnectListener(onConnectListener);
        bluemanage.setOnSendMessageListener(onSendMessageListener);
        bluemanage.setOnReceiveMessageListener(onReceiveMessageListener);
        bluemanage.requestEnableBt();
        bluemanage.setReadVersion(false);
        //bluemanage.searchDevices();
        bluemanage.connectDevice(mBlueMac);
    }

    /**
     * @param type    0 修改状态  1 更新进度  2 体检完成  3 体检数据进度
     * @param context
     */
    public void sendMessage(int type, String context) {
        if (handler != null) {
            Message message = handler.obtainMessage();
            message.what = type;
            message.obj = context;
            handler.sendMessage(message);
        }
    }

    private void printer() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        e("stop service");
        if (bluemanage != null) {
            bluemanage.close();
            bluemanage = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        EventBus.getDefault().unregister(this);

        if (bluemanage != null) {
            bluemanage.stopScan();
            bluemanage.closeDevice();
            bluemanage.close();
        }

        stopRecord();
    }


    /**
     * 开始录音 使用amr格式
     * 录音文件
     *
     * @return
     */
    public void startRecord() {
        // 开始录音
        e("startRecord");
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            fileName = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".m4a";
            //注意文件夹要创建之后才能使用
            filePath = FileUtil.getSDPath(getContext()) + MyApp.UrlAudio + fileName;
            /* ③准备 */
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e("call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            e("call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        if (mMediaRecorder != null) {
            //在5.0以上在调用stop的时候会报错，翻阅了一下谷歌文档发现上面确实写的有可能会报错的情况，捕获异常清理一下就行了，感谢大家反馈！
            try {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
                filePath = "";
            } catch (RuntimeException e) {
                Log.e("mediaR", e.toString());
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
                File file = new File(filePath);
                if (file.exists())
                    file.delete();
                filePath = "";
            }
        }
    }

    private void e(String msg) {
        Log.e("CreateOrderActivity", msg);
    }
}
