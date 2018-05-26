package com.example.administrator.js.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

public class ShankeTimeSettingActivity extends BaseActivity{


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_shanke_time_setting;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {

    }
}
