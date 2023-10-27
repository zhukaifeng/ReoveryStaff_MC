package com.dclee.recovery.view.nfc;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.widget.TextView;

import com.dclee.recovery.R;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.util.NfcUtils;
import com.dclee.recovery.util.T;

import static com.dclee.recovery.util.NfcUtils.ByteArrayToHexString;
import static com.dclee.recovery.util.NfcUtils.flipHexStr;

public class MyNfcActivity extends BaseActivity {

    private TextView tvCard;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nfc;
    }

    @Override
    public void initView() {
        tvCard = (TextView) findViewById(R.id.tv_card);
        PackageManager packageManager = this.getPackageManager();
        boolean b1 = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC);
        T.showShort(MyNfcActivity.this, "是否支持nfc：" + b1);

        //nfc初始化设置
        NfcUtils nfcUtils = new NfcUtils(this);
    }

    @Override
    public void initData() {

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
            tvCard.setText(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
