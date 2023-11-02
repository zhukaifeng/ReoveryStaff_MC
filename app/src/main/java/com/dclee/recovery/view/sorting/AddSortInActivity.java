package com.dclee.recovery.view.sorting;

import static com.dclee.recovery.util.HexUtils.hexStringToString;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.calypso.bluelib.bean.MessageBean;
import com.calypso.bluelib.bean.SearchResult;
import com.calypso.bluelib.listener.OnConnectListener;
import com.calypso.bluelib.listener.OnReceiveMessageListener;
import com.calypso.bluelib.listener.OnSearchDeviceListener;
import com.calypso.bluelib.listener.OnSendMessageListener;
import com.calypso.bluelib.manage.BlueManager;
import com.calypso.bluelib.utils.TypeConversion;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.BaseAdapter;
import com.dclee.recovery.base.DbHelper;
import com.dclee.recovery.bean.db.SortInBean;
import com.dclee.recovery.pojo.OrderCreateBean;
import com.dclee.recovery.pojo.PictureBBean;
import com.dclee.recovery.pojo.SortDefaultBean;
import com.dclee.recovery.pojo.SortInListBean;
import com.dclee.recovery.pojo.SortReqDetailBean;
import com.dclee.recovery.util.FastJsonTools;
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.view.purchase.SortRequestBean;
import com.lzy.imagepicker.ImagePicker;
import com.sunmi.utils.DoubleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


public class AddSortInActivity extends BaseActivity {

    private TextView tv_back;
    private TextView tv_add_continue;
    private TextView tv_add;
    private TextView tv_netweight;
    private EditText edt_weight;
    private TextView tv_type;
    private TextView tv_select_person;
    private TextView tv_weight;
    private EditText edt_buckle;
    private RequestUtil mRequestUtil;
    private String receiveId;
//    private ArrayAdapter<String> adapterForSpinnerPerson;
//    private List<String> listForPersonSpinner = new ArrayList<>();
    private SortDefaultBean mSortDefaultBean;
    private PopupWindow pop;
    private PopupWindow pop_person;
    private int mCurrentSelectIndex = 0;
    private SortDefaultBean.DataDTO.SysProductTypeParentChooseListDTO.ChildrenDTO mSelectType;
    private RecyclerView selectedImgRecycler;
    private TypeImageAdapter typeImageAdapter;
    private List<String> mPics = new ArrayList<>();
    private int index = 0;
    private SortDefaultBean.DataDTO.SysUserListDTO mOperateBean;
    private DbHelper dbHelper = new DbHelper();
    private Context mContext;
    private String mBlueMac;
    private BlueManager bluemanage;

    private List<SearchResult> mDevices;
    private OnConnectListener onConnectListener;
    private OnSendMessageListener onSendMessageListener;
    private OnSearchDeviceListener onSearchDeviceListener;
    private OnReceiveMessageListener onReceiveMessageListener;

    public AddSortInActivity() throws DbException {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_sortin;
    }

