package com.example.administrator.js;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

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


    Button mBtSure;

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {

        mBtSure=findViewById(R.id.bt_sure);
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

    public void onViewClicked() {

        Http.getDefault().login("18202820092", "123456")
                .compose(RxHelper.<User>handleResult())
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

