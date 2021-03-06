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
import com.appbaselib.constant.Constants;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.view.PasswordToggleEditText;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.uber.autodispose.AutoDispose;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;

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
    TextView mBtRegister;

    private String role;
    public String openId;
    public String nickname;
    public String img;
    public String sex;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {

        Observable<CharSequence> mObservablePhone = RxTextView.textChanges(mTvPhone);
        Observable<CharSequence> mCharSequenceObservablePassword = RxTextView.textChanges(mEdYzm);
        Observable<CharSequence> mObservableYzm = RxTextView.textChanges(mPassword);

        Observable.combineLatest(mObservablePhone, mCharSequenceObservablePassword, mObservableYzm, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence mCharSequence, CharSequence mCharSequence2, CharSequence mCharSequence3) throws Exception {

                if (!TextUtils.isEmpty(mCharSequence) && mCharSequence.length() == 11) {
                    mBtYzm.setEnabled(true);
                } else {
                    mBtYzm.setEnabled(false);

                }

                return !TextUtils.isEmpty(mCharSequence.toString()) && !TextUtils.isEmpty(mCharSequence2) && !TextUtils.isEmpty(mCharSequence3);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean mBoolean) throws Exception {
                mBtRegister.setEnabled(mBoolean);
            }
        });

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

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMessage(EventMessage.UsertypeMessage message) {

        if (message != null) {
            role = message.i + "";
        }

    }

    private void register() {

        Http.getDefault().register(TextUtils.isEmpty(openId) ? "0" : "1", openId,
                mTvPhone.getText().toString(), mEdYzm.getText().toString(), role, mPassword.getText().toString(), nickname, img, sex)
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>(mContext) {
                    @Override
                    protected void onSucess(User mUser) {

                        if (mOnUserGetListener != null) {
                            mOnUserGetListener.onUserGet(mUser);
                        }
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });
    }

    Disposable mDisposable;

    private void requestCode() {

        final int count = 60;
        mDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)

                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long mLong) throws Exception {
                        return count - mLong;
                    }
                }).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable mDisposable) throws Exception {
                        mBtYzm.setEnabled(false);

                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long mLong) {
                        mBtYzm.setText("剩余" + mLong + "秒");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mBtYzm.setEnabled(true);
                        mBtYzm.setText("获取验证码");
                    }
                });


        Http.getDefault().getCode("1", mTvPhone.getText().toString())
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
    OnUserGetListener mOnUserGetListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnbackClickListener = (OnbackClickListener) activity;
        mOnUserGetListener = (OnUserGetListener) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