    @Override
    public void initView() {
        tv_back = findViewById(R.id.tv_back);
        tv_add_continue = findViewById(R.id.tv_add_continue);
        tv_add = findViewById(R.id.tv_add);
        tv_netweight = findViewById(R.id.tv_netweight);
        edt_weight = findViewById(R.id.edt_weight);
        tv_type = findViewById(R.id.tv_type);
        edt_buckle = findViewById(R.id.edt_buckle);
        tv_select_person = findViewById(R.id.tv_select_person);
        mContext = this;
        stringBuilder = new StringBuilder();
        mDevices = new ArrayList<>();

        selectedImgRecycler = findViewById(R.id.selected_img_recycler);

        selectedImgRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        typeImageAdapter = new TypeImageAdapter(this, mRequestUtil);
        // typeImageAdapter.setUploadedImages(receiveParam.getImages());
        selectedImgRecycler.setAdapter(typeImageAdapter);
        edt_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(edt_weight.getText().toString()) &&
                        !TextUtils.isEmpty(edt_buckle.getText().toString())) {
                    double weight = Double.parseDouble(edt_weight.getText().toString());
                    double buckle = Double.parseDouble(edt_buckle.getText().toString());
//                    if (weight<buckle){
//                        Toast.
//                    }
                    double netweight = DoubleUtils.sub(weight, buckle);
                    tv_netweight.setText(String.valueOf(netweight));
                }
            }
        });
        edt_buckle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(edt_weight.getText().toString())
                &&!TextUtils.isEmpty(edt_buckle.getText().toString())){
                    double weight = Double.parseDouble(edt_weight.getText().toString());
                    double buckle = Double.parseDouble(edt_buckle.getText().toString());
//                    if (weight<buckle){
//                        Toast.
//                    }
                    double netweight = DoubleUtils.sub(weight, buckle);
                    tv_netweight.setText(String.valueOf(netweight));
                }

            }
        });
        tv_select_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPerson();
            }
        });
        tv_weight = findViewById(R.id.tv_weight);
        mRequestUtil = new RequestUtil(this);
        receiveId = getIntent().getStringExtra("id");

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();


            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOperateBean == null) {
                    Toast.makeText(AddSortInActivity.this, "请选择员工", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mSelectType == null) {
                    Toast.makeText(AddSortInActivity.this, "请选择品类", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_weight.getText().toString())) {
                    Toast.makeText(AddSortInActivity.this, "请输入重量", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_buckle.getText().toString())) {
                    Toast.makeText(AddSortInActivity.this, "请输入扣重", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitData();
            }
        });
    }

    private void submitData() {

        if (typeImageAdapter.getUploadedImages().size() > 0) {
            uploadFile();
        } else {
            createOrder();
        }

        tv_add_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_weight.setText("");
                mOperateBean = null;
                tv_select_person.setText("点击选择");
                tv_type.setText("点击选择");
                mPics.clear();
                index = 0;
                mSelectType = null;
                mCurrentSelectIndex =0;
                tv_type.setText("");
                edt_weight.setText("");
                edt_buckle.setText("");
                tv_netweight.setText("");
                List<String> tmp = new ArrayList<>();
                typeImageAdapter.setUploadedImages(tmp);
            }
        });

    }

    @SuppressLint("CheckResult")
    private void createOrder() {
        SortInBean sortInBean = new SortInBean();
        sortInBean.setSorter(mOperateBean.getUserId());
        sortInBean.setDeductWeight(edt_buckle.getText().toString());
        if (mPics.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String s : mPics) {
                stringBuffer.append(s).append(",");
            }
            String value = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
            sortInBean.setPicIdStr(value);
        }
        sortInBean.setReceiveId(receiveId);
        sortInBean.setProductId(mSelectType.getProductId());
        sortInBean.setWeight(edt_weight.getText().toString());
        sortInBean.setProductName(mSelectType.getProductTypeName());
        sortInBean.setSorterName(mOperateBean.getUserIdText());
        boolean success = dbHelper.addSortInBean(sortInBean);
        if (success){
            Toast.makeText(AddSortInActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile() {
        Log.d("zkf", "index:" + index);
        File file = new File(typeImageAdapter.getUploadedImages().get(index));
        RequestParams requestParams = new RequestParams();
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file", file);
        final LoadingDialog loadingDialog = new LoadingDialog(AddSortInActivity.this)
                .setLoadingText("图片上传中请稍后...");
        loadingDialog.show();
        mRequestUtil.postFile("app/common/uploadPic", requestParams, PictureBBean.class, new RequestUtil.OnRequestFinishListener<PictureBBean>() {
            @Override
            public void onRequestSuccess(PictureBBean result) {
                Log.d("zkf", "pic url:" + result.getUrl());
                Log.d("zkf", "post UploadPic");
                loadingDialog.close();
                mPics.add(result.getId());
                index++;
                if (index < typeImageAdapter.getUploadedImages().size()) {
                    uploadFile();
                } else {
                    Log.d("zkf", "index1:" + index);
                    Log.d("zkf", "post complete");
                    createOrder();

                }

                // EventBus.getDefault().post(new UploadPic(result));
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {
                loadingDialog.close();
            }
        });
    }
    private void showSelectPerson() {
        View contentView = LayoutInflater.from(AddSortInActivity.this).inflate(R.layout.popup_select_person, null);
        if (null != pop_person) {
            if (pop_person.isShowing()) {
                return;
            }
            pop_person.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        } else {
            pop_person = new PopupWindow(this);
            final RecyclerView rv_category = contentView.findViewById(R.id.rv_person);
            LinearLayout linear_content = contentView.findViewById(R.id.linear_content);
            PopupPersonAdapter personAdapter = new PopupPersonAdapter(this);
            rv_category.setLayoutManager(new GridLayoutManager(this,3));
            rv_category.setAdapter(personAdapter);
            if (mSortDefaultBean.getData().getSysProductTypeParentChooseList().size() > 0) {
                mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(0).setSelected(true);
            }
            personAdapter.setDatas(mSortDefaultBean.getData().getSysUserList());



            personAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mOperateBean = mSortDefaultBean.getData().getSysUserList().get(position);
                    tv_select_person.setText(mOperateBean.getUserIdText());
                    pop_person.dismiss();
                }
            });
            linear_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop_person.dismiss();
                }
            });
            pop_person.setOutsideTouchable(false);
            pop_person.setTouchable(true);
            pop_person.setContentView(contentView);
            DisplayMetrics dm = new DisplayMetrics();//屏幕度量
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screen_width = dm.widthPixels;//宽度
            int screen_height = dm.heightPixels ;//高度
            pop_person.setWidth(screen_width);
            pop_person.setHeight(screen_height);
            pop_person.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        }
    }

    private void showSelect() {
        View contentView = LayoutInflater.from(AddSortInActivity.this).inflate(R.layout.popup_select, null);
        if (null != pop) {
            if (pop.isShowing()) {
                return;
            }
            pop.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        } else {
            pop = new PopupWindow(this);
            final RecyclerView rv_category = contentView.findViewById(R.id.rv_category);
            final RecyclerView rv_type = contentView.findViewById(R.id.rv_type);
            LinearLayout linear_content = contentView.findViewById(R.id.linear_content);
            PopupCategoryAdapter categoryAdapter = new PopupCategoryAdapter(this);
            rv_category.setLayoutManager(new LinearLayoutManager(this));
            rv_category.setAdapter(categoryAdapter);
            if (mSortDefaultBean.getData().getSysProductTypeParentChooseList().size() > 0) {
                mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(0).setSelected(true);
            }
            categoryAdapter.setDatas(mSortDefaultBean.getData().getSysProductTypeParentChooseList());

            PopupTypeAdapter typeAdapter = new PopupTypeAdapter(this);
            rv_type.setLayoutManager(new LinearLayoutManager(this));
            rv_type.setAdapter(typeAdapter);
            if (mSortDefaultBean.getData().getSysProductTypeParentChooseList().size() > 0) {
                typeAdapter.setDatas(mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(0).getChildren());
                mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(0).getChildren().get(0).setSelected(true);

            }
            categoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    for (SortDefaultBean.DataDTO.SysProductTypeParentChooseListDTO bean : mSortDefaultBean.getData().getSysProductTypeParentChooseList()) {
                        bean.setSelected(false);
                    }
                    mCurrentSelectIndex = position;
                    mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(position).setSelected(true);
                    typeAdapter.setDatas(mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(position).getChildren());
                    categoryAdapter.notifyDataSetChanged();
                    typeAdapter.notifyDataSetChanged();
                }
            });
            typeAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    for (SortDefaultBean.DataDTO.SysProductTypeParentChooseListDTO.ChildrenDTO bean : mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(mCurrentSelectIndex).getChildren()) {
                        bean.setSelected(false);
                    }
                    mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(mCurrentSelectIndex).getChildren().get(position).setSelected(true);
                    mSelectType = mSortDefaultBean.getData().getSysProductTypeParentChooseList().get(mCurrentSelectIndex).getChildren().get(position);
                    typeAdapter.notifyDataSetChanged();
                    tv_type.setText(mSelectType.getProductTypeName());
                    pop.dismiss();
                }
            });
            linear_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop.dismiss();
                }
            });
            pop.setOutsideTouchable(false);
            pop.setTouchable(true);
            pop.setContentView(contentView);
            DisplayMetrics dm = new DisplayMetrics();//屏幕度量
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screen_width = dm.widthPixels;//宽度
            int screen_height = dm.heightPixels ;//高度
            pop.setWidth(screen_width);
            pop.setHeight(screen_height);
            pop.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        }
    }

    @Override
    public void initData() {
        getDataDetail();
        mBlueMac = PreferencesUtils.getString(getActivity(), "blue_mac", "");
        if (TextUtils.isEmpty(mBlueMac)) {
            T.showShort(mContext, "请先选择蓝牙设备");
            //startActionWithFinish(DeviceActivity.class);
        } else {
            initBlueManager();
        }
    }

    @SuppressLint("CheckResult")
    private void getDataDetail() {
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        RequestParams requestParams = new RequestParams();
                        requestParams.addParameter("receiveId", receiveId);
                        mRequestUtil.doPostWithToken2("mobile/orderReceive/defaultAddInfo", requestParams, SortInListBean.class, new RequestUtil.OnRequestFinishListener<String>() {


                            @Override
                            public void onRequestSuccess(String result) {
                                Log.d("zkf", "result:" + result);
                                mSortDefaultBean = FastJsonTools.get(result, SortDefaultBean.class);



                            }

                            @Override
                            public void onRequestFail(int errorCode, String desc) {

                            }
                        });
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && resultCode != ImagePicker.RESULT_CODE_ITEMS) {
            return;
        }


        typeImageAdapter.onActivityResult(requestCode, resultCode, data);
    }

    private StringBuilder stringBuilder;
    private int mParmTag;
    private String mWeightData;
    private double mWeightValue;

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
            Log.e("mWeightData：" , mWeightData);
            if (mWeightData.contains("kg")) {
                try {
                    mWeightData = mWeightData.substring(0, mWeightData.length() - 2);
                    mWeightValue = Double.valueOf(mWeightData);
                    tv_weight.setText(doubleToString(mWeightValue));
                    edt_weight.setText(doubleToString(mWeightValue));
                } catch (Exception e) {
                    Log.e("getWeightParm error " , e.getMessage());
                    tv_weight.setText("0.0");
                }

            }
            //tvOrderWeight.setText(mWeightData);
            mParmTag = 0;
            mWeightData = "";
        }
    }

    private String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
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

    private void e(String msg) {
        Log.e("CreateOrderActivity", msg);
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

        if (bluemanage != null) {
            bluemanage.stopScan();
            bluemanage.closeDevice();
            bluemanage.close();
        }

    }
}
