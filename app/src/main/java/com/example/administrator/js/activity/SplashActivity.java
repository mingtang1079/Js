package com.example.administrator.js.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.administrator.js.App;
import com.example.administrator.js.R;
import com.example.administrator.js.model.User;
import com.example.administrator.js.login.LoginActivity;
@Route(path = "/activity/SplashActivity")
public class SplashActivity extends com.appbaselib.base.BaseActivity {

    User mUser;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;

    }

    @Override
    protected void initView() {

        mUser = ((App) App.mInstance).getUser();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mUser != null) {
//            ARouter.getInstance().build("/activity/MainActivity")
//                    .withTransition(R.anim.anim_enter,R.anim.anim_exit)
//                    .navigation();

            Intent m = new Intent(this, MainActivity.class);
            startActivity(m);
            finish();

        } else {
//            ARouter.getInstance().build("/login/LoginActivity")
//                    .navigation();
            Intent m = new Intent(this, LoginActivity.class);
            startActivity(m);
            finish();
        }

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }


}
