package com.example.administrator.js.login;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Navigator;
import com.appbaselib.constant.Constants;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.CommonUtils;
import com.appbaselib.utils.JsonUtil;
import com.appbaselib.utils.LogUtils;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.App;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.constant.Constans;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.me.model.User;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

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
    LoginFragmentKt mLoginFragment;
    RegisterFragment mRegisterFragment;
    UserTypeFragment mUserTypeFragment;

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {
        mLoginFragment = new LoginFragmentKt();
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
            mNavigator.showFragment(mUserTypeFragment);
        else if (mUserTypeFragment.isVisible()) {
            mNavigator.showFragment(mLoginFragment);
        } else
            finish();

    }

    @Override
    public void onUserGet(final User mUser) {

        PreferenceUtils.saveObjectAsGson(mContext, Constants.PRE_USER, mUser);
        PreferenceUtils.setPrefString(mContext,Constans.TOKEN,mUser.apitoken);

        ((App) App.mInstance).connectRongYun();

        ARouter.getInstance().build("/activity/MainActivity")
                .navigation(mContext);
        finish();

    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatusChange(EventMessage.weixinLogin mListStatusChange) {
        requestUserInfo(mListStatusChange.code);

    }

    private void requestUserInfo(final String mCode) {
        Map<String,String> mStringStringMap=new HashMap<>();
        mStringStringMap.put("code",mCode);

        Http.getDefault().getUserInfoByWxCode(mStringStringMap)
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>() {
                    @Override
                    protected void onSucess(User mUser) {
                        if (mUser != null) {
                            if (!TextUtils.isEmpty(mUser.id)) {
                                onUserGet(mUser);
                            } else {
                                //走注册界面 绑定手机号
                                mRegisterFragment.openId = mUser.openid;
                                mRegisterFragment.nickname = mUser.nickname;
                                mRegisterFragment.img = mUser.img;
                                mRegisterFragment.sex = mUser.sex;
                                mNavigator.showFragment(mUserTypeFragment);

                            }
                        }
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });

    }

}

