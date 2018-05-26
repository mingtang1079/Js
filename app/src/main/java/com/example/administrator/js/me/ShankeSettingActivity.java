package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShankeSettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_time_setting)
    LinearLayout mLlTimeSetting;
    @BindView(R.id.ll_tixing_setting)
    LinearLayout mLlTixingSetting;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_shanke_setting;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("课程设置");
    }

    @OnClick({R.id.ll_time_setting, R.id.ll_tixing_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time_setting:

                start(ShankeTimeSettingActivity.class);

                break;
            case R.id.ll_tixing_setting:

                start(ShankeTixingSettingActivity.class);

                break;
        }
    }
}
