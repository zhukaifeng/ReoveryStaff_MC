package com.dclee.recovery.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.Serializable;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected Context mActivity;

    protected int mPageSize = 10;
    protected int mPageNum = 1;
    protected String mType = "";
    protected RefreshLayout mRefreshlayout;
    protected boolean isStartRefresh;

    /**
     * 当fragment与activity发生关联时调用
     *
     * @param context 与之相关联的activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayout(), null);
        return view;
    }

    /**
     * 绑定布局
     *
     * @return
     */
    protected abstract int setLayout();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onFindView(view);
        initView(view);
        initListener();
    }

    /**
     * 初始化组件
     */
    protected abstract void initView(View view);

    protected abstract void initListener();

    protected abstract void onFindView(View view);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 设置数据等逻辑代码
     */
    protected abstract void initData();

    /**
     * 简化findViewById
     *
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T fvbi(int resId) {
        return (T) getView().findViewById(resId);
    }

    /**
     * intent跳转
     *
     * @param context
     * @param clazz
     */
    protected void toClass(Context context, Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    protected void toLogin(Context context, Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * intent跳转
     *
     * @param context
     * @param clazz
     */
    protected void toClass(Context context, Class<? extends BaseActivity> clazz, String key, String value) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    /**
     * intent带值跳转
     *
     * @param context
     * @param clazz
     * @param bundle
     */
    protected void toClass(Context context, Class<? extends BaseActivity> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("mydata", bundle);
        context.startActivity(intent);
    }

    /**
     * 跳转
     */
    protected void toClass(Context context, Class<? extends BaseActivity> clazz, String key, Serializable Seria) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(key, Seria);
        startActivity(intent);
    }

    /**
     * 带返回值的跳转
     *
     * @param context
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    protected void toClass(Context context, Class<? extends BaseActivity> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        getActivity().startActivityForResult(intent, requestCode);
    }

    public void onPageIn() {
    }

    public void onPageOut() {
    }

    protected boolean isEmptyText(String text) {
        if (TextUtils.isEmpty(text)) {
            return true;
        } else {
            if ("null".equals(text)) {
                return true;
            } else {
                return false;
            }
        }
    }

    protected void loadRslResult(boolean isSuc) {
        isStartRefresh = false;
        if (mRefreshlayout != null) {
            if (mPageNum == 1) {
                mRefreshlayout.finishRefresh(isSuc);
            } else {
                mRefreshlayout.finishLoadMore(isSuc);
            }
        }
    }
}
