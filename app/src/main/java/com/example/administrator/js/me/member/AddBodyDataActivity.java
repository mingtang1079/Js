package com.example.administrator.js.me.member;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBodyDataActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_reason)
    LinearLayout mLlReason;
    @BindView(R.id.et_tizhong)
    EditText mEtTizhong;
    @BindView(R.id.et_shengao)
    EditText mEtShengao;
    @BindView(R.id.et_tizhi)
    EditText mEtTizhi;
    @BindView(R.id.et_daixie)
    EditText mEtDaixie;
    @BindView(R.id.et_shuifeng)
    EditText mEtShuifeng;
    @BindView(R.id.tv_weidu)
    TextView mTvWeidu;
    @BindView(R.id.btn_sure)
    Button mBtnSure;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_add_body_data;
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

        mToolbar.setTitle("添加数据");


    }

    @OnClick({R.id.tv_weidu, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_weidu:

                break;
            case R.id.btn_sure:


                break;
        }
    }
}
