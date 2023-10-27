package com.dclee.recovery.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.util.ToastUtil;

import org.xutils.http.RequestParams;

public class CodeActivity extends BaseActivity {
    private EditText etAccount;
    private EditText etCode;
    private TextView tvCode;
    private EditText etPwd;
    private EditText etPwdAgain;

    private RequestUtil mRequestUtil;
    private MyCountDownTimer mDownTimer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_code;
    }

    @Override
    public void initView() {
        etAccount = findViewById(R.id.et_account);
        etCode = findViewById(R.id.et_code);
        tvCode = findViewById(R.id.tv_code);
        etPwd = findViewById(R.id.et_pwd);
        etPwdAgain = findViewById(R.id.et_pwd_again);
        mRequestUtil = new RequestUtil(this);
        mDownTimer = new MyCountDownTimer(60000, 1000);
        initListener();
    }

    private void initListener() {
        tvCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
                if (mDownTimer != null)
                    mDownTimer.start();
            }
        });
    }

    private void getCode() {
        if (TextUtils.isEmpty(etAccount.getText().toString())) {
            T.showShort(mActivity, "请输入手机号");
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.addParameter("mobile", etAccount.getText().toString());
        mRequestUtil.doPostWithoutToken("s1/sendVerificationCode", requestParams, Object.class, new RequestUtil.OnRequestFinishListener<Object>() {
            @Override
            public void onRequestSuccess(Object result) {
                T.showShort(mActivity, "发送短信验证码成功");
            }

            @Override
            public void onRequestFail(int errorCode, String desc) {
                T.showShort(mActivity, desc);
                if (mDownTimer != null)
                    mDownTimer.cancel();
                tvCode.setText("重新获取");
                tvCode.setClickable(true);
            }
        });
    }

    @Override
    public void initData() {

    }

    public void doCodeSubmit(View view) {
        String account = etAccount.getText().toString();
        String code = etCode.getText().toString();
        String password = etPwd.getText().toString();
        String passwordAgain = etPwdAgain.getText().toString();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(code) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAgain)) {
            ToastUtil.showToast(getActivity(), "请将信息填写完整");
            return;
        }

        if (!password.equals(passwordAgain)) {
            ToastUtil.showToast(getActivity(), "两次密码不一致");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("account", account);
        bundle.putString("password", password);
        bundle.putString("code", code);
        startAction(RegisterActivity.class, bundle);
    }

    private class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            tvCode.setClickable(false);
            tvCode.setText(l / 1000 + "秒");

        }

        @Override
        public void onFinish() {
            tvCode.setText("重新获取");
            tvCode.setClickable(true);
        }
    }
}