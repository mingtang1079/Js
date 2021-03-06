package com.example.administrator.js.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Navigator;
import com.example.administrator.js.R;
import com.example.administrator.js.activity.fragment.PaySuccessFragment;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/activity/PaySuccessfulActivity")
public class PaySuccessfulActivity extends BaseActivity {

    @Autowired
    String orderId;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_pay_successful;
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
        mToolbar.setTitle("支付结果");

        Navigator mNavigator = new Navigator(getSupportFragmentManager(), R.id.content);
        mNavigator.showFragment(new PaySuccessFragment());
    }

}
