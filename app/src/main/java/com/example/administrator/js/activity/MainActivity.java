package com.example.administrator.js.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Navigator;
import com.example.administrator.js.R;
import com.example.administrator.js.exercise.ExerciseFragment;
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

    MeFragment mMeFragment;
    ExerciseFragment mExerciseFragment;

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

        mMeFragment = new MeFragment();
        mExerciseFragment = new ExerciseFragment();

        mNavigator = new Navigator(getSupportFragmentManager(), R.id.content);
        mNavigator.showFragment(mExerciseFragment);

        mBnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.js) {

                    mNavigator.showFragment(mExerciseFragment);
                } else if (item.getItemId() == R.id.vip) {

                } else if (item.getItemId() == R.id.course) {


                } else {

                    mNavigator.showFragment(mMeFragment);

                }

                return true;
            }
        });
    }

    @OnClick(R.id.iv_add)
    public void onViewClicked() {

    }
}
