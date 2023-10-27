package com.dclee.recovery.view.mine;

import com.dclee.recovery.base.BaseActivity;

import com.dclee.recovery.R;
import com.dclee.recovery.wedget.TitleBar;

public class MineActivity extends BaseActivity {

    private TitleBar title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    public void initView() {
        title = findViewById(R.id.title);

        if (title != null)
            title.setTitle("我的");
    }

    @Override
    public void initData() {

    }

}
