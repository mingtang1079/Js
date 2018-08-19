package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.utils.PackageUtil;
import com.example.administrator.js.App;
import com.example.administrator.js.R;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_banben)
    TextView mTvBanben;
    @BindView(R.id.btn_update)
    Button mBtnUpdate;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about_us;
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
        mToolbar.setTitle("关于我们");
    mTvBanben.setText("V"+ PackageUtil.getAppVersionName(App.mInstance));
    }

    @OnClick(R.id.btn_update)
    public void onViewClicked() {
        Beta.checkUpgrade();
    }
}
