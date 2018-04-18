package com.example.administrator.test.me;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.test.R;
import com.example.administrator.test.R2;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/me/UserInfoActivity")
public class UserInfoActivity extends BaseActivity {


    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.ll_head)
    LinearLayout mLlHead;
    @BindView(R2.id.ll_nick)
    LinearLayout mLlNick;
    @BindView(R2.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R2.id.ll_area)
    LinearLayout mLlArea;
    @BindView(R2.id.ll_barcode)
    LinearLayout mLlBarcode;
    @BindView(R2.id.ll_weixin)
    LinearLayout mLlWeixin;
    @BindView(R2.id.ll_phone)
    LinearLayout mLlPhone;
    @BindView(R2.id.ll_password)
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

    @OnClick({R2.id.ll_head, R2.id.ll_nick, R2.id.ll_sex, R2.id.ll_area, R2.id.ll_barcode, R2.id.ll_weixin, R2.id.ll_phone, R2.id.ll_password})
    public void onViewClicked(View view) {

    }
}
