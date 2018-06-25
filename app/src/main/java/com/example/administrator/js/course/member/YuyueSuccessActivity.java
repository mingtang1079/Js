package com.example.administrator.js.course.member;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YuyueSuccessActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_back_home)
    TextView mTvBackHome;
    @BindView(R.id.tv_back_top)
    TextView mTvBackTop;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_yuyue_success;
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

    }

    @OnClick({R.id.tv_back_home, R.id.tv_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_home:
                break;
            case R.id.tv_back_top:
                break;
        }
    }
}
