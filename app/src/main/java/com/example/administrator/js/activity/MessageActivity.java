package com.example.administrator.js.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appbaselib.adapter.FragmentAdapter;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity {

    public Toolbar mToolbar;
    public TabLayout mTab;
    public ViewPager mViewpager;

    FragmentAdapter mFragmentAdapter;

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTab = (TabLayout) findViewById(R.id.tab);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragments(), getTabTitle());
        mViewpager.setAdapter(mFragmentAdapter);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTab.setupWithViewPager(mViewpager);
        //  setTabLine(mTab, 50, 50);
    }


    protected String[] getTabTitle() {

        return new String[]{"私信", "系统"};
    }

    ;

    public List<Fragment> getFragments() {

        List<Fragment> m = new ArrayList<>();
        MessageFragment mMessageFragment = new MessageFragment();
        MessageFragment mMessageFragment1 = new MessageFragment();
        m.add(mMessageFragment);
        m.add(mMessageFragment1);
        return m;
    }

    ;

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tab;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


}
