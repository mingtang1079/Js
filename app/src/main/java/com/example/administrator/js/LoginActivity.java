package com.example.administrator.js;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.tv_phone)
    AutoCompleteTextView mTvPhone;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.bt_sure)
    Button mBtSure;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {

        mBtSure = findViewById(R.id.bt_sure);
        mBtSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                onViewClicked();
            }
        });
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {

        Http.getDefault().login("18202820092", "123456")
                .as(RxHelper.<User>handleResult(this))
                .subscribe(new ResponceSubscriber<User>() {
                    @Override
                    protected void onSucess(User mUser) {
                        Log.d("ddd====?", "fdsfs");
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });
    }
}

