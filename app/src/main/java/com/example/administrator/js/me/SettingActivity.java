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

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.ll_ziliao)
    LinearLayout mLlZiliao;
    @BindView(R.id.ll_yuyan)
    LinearLayout mLlYuyan;
    @BindView(R.id.ll_shangke)
    LinearLayout mLlShangke;
    @BindView(R.id.ll_yinsi)
    LinearLayout mLlYinsi;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
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
        mToolbar.setTitle("设置");
    }

    @OnClick({R.id.ll_ziliao, R.id.ll_yuyan, R.id.ll_shangke, R.id.ll_yinsi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_ziliao:
                start(UserInfoActivity.class);

                break;
            case R.id.ll_yuyan:
                break;
            case R.id.ll_shangke:

                start(ShankeTixingSettingActivity.class);
                break;
            case R.id.ll_yinsi:



                break;
        }
    }
}
