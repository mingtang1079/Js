package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.view.RatioImageView;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/me/RealNameVerifyActivity")
public class RealNameVerifyActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_shenfenzheng)
    EditText mEtShenfenzheng;
    @BindView(R.id.iv_shenfengzheng)
    RatioImageView mIvShenfengzheng;
    @BindView(R.id.iv_shengfenzhengback)
    RatioImageView mIvShengfenzhengback;
    @BindView(R.id.iv_gongpai)
    RatioImageView mIvGongpai;
    @BindView(R.id.iv_zhengshu)
    RatioImageView mIvZhengshu;
    @BindView(R.id.tv_sure)
    TextView mTvSure;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_real_name_verify;
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

        mToolbar.setTitle("实名认证");
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
    }
}
