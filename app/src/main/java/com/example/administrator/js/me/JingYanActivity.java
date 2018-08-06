package com.example.administrator.js.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alipay.sdk.app.H5AuthActivity;
import com.appbaselib.base.Html5Activity;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;

import butterknife.BindView;

public class JingYanActivity extends Html5Activity {

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
        mToolbar.setTitle("等级详情");
        url = "https://www.cdmuscle.com/h5/news/detail?id=dengjixiangqin&userid="+ UserManager.getInsatance().getUser().id;
        super.initView();
    }

}
