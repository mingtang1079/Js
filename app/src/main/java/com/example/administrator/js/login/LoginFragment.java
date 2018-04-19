package com.example.administrator.js.login;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.constant.Constants;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.BuildConfig;
import com.example.administrator.js.R;
import com.example.core.model.User;
import com.example.administrator.js.view.PasswordToggleEditText;
import com.example.core.network.Http;
import com.jakewharton.rxbinding2.widget.RxTextView;

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
    Button mBtLogin;
    @BindView(R.id.bt_register)
    Button mBtRegister;
    @BindView(R.id.iv_weixin)
    ImageView mIvWeixin;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

        if (BuildConfig.DEBUG) {
            mTvPhone.setText("18202820092");
            mPassword.setText("123456");
        }

        Observable<CharSequence> mObservablePhone = RxTextView.textChanges(mTvPhone).skip(1);
        Observable<CharSequence> mCharSequenceObservablePassword = RxTextView.textChanges(mPassword).skip(1);
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

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @OnClick({R.id.bt_login, R.id.bt_register, R.id.iv_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:

                login();
                break;
            case R.id.bt_register:

                if (mOnbackClickListener instanceof OnbackClickListener)
                    mOnbackClickListener.onBackClick(2);

                break;
            case R.id.iv_weixin:


                break;
        }
    }

    private void login() {
        Http.getDefault().login(mTvPhone.getText().toString(), mPassword.getText().toString())
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>(mContext) {
                    @Override
                    protected void onSucess(User mUser) {

                        PreferenceUtils.saveObjectAsGson(mContext, Constants.PRE_USER, mUser);
                        ARouter.getInstance().build("/activity/MainActivity")
                                .navigation();
                        //   startActivity(new Intent(mContext, MainActivity.class));
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
