package com.example.administrator.js.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    String targetId;
    String title;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_chat;
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
    protected void getIntentData() {
        super.getIntentData();

        targetId = getIntent().getData().getQueryParameter("targetId");
        title = getIntent().getData().getQueryParameter("title");

    }

    @Override
    protected void initView() {
        mToolbar.setTitle(title);
    }

}
