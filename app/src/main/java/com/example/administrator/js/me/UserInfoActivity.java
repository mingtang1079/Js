package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/me/UserInfoActivity")
public class UserInfoActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_head)
    LinearLayout mLlHead;
    @BindView(R.id.ll_nick)
    LinearLayout mLlNick;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.ll_area)
    LinearLayout mLlArea;
    @BindView(R.id.ll_barcode)
    LinearLayout mLlBarcode;
    @BindView(R.id.ll_weixin)
    LinearLayout mLlWeixin;
    @BindView(R.id.ll_phone)
    LinearLayout mLlPhone;
    @BindView(R.id.ll_password)
    LinearLayout mLlPassword;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user_info;
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

        mToolbar.setTitle("个人资料");
    }

    @OnClick({R.id.ll_head, R.id.ll_nick, R.id.ll_sex, R.id.ll_area, R.id.ll_barcode, R.id.ll_weixin, R.id.ll_phone, R.id.ll_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                break;
            case R.id.ll_nick:
                break;
            case R.id.ll_sex:
                break;
            case R.id.ll_area:
                break;
            case R.id.ll_barcode:
                break;
            case R.id.ll_weixin:
                break;
            case R.id.ll_phone:
                break;
            case R.id.ll_password:
                break;
        }
    }
}
