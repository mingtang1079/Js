package com.example.administrator.js.login;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Navigator;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
@Route(path = "/login/LoginActivity")
public class LoginActivity extends BaseActivity {


    Navigator mNavigator;
    LoginFragment mLoginFragment;
    registerFragment mRegisterFragment;

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {
        mLoginFragment = new LoginFragment();
        mRegisterFragment = new registerFragment();
        mNavigator = new Navigator(getSupportFragmentManager(), R.id.container);
       mNavigator.showFragment(mLoginFragment);

    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


}

