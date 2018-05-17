package com.example.administrator.js.login;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Navigator;
import com.appbaselib.constant.Constants;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.CommonUtils;
import com.appbaselib.utils.JsonUtil;
import com.appbaselib.utils.LogUtils;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.App;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.constant.Constans;
import com.example.administrator.js.me.model.User;
import com.google.gson.JsonObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * A login screen that offers login via email/password.
 */
@Route(path = "/login/LoginActivity")
public class LoginActivity extends BaseActivity implements OnbackClickListener, OnUserGetListener {


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

    @Override
    public void onUserGet(final User mUser) {

        ((App) App.mInstance).connectRongYun();

        PreferenceUtils.saveObjectAsGson(mContext, Constants.PRE_USER, mUser);
        ARouter.getInstance().build("/activity/MainActivity")
                .navigation(mContext);
        finish();

    }


}

