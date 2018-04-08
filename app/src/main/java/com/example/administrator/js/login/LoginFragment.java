package com.example.administrator.js.login;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.example.administrator.js.R;

import butterknife.BindView;

/**
 * Created by tangming on 2018/4/8.
 */

public class LoginFragment extends BaseFragment {

    @BindView(R.id.tv_phone)
    AutoCompleteTextView mTvPhone;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.bt_sure)
    Button mBtSure;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

        mBtSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                ARouter.getInstance().build("/login/LoginActivity")
                        //    .withTransition(R.anim.anim_enter,R.anim.anim_exit)

                        .navigation();
            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
