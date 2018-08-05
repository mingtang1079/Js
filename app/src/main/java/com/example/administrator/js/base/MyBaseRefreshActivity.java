package com.example.administrator.js.base;

import android.support.v7.widget.Toolbar;

import com.appbaselib.base.BaseRefreshActivity;
import com.example.administrator.js.R;

import butterknife.BindView;

public abstract class MyBaseRefreshActivity<T> extends BaseRefreshActivity {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void initView() {
        super.initView();
        toggleShowLoading(true);
        requestData();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }


}
