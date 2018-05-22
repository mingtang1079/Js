package com.example.administrator.js.vip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.User;

@Route(path = "/vip/VipUserDetailActivity")
public class VipUserDetailActivity extends BaseActivity {

    @Autowired
    User mUser;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_vip_user_detail;
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
