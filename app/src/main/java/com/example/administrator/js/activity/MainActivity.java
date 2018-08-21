package com.example.administrator.js.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseActivity2;
import com.appbaselib.base.Navigator;
import com.example.administrator.js.R;
import com.example.administrator.js.databinding.ActivityMainBinding;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = "/activity/MainActivity")
public class MainActivity extends BaseActivity2<ActivityMainBinding> {

    Navigator mNavigator;
    Fragment mFragment4;
    Fragment mFragment1;

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
        mViewDataBinding.bnve.enableItemShiftingMode(false);
        mViewDataBinding.bnve.enableShiftingMode(false);
        mViewDataBinding.bnve.enableAnimation(false);
        mNavigator = new Navigator(getSupportFragmentManager(), R.id.content);
        mFragment4 = (Fragment) ARouter.getInstance().build("/me/MeFragment")
                .navigation();
        mFragment1 = (Fragment) ARouter.getInstance().build("/one/OneFragment").navigation(mContext);
        if (mFragment1 != null)
            mNavigator.showFragment(mFragment1);

        mViewDataBinding.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.i_music) {

                    if (mFragment1 != null)
                        mNavigator.showFragment(mFragment1);
                    else {
                        showToast("未加载该模块");
                    }
                } else if (item.getItemId() == R.id.i_me) {
                    if (mFragment4 != null)

                        mNavigator.showFragment(mFragment4);
                    else {
                        showToast("未加载该模块");
                    }
                }
                return true;
            }
        });
    }

    @OnClick(R.id.iv_add)
    public void onViewClicked() {
    }
}
