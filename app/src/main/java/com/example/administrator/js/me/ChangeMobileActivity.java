package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.observers.DefaultObserver;

public class ChangeMobileActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_phone)
    EditText mTvPhone;
    @BindView(R.id.ed_yzm)
    EditText mEdYzm;
    @BindView(R.id.bt_yzm)
    Button mBtYzm;
    @BindView(R.id.bt_sure)
    Button mBtSure;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_change_mobile;
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
        Observable<CharSequence> mObservablePhone = RxTextView.textChanges(mTvPhone);
        Observable<CharSequence> mObservableYzm = RxTextView.textChanges(mEdYzm);

        Observable.combineLatest(mObservablePhone, mObservableYzm, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence mCharSequence, CharSequence mCharSequence2) throws Exception {

                mBtYzm.setEnabled((!TextUtils.isEmpty(mCharSequence) && mCharSequence.length() == 11));

                return !TextUtils.isEmpty(mCharSequence.toString()) && !TextUtils.isEmpty(mCharSequence2);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean mBoolean) throws Exception {
                mBtSure.setEnabled(mBoolean);
            }
        });
    }

    @OnClick({R.id.bt_yzm, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_yzm:

                requestCode();
                break;
            case R.id.bt_sure:

                sure();
                break;
        }
    }

    private void sure() {

        Http.getDefault().changemobile(mTvPhone.getText().toString(), mEdYzm.getText().toString(), UserManager.getInsatance().getUser().id)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mUser) {

                        showToast("修改成功");
                        finish();
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

    private void requestCode() {

        final int count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
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
                .subscribe(new DefaultObserver<Long>() {
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


        Http.getDefault().getCode("2", mTvPhone.getText().toString())
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

}
