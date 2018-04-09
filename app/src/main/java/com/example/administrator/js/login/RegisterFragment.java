package com.example.administrator.js.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
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

public class RegisterFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_phone)
    EditText mTvPhone;
    @BindView(R.id.ed_yzm)
    EditText mEdYzm;
    @BindView(R.id.bt_yzm)
    Button mBtYzm;
    @BindView(R.id.password)
    PasswordToggleEditText mPassword;
    @BindView(R.id.bt_register)
    Button mBtRegister;
    Unbinder unbinder;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick({R.id.iv_back, R.id.bt_yzm, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

                if (mOnbackClickListener instanceof OnbackClickListener)
                    mOnbackClickListener.onBackClick(1);

                break;
            case R.id.bt_yzm:

                requestCode();
                break;
            case R.id.bt_register:

                register();

                break;
        }
    }

    private void register() {

        Http.getDefault().register("0", mTvPhone.getText().toString(), mEdYzm.getText().toString(), "0", mPassword.getText().toString())
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>(mContext) {
                    @Override
                    protected void onSucess(User mUser) {
                        ARouter.getInstance().build("/activity/MainActivity")
                                .navigation();
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });
    }

    private void requestCode() {
        Http.getDefault().getCode(mTvPhone.getText().toString(), "1")
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mUser) {

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);

                    }
                });
    }

    OnbackClickListener mOnbackClickListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnbackClickListener = (OnbackClickListener) activity;
    }
}
