package com.example.administrator.js.login;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.constant.Constants;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.PreferenceUtils;
import com.appbaselib.utils.ToastUtils;
import com.example.administrator.js.App;
import com.example.administrator.js.BuildConfig;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.constant.Constans;
import com.example.administrator.js.me.ForgetPasswordActivity;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.view.PasswordToggleEditText;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

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
    TextView mBtLogin;
    @BindView(R.id.bt_register)
    TextView mBtRegister;
    @BindView(R.id.iv_weixin)
    ImageView mIvWeixin;


    private IWXAPI api;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

        if (BuildConfig.DEBUG) {
            mTvPhone.setText("18380224875");
            mPassword.setText("qqqq1111");
        }

     //   ImageLoader.load(mContext, R.drawable.spalash, mImageViewSpa);
        //mImageViewSpa.setImageDrawable(mContext.getResources().getDrawable(R.drawable.spalash));

        Observable<CharSequence> mObservablePhone = RxTextView.textChanges(mTvPhone);
        Observable<CharSequence> mCharSequenceObservablePassword = RxTextView.textChanges(mPassword);
        Observable.combineLatest(mObservablePhone, mCharSequenceObservablePassword, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence mCharSequence, CharSequence mCharSequence2) throws Exception {
                return !TextUtils.isEmpty(mCharSequence.toString()) && !TextUtils.isEmpty(mCharSequence2);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean mBoolean) throws Exception {
                mBtLogin.setEnabled(mBoolean);
            }
        });


        api = WXAPIFactory.createWXAPI(mContext, Constans.weixin);
        api.registerApp(Constans.weixin);


    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @OnClick({R.id.bt_login, R.id.bt_register, R.id.iv_weixin, R.id.tv_wjmm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:

                login();
                break;
            case R.id.bt_register:

                if (mOnbackClickListener instanceof OnbackClickListener)
                    mOnbackClickListener.onBackClick(3);

                break;
            case R.id.iv_weixin:
                if (!api.isWXAppInstalled()) {
                    showToast("请安装微信");
                } else {
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    api.sendReq(req);
                }

                break;

            case R.id.tv_wjmm:

                start(ForgetPasswordActivity.class);

                break;
        }
    }

    private void login() {

        mBtLogin.setEnabled(false);
        mBtLogin.setText("登录中");
        Http.getDefault().login(mTvPhone.getText().toString(), mPassword.getText().toString())
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>() {
                    @Override
                    protected void onSucess(User mUser) {

                        if (mOnUserGetListener != null) {
                            mOnUserGetListener.onUserGet(mUser);
                        }

                    }

                    @Override
                    protected void onFail(String message) {
                        ToastUtils.showShort(App.mInstance, message);
                        mBtLogin.setEnabled(true);
                        mBtLogin.setText("登录");
                    }


                });
    }


    OnbackClickListener mOnbackClickListener;
    OnUserGetListener mOnUserGetListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnbackClickListener = (OnbackClickListener) activity;
        mOnUserGetListener = (OnUserGetListener) activity;
    }
}
