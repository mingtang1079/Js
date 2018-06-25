package com.example.administrator.js.course.member;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.activity.MutichoosePhotoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TousuActivity extends MutichoosePhotoActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.btn_sure)
    Button mBtnSure;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tousu;
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
        super.initView();

    }

    @OnClick(R.id.btn_sure)
    public void onViewClicked() {

    }
}
