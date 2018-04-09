package com.example.administrator.js.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appbaselib.base.BaseFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.User;
import com.example.administrator.js.view.PasswordToggleEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by tangming on 2018/4/8.
 */

public class LoginFragment extends BaseFragment {


    @BindView(R.id.tv_phone)
    EditText mTvPhone;
    @BindView(R.id.password)
    PasswordToggleEditText mPassword;
    @BindView(R.id.tv_wjmm)
    TextView mTvWjmm;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.bt_register)
    Button mBtRegister;
    @BindView(R.id.iv_weixin)
    ImageView mIvWeixin;
    Unbinder unbinder;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {


    }

    @OnClick(R.id.bt_login)
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


    @OnClick({R.id.bt_login, R.id.bt_register, R.id.iv_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:

                break;
            case R.id.bt_register:


                break;
            case R.id.iv_weixin:


                break;
        }
    }
}
