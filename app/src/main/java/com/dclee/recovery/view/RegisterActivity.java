package com.dclee.recovery.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.bean.event.UploadPic;
import com.dclee.recovery.pojo.MapBean;
import com.dclee.recovery.pojo.RegisterBean;
import com.dclee.recovery.util.FileUtil;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.view.purchase.OnSelectDateListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.xutils.http.RequestParams;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private RadioButton rbRec;
    private RadioButton rbRec2;
    private EditText etName;
    private Spinner spSex;
    private EditText etUser;
    private LinearLayout llIdcard;
    private EditText etIdcard;
    private LinearLayout llCarnum;
    private EditText etCarnum;
    private TextView tvBrith;
    private Spinner spSheng;
    private Spinner spShi;
    private Spinner spQu;
    private EditText etAddress;
    private ImageView ivIdcardPositive;
    private ImageView ivIdcardNegative;
    private ImageView ivCarphotoPositive;
    private ImageView ivCarphotoNegative;
    private ImageView ivJszPositive;
    private ImageView ivJszNegative;
    private ImageView ivXszPositive;
    private ImageView ivXszNegative;
    private Button btnRegister;
    private LinearLayout llJsz;
    private LinearLayout llXsz;

    private RequestUtil requestUtil;

    private String mPassword;
    private String mCode;

    private static final int REQUESTCODE_CHOOSE_P = 1000;
    private static final int REQUESTCODE_CHOOSE_N = 1001;
    private static final int REQUESTCODE_CHOOSE_CARP = 1002;
    private static final int REQUESTCODE_CHOOSE_CARN = 1003;
    private static final int REQUESTCODE_CHOOSE_JSZP = 1004;
    private static final int REQUESTCODE_CHOOSE_JSZN = 1005;
    private static final int REQUESTCODE_CHOOSE_XSZP = 1006;
    private static final int REQUESTCODE_CHOOSE_XSZN = 1007;

    private String mPositiveCard;
    private String mNegativeCard;
    private String mPositiveCar;
    private String mNegativeCar;
    private String mPositiveJsz;
    private String mNegativeJsz;
    private String mPositiveXsz;
    private String mNegativeXsz;

    private List<MapBean> mShengDataList = new ArrayList<>();
    private List<MapBean> mShiDataList = new ArrayList<>();
    private List<MapBean> mQuDataList = new ArrayList<>();
    private List<String> mShengList = new ArrayList<>();
    private List<String> mShiList = new ArrayList<>();
    private List<String> mQuList = new ArrayList<>();
    private static final int SHENG = 1003;
    private static final int SHI = 1004;
    private static final int QU = 1005;
    private int mSqType = SHENG;
    private String mShengId = "";
    private String mShiId = "";
    private String mQuId = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        radioGroup = findViewById(R.id.radioGroup);
        rbRec = findViewById(R.id.rb_rec);
        rbRec2 = findViewById(R.id.rb_rec2);
        etName = findViewById(R.id.et_name);
        spSex = findViewById(R.id.sp_sex);
        etUser = findViewById(R.id.et_user);
        llIdcard = findViewById(R.id.ll_idcard);
        etIdcard = findViewById(R.id.et_idcard);
        llCarnum = findViewById(R.id.ll_carnum);
        etCarnum = findViewById(R.id.et_carnum);
        tvBrith = findViewById(R.id.tv_brith);
        spSheng = findViewById(R.id.sp_sheng);
        spShi = findViewById(R.id.sp_shi);
        spQu = findViewById(R.id.sp_qu);
        etAddress = findViewById(R.id.et_address);
        ivIdcardPositive = findViewById(R.id.iv_idcard_positive);
        ivIdcardNegative = findViewById(R.id.iv_idcard_negative);
        ivCarphotoPositive = findViewById(R.id.iv_carphoto_positive);
        ivCarphotoNegative = findViewById(R.id.iv_carphoto_negative);
        ivJszPositive = findViewById(R.id.iv_jsz_positive);
        ivJszNegative = findViewById(R.id.iv_jsz_negative);
        ivXszPositive = findViewById(R.id.iv_xsz_positive);
        ivXszNegative = findViewById(R.id.iv_xsz_negative);
        btnRegister = findViewById(R.id.btn_register);
        llJsz = findViewById(R.id.ll_jsz);
        llXsz = findViewById(R.id.ll_xsz);

        spSheng.setSelection(0, true);
        spShi.setSelection(0, true);
        spQu.setSelection(0, true);

        requestUtil = new RequestUtil(this);
        initLis();
    }

    private void initLis() {
        btnRegister.setOnClickListener(this);
        ivIdcardPositive.setOnClickListener(this);
        ivIdcardNegative.setOnClickListener(this);
        tvBrith.setOnClickListener(this);

        ivCarphotoPositive.setOnClickListener(this);
        ivCarphotoNegative.setOnClickListener(this);
        ivJszPositive.setOnClickListener(this);
        ivJszNegative.setOnClickListener(this);
        ivXszPositive.setOnClickListener(this);
        ivXszNegative.setOnClickListener(this);

        spSheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSqType = SHI;
                mShengId = mShengDataList.get(position).getId();
                getAddress(mShengId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spShi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSqType = QU;
                mShiId = mShiDataList.get(position).getId();
                getAddress(mShiId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spQu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mQuId = mQuDataList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbRec.getId()) {
                    llCarnum.setVisibility(View.VISIBLE);
                    llJsz.setVisibility(View.VISIBLE);
                    llXsz.setVisibility(View.VISIBLE);
                }

                if (checkedId == rbRec2.getId()) {
                    llCarnum.setVisibility(View.GONE);
                    llJsz.setVisibility(View.GONE);
                    llXsz.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initData() {
        getAddress("");
        Bundle bundle = getIntent().getBundleExtra("mydata");

        if (bundle==null){
            T.showShort(mActivity,"参数错误");
            finish();
            return;
        }

        etUser.setText(bundle.getString("account", ""));
        mPassword = bundle.getString("password", "");
        mCode = bundle.getString("code", "");
    }

    private void getAddress(String parent_id) {
        RequestParams requestParams = new RequestParams();
        if (!TextUtils.isEmpty(parent_id))
            requestParams.addParameter("parent_id", parent_id);
        requestUtil.postList("s1/maps", requestParams, MapBean.class, new RequestUtil.OnRequestFinishListener<List<MapBean>>() {
            @Override
            public void onRequestSuccess(List<MapBean> result) {
                if (mSqType == SHENG) {
                    mShengDataList.clear();
                    mShiDataList.clear();
                    mQuDataList.clear();
                    mShengDataList.addAll(result);
                    mShengList.clear();
                    mShiList.clear();
                    mQuList.clear();
                    for (MapBean mapBean : mShengDataList) {
                        mShengList.add(mapBean.getName());
                    }
                } else if (mSqType == SHI) {
                    mShiDataList.clear();
                    mQuDataList.clear();
                    mShiDataList.addAll(result);
                    mShiList.clear();
                    mQuList.clear();
                    for (MapBean mapBean : mShiDataList) {
                        mShiList.add(mapBean.getName());
                    }
                } else {
                    mQuDataList.clear();
                    mQuDataList.addAll(result);
                    mQuList.clear();
                    for (MapBean mapBean : mQuDataList) {
                        mQuList.add(mapBean.getName());
                    }
                }
                initSqView();
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {

            }
        });
    }

    private void initSqView() {
        switch (mSqType) {
            case SHENG:
                ArrayAdapter a1 = new ArrayAdapter<>(mActivity, android.R.layout.simple_dropdown_item_1line, mShengList);
                spSheng.setAdapter(a1);
                break;

            case SHI:
                ArrayAdapter a2 = new ArrayAdapter<>(mActivity, android.R.layout.simple_dropdown_item_1line, mShiList);
                spShi.setAdapter(a2);
                break;

            case QU:
                ArrayAdapter a3 = new ArrayAdapter<>(mActivity, android.R.layout.simple_dropdown_item_1line, mQuList);
                spQu.setAdapter(a3);
                break;
        }
    }

    private void doRegister() {
        String phone = etUser.getText().toString();
        String name = etName.getText().toString();
        String brith = tvBrith.getText().toString();

        String carnum = etCarnum.getText().toString();
        String idcard = etIdcard.getText().toString();
        String address = etAddress.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(name) || TextUtils.isEmpty(brith)
                || TextUtils.isEmpty(idcard) || TextUtils.isEmpty(address)) {
            T.showShort(mActivity, "请把信息输入完整");
            return;
        }

        if (phone.length() != 11) {
            T.showShort(mActivity, "请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(mShengId) || TextUtils.isEmpty(mShiId) || TextUtils.isEmpty(mQuId)) {
            T.showShort(mActivity, "请选择地址");
            return;
        }

        if (TextUtils.isEmpty(mNegativeCard) || TextUtils.isEmpty(mPositiveCard)) {
            T.showShort(mActivity, "请上传身份证");
            return;
        }

        if (TextUtils.isEmpty(mPositiveCar) || TextUtils.isEmpty(mNegativeCar)) {
            T.showShort(mActivity, "请上传车身照片");
            return;
        }

        RequestParams requestParams = new RequestParams();

        if (rbRec.isChecked()) {
            if (TextUtils.isEmpty(carnum)) {
                T.showShort(mActivity, "请把信息输入完整");
                return;
            }
            if (TextUtils.isEmpty(mPositiveJsz) || TextUtils.isEmpty(mNegativeJsz)) {
                T.showShort(mActivity, "请上传驾驶证照片");
                return;
            }
            if (TextUtils.isEmpty(mPositiveXsz) || TextUtils.isEmpty(mNegativeXsz)) {
                T.showShort(mActivity, "请上传行驶证照片");
                return;
            }
            requestParams.addParameter("carNo", carnum);
            requestParams.addParameter("role", 10);
            requestParams.addParameter("driver_img_1", mPositiveJsz);
            requestParams.addParameter("driver_img_2", mNegativeJsz);
            requestParams.addParameter("driving_img_1", mPositiveXsz);
            requestParams.addParameter("driving_img_2", mNegativeXsz);
        } else {
            requestParams.addParameter("role", 5);
        }

        requestParams.addParameter("gender", spSex.getSelectedItemPosition() + 1);
        requestParams.addParameter("randomNum", mCode);
        requestParams.addParameter("password", mPassword);

        requestParams.addParameter("id_card_img_1", mPositiveCard);
        requestParams.addParameter("id_card_img_2", mNegativeCard);
        requestParams.addParameter("car_img_1", mPositiveCar);
        requestParams.addParameter("car_img_2", mNegativeCar);

//        File[] files = {new File(mNegativeCard), new File(mPositiveCard)};
//        requestParams.setMultipart(true);
        //requestParams.addBodyParameter("images",getFileMap(cards));
//        requestParams.addBodyParameter("images[0]", new File(mNegativeCard), "multipart/form-data");
//        requestParams.addBodyParameter("images[1]", new File(mPositiveCard), "multipart/form-data");
//        requestParams.addParameter("images", files);

        requestParams.addParameter("username", name);
        requestParams.addParameter("phone", phone);
        requestParams.addParameter("address", address);
        requestParams.addParameter("idCardNo", idcard);
        requestParams.addParameter("birthday", brith);
        requestParams.addParameter("province", mShengId);
        requestParams.addParameter("city", mShiId);
        requestParams.addParameter("district", mQuId);
        requestUtil.doPostWithoutToken("s1/register", requestParams, RegisterBean.class, new RequestUtil.OnRequestFinishListener<RegisterBean>() {
            @Override
            public void onRequestSuccess(RegisterBean result) {
                T.showShort(mActivity, "注册成功");
                finish();
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {
                T.showShort(mActivity, desc);
            }
        });
    }

    private static Map<String, File> getFileMap(List<String> fileNames) {
        Map<String, File> fileMap = new HashMap<>();
        for (String fileName : fileNames) {
            File file = new File(fileName);
            fileMap.put(file.getName(), file);
        }

        return fileMap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                doRegister();
                break;

            case R.id.iv_idcard_positive:
                try {
                    choosePic(REQUESTCODE_CHOOSE_P);
                } catch (Exception e) {
                }

                break;

            case R.id.iv_idcard_negative:
                try {
                    choosePic(REQUESTCODE_CHOOSE_N);
                } catch (Exception e) {
                }

                break;

            case R.id.iv_carphoto_positive:
                try {
                    choosePic(REQUESTCODE_CHOOSE_CARP);
                } catch (Exception e) {
                }

                break;

            case R.id.iv_carphoto_negative:
                try {
                    choosePic(REQUESTCODE_CHOOSE_CARN);
                } catch (Exception e) {
                }

                break;

            case R.id.iv_jsz_positive:
                try {
                    choosePic(REQUESTCODE_CHOOSE_JSZP);
                } catch (Exception e) {
                }

                break;

            case R.id.iv_jsz_negative:
                try {
                    choosePic(REQUESTCODE_CHOOSE_JSZN);
                } catch (Exception e) {
                }

                break;

            case R.id.iv_xsz_positive:
                try {
                    choosePic(REQUESTCODE_CHOOSE_XSZP);
                } catch (Exception e) {
                }

                break;

            case R.id.iv_xsz_negative:
                try {
                    choosePic(REQUESTCODE_CHOOSE_XSZN);
                } catch (Exception e) {
                }

                break;

            case R.id.tv_brith:
                toSelectDate(new OnSelectDateListener() {
                    @Override
                    public void onDateSelected(Date date, String dateStr) {
                        tvBrith.setText(dateStr);
                    }
                });
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void choosePic(final int type) {
        new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
//                        Matisse
//                                .from(mActivity)
//                                .choose(MimeType.ofImage())
//                                .showSingleMediaType(true)
//                                .capture(true)
//                                .captureStrategy(new CaptureStrategy(true, "PhotoPicker"))
//                                .countable(true)
//                                .maxSelectable(1)
//                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//                                .thumbnailScale(0.8f)
//                                .theme(R.style.Matisse_Dracula)
//                                .imageEngine(new GlideEngine())
//                                .forResult(type);


                        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
                        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intentToPickPic, type);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != RESULT_OK)
            return;
        Uri uri = data.getData();
        uploadPic(uri, requestCode);
    }

    private void uploadPic(final Uri uri, final int requestCode) {
        String fileName = FileUtil.getFilePathByUri(this, uri);
        RequestParams requestParams = new RequestParams();
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("images", new File(fileName));
        final LoadingDialog loadingDialog = new LoadingDialog(mActivity)
                .setLoadingText("图片上传中...");
        loadingDialog.show();

        requestUtil.doNewPost("v1/appUploads", requestParams, new RequestUtil.OnRequestFinishListener2() {
            @Override
            public void onRequestSuccess(String result) {
                loadingDialog.close();
                if (requestCode == REQUESTCODE_CHOOSE_P) {
                    mPositiveCard = result;
                    Glide.with(mActivity).load(uri).into(ivIdcardPositive);
                } else if (requestCode == REQUESTCODE_CHOOSE_N) {
                    mNegativeCard = result;
                    Glide.with(mActivity).load(uri).into(ivIdcardNegative);
                } else if (requestCode == REQUESTCODE_CHOOSE_CARP) {
                    mPositiveCar = result;
                    Glide.with(mActivity).load(uri).into(ivCarphotoPositive);
                } else if (requestCode == REQUESTCODE_CHOOSE_CARN) {
                    mNegativeCar = result;
                    Glide.with(mActivity).load(uri).into(ivCarphotoNegative);
                } else if (requestCode == REQUESTCODE_CHOOSE_JSZP) {
                    mPositiveJsz = result;
                    Glide.with(mActivity).load(uri).into(ivJszPositive);
                } else if (requestCode == REQUESTCODE_CHOOSE_JSZN) {
                    mNegativeJsz = result;
                    Glide.with(mActivity).load(uri).into(ivJszNegative);
                } else if (requestCode == REQUESTCODE_CHOOSE_XSZP) {
                    mPositiveXsz = result;
                    Glide.with(mActivity).load(uri).into(ivXszPositive);
                } else if (requestCode == REQUESTCODE_CHOOSE_XSZN) {
                    mNegativeXsz = result;
                    Glide.with(mActivity).load(uri).into(ivXszNegative);
                }
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {
                T.showShort(mActivity, "上传失败");
                loadingDialog.close();
            }
        });
    }

    private void toSelectDate(final OnSelectDateListener onSelectDateListener) {
        Calendar endDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startDate.set(1960, 1, 1);
        endDate.set(2025, 11, 28);
        TimePickerView datePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                onSelectDateListener.onDateSelected(date, simpleDateFormat.format(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(16)
                .setTitleSize(17)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.textGrayColor))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        datePicker.show(true);
    }
}
