package com.dclee.recovery.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.base.CacheUtil;
import com.dclee.recovery.base.Config;
import com.dclee.recovery.pojo.LoginResult;
import com.dclee.recovery.util.PreferencesUtils;
import com.dclee.recovery.util.RequestUtil;
import com.dclee.recovery.util.T;
import com.dclee.recovery.util.ToastUtil;
import com.dclee.recovery.view.home.HomeActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.commonsdk.debug.I;

import org.xutils.http.RequestParams;

import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity {
    private EditText mAccount;
    private EditText mPassword;
    private RequestUtil mRequestUtil;
    private CheckBox cbPn;
    private TextView tvPn;
    private Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        Log.d("zkf","login activity");
        mAccount = findViewById(R.id.et_account);
        mPassword = findViewById(R.id.et_password);
        cbPn = findViewById(R.id.cb_pn);
        tvPn = findViewById(R.id.tv_pn);
        btnLogin = findViewById(R.id.btn_login);
        mRequestUtil = new RequestUtil(this);

        String msg1 = "已阅读并同意";
        String msg2 = "《禹洲美城骑手协议》";
        //zkfdebug
        mAccount.setText("NX001");
        mPassword.setText("123456");
        cbPn.setChecked(true);

        SpannableString spannableString = new SpannableString(msg1 + msg2);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lite_blue)), msg1.length(), (msg1 + msg2).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPn.setText(spannableString);

        tvPn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://yzmckj.cn/privateHome.html");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zkf","click 1");
                doLogin();
//                Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
              //  startActivity(new Intent(LoginActivity.this, HomeActivity.class));

            }
        });
    }

    public void onRegister(View view) {
        if (!cbPn.isChecked()) {
            T.showShort(mActivity, "请先同意禹洲美城骑手协议");
            return;
        }
        startAction(CodeActivity.class);
    }

    @SuppressLint("CheckResult")
    public void doLogin() {
        if (!cbPn.isChecked()) {
            T.showShort(mActivity, "请先同意嘉达通蓝牙版骑手协议");
            return;
        }
        new RxPermissions(this).request(Manifest.permission.INTERNET)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            String account = mAccount.getText().toString();
                            String password = mPassword.getText().toString();
                            if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                                ToastUtil.showToast(getActivity(), "用户名及密码不能为空");
                                return;
                            }
                            RequestParams requestParams = new RequestParams();
                            requestParams.addParameter("username", account);
                            requestParams.addParameter("password", password);

//                            String umToken = PreferencesUtils.getString(mActivity, Config.UM_TOKEN, "");
//                            if (!TextUtils.isEmpty(umToken))
//                                requestParams.addParameter("deviceToken", umToken);

                            mRequestUtil.doPostWithoutToken("app/common/login", requestParams, LoginResult.class, new RequestUtil.OnRequestFinishListener<LoginResult>() {
                                @Override
                                public void onRequestSuccess(LoginResult result) {
                                    CacheUtil.login(result);
                                    Log.d("zkf","result:" + result.toString());
                                    startActivity(new Intent(getActivity(), HomeActivity.class));
                                    finish();
                                }

                                @Override
                                public void onRequestFail(int errorCode, String desc) {

                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void initData() {

    }
}
