package com.example.administrator.js.vipandtrainer.trainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.me.member.MyOrderActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplySuccessActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_back_home)
    TextView mTvBackHome;
    @BindView(R.id.tv_back_top)
    TextView mTvBackTop;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_apply_success;
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
        mToolbar.setTitle("申请提交");
    }

    @OnClick({R.id.tv_back_home, R.id.tv_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_home:
                finish();
                break;
            case R.id.tv_back_top:
                start(MyOrderActivity.class);
                finish();
                break;
        }
    }
}
