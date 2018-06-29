package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Html5Activity;
import com.example.administrator.js.BuildConfig;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutUsActivity extends Html5Activity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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
        url = "https://www.cdmuscle.com/h5/news/detail?id=about";
        super.initView();
    }

}
