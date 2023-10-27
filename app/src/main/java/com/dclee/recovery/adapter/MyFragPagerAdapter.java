package com.dclee.recovery.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyFragPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mfragmentList;

    public MyFragPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mfragmentList = fragmentList;
    }

    //获取集合中的某个项
    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    //返回绘制项的数目
    @Override
    public int getCount() {
        return mfragmentList.size();
    }
}
