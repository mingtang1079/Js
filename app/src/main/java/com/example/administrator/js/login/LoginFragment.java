package com.example.administrator.js.login;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.User;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tangming on 2018/4/8.
 */

public class LoginFragment extends BaseFragment {

    @BindView(R.id.tv_phone)
    EditText mTvPhone;
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

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {

        Http.getDefault().login("18202820092", "123456")
                .as(RxHelper.<User>handleResult(mContext))
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

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
