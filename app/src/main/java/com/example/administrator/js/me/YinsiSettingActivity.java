package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.utils.LogUtils;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.model.Userprice;
import com.example.administrator.js.me.presenter.UserPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YinsiSettingActivity extends BaseActivity implements UserPresenter.UserResponse {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.switchcomat)
    SwitchCompat mSwitchcomat;

    UserPresenter mUserPresenter;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_yinsi_setting;
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

        mToolbar.setTitle("隐私设置");
        mUserPresenter = new UserPresenter(this);

        User mUser = UserManager.getInsatance().getUser();
        if ("0".equals(mUser.privatemode)) {
            mSwitchcomat.setChecked(false);
        } else {
            mSwitchcomat.setChecked(true);
        }
        mSwitchcomat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton mCompoundButton, boolean mB) {
                if (mB) {
                    mUserPresenter.updateUser("privatemode", "1");

                } else {
                    mUserPresenter.updateUser("privatemode", "0");

                }
            }
        });
    }

    @Override
    public void onSuccess() {
        LogUtils.d("sucess");
    }

    @Override
    public void onFail(String mes) {
        LogUtils.d("false");

    }
}
