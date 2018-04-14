package com.example.administrator.js.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Navigator;
import com.example.administrator.js.R;
import com.example.administrator.js.me.MeFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/activity/MainActivity")
public class MainActivity extends BaseActivity {


    @BindView(R.id.bnve)
    BottomNavigationViewEx mBnve;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.container)
    FrameLayout mContainer;

    Navigator mNavigator;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {
        mBnve.enableItemShiftingMode(false);
        mBnve.enableShiftingMode(false);
        mBnve.enableAnimation(false);

        mNavigator = new Navigator(getSupportFragmentManager(), R.id.content);
        mNavigator.showFragment(new MeFragment());
    }

    @OnClick(R.id.iv_add)
    public void onViewClicked() {
    }
}
