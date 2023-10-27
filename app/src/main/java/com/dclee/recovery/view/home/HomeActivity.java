package com.dclee.recovery.view.home;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dclee.recovery.MyApp;
import com.dclee.recovery.R;
import com.dclee.recovery.adapter.HomeFragmentPagerAdapter;
import com.dclee.recovery.base.BaseActivity;
import com.dclee.recovery.util.FileUtil;
import com.dclee.recovery.util.ToastUtil;
import com.dclee.recovery.view.check.CheckActivity;
import com.lzy.imagepicker.ImagePicker;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class HomeActivity extends BaseActivity {

    public static final int PAGE_HOME = 0;
    public static final int PAGE_MINE = 1;
    private static final String TAG = "HomeAct";

    private LinearLayout llBottomHome;
    private ImageView ivBottomHome;
    private TextView tvBottomHome;
    private LinearLayout llBottomMe;
    private ImageView ivBottomMe;
    private TextView tvBottomMe;

    private ViewPager2 vpHome;


    public static final int FLAG_SUCCESS = 1;//创建成功
    public static final int FLAG_EXISTS = 2;//已存在
    public static final int FLAG_FAILED = 3;//创建失败

    private List<Fragment> mFragments = new ArrayList<>();

    public HomeActivity() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        Log.d("zkf","home activity");
        llBottomHome = findViewById(R.id.ll_bottom_home);
        ivBottomHome = findViewById(R.id.iv_bottom_home);
        tvBottomHome = findViewById(R.id.tv_bottom_home);
        llBottomMe = findViewById(R.id.ll_bottom_me);
        ivBottomMe = findViewById(R.id.iv_bottom_me);
        tvBottomMe = findViewById(R.id.tv_bottom_me);
        vpHome = findViewById(R.id.vp_home);

        mFragments.add(new HomeFrag());
        //mFragments.add(new MineFrag());

        HomeFragmentPagerAdapter mAdapter = new HomeFragmentPagerAdapter(this, mFragments);
        vpHome.setAdapter(mAdapter);
        vpHome.setCurrentItem(PAGE_HOME);

    }

    private void initFile() {
        createDir(FileUtil.getSDPath(getBaseContext())+MyApp.UrlAudio);
    }

    /**
     * 创建 文件夹
     *
     * @param dirPath 文件夹路径
     * @return 结果码
     */
    public static int createDir(String dirPath) {
        File dir = new File(dirPath);
        //文件夹是否已经存在
        if (dir.exists()) {
            Log.e(TAG, "The directory [ " + dirPath + " ] has already exists");
            return FLAG_EXISTS;
        }
        if (!dirPath.endsWith(File.separator)) {//不是以 路径分隔符 "/" 结束，则添加路径分隔符 "/"
            dirPath = dirPath + File.separator;
        }
        //创建文件夹
        if (dir.mkdirs()) {
            Log.e(TAG, "create directory [ " + dirPath + " ] success");
            return FLAG_SUCCESS;
        }

        Log.e(TAG, "create directory [ " + dirPath + " ] failed");
        return FLAG_FAILED;
    }

    @Override
    public void initData() {
        new RxPermissions(HomeActivity.this).request(Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            ToastUtil.showToast(getActivity(), "请先开启相关权限");
                            finish();
                        } else {
                            initFile();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK && resultCode != ImagePicker.RESULT_CODE_ITEMS) {
            return;
        }
        if (null != data) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                if (result.contains("id") && result.contains("name")) {
                    startAction(CheckActivity.class, "StaffData", result);
                }
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
            }
        }
    }
}