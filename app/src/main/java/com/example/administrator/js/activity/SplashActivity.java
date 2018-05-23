package com.example.administrator.js.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.administrator.js.App;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.login.LoginActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

@Route(path = "/activity/SplashActivity")
public class SplashActivity extends com.appbaselib.base.BaseActivity {

    User mUser;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;

    }

    @Override
    protected void initView() {

        mUser = UserManager.getInsatance().getUser();
    }

    @Override
    protected void onStart() {
        super.onStart();

        requestPermission();



    }

    private void luanch() {
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

    private void requestPermission() {

        {
            RxPermissions mRxPermissions = new RxPermissions(this);
            mRxPermissions
                    .request(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean mBoolean) throws Exception {
                            if (mBoolean) {
                                luanch();

                            } else {
                                showToast("没有权限,请手动开启定位权限");
                                finish();
                            }
                        }
                    });
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
