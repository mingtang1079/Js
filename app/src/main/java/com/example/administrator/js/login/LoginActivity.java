package com.example.administrator.js.login;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Navigator;
import com.example.administrator.js.R;

/**
 * A login screen that offers login via email/password.
 */
@Route(path = "/login/LoginActivity")
public class LoginActivity extends BaseActivity implements OnbackClickListener {


    Navigator mNavigator;
    LoginFragment mLoginFragment;
    RegisterFragment mRegisterFragment;
    UserTypeFragment mUserTypeFragment;

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {
        mLoginFragment = new LoginFragment();
        mRegisterFragment = new RegisterFragment();
        mUserTypeFragment = new UserTypeFragment();
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

    @Override
    public void onBackClick(int tag) {
        if (tag == 1) {
            mNavigator.showFragment(mLoginFragment);
        } else if (tag == 2) {
            mNavigator.showFragment(mRegisterFragment);
        } else {
            mNavigator.showFragment(mUserTypeFragment);

        }
    }

    @Override
    public void onBackPressed() {

        if (mRegisterFragment.isVisible())
            mNavigator.showFragment(mLoginFragment);
        else if (mUserTypeFragment.isVisible()) {
            mNavigator.showFragment(mRegisterFragment);
        } else
            finish();

    }
}

