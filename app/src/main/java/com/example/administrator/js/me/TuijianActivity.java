package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.Tuijian;
import com.google.gson.JsonObject;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class TuijianActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_mobile)
    EditText mEtMobile;

    MenuItem mMenuItem;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tuijian;
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
        mToolbar.setTitle("推荐好友信息");

        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setEnabled(false);
        mMenuItem.setTitle("提交");

        Observable<CharSequence> mObservablePhone = RxTextView.textChanges(mEtName);
        Observable<CharSequence> mCharSequenceObservablePassword = RxTextView.textChanges(mEtMobile);
        Observable.combineLatest(mObservablePhone, mCharSequenceObservablePassword, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence mCharSequence, CharSequence mCharSequence2) throws Exception {
                return !TextUtils.isEmpty(mCharSequence.toString()) && !TextUtils.isEmpty(mCharSequence2);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean mBoolean) throws Exception {
                mMenuItem.setEnabled(mBoolean);
            }
        });
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                if (mMenuItem.getItemId() == R.id.btn_common) {
                    save();
                }
                return true;
            }
        });
    }

    private void save() {

        Http.getDefault().promotionSave(UserManager.getInsatance().getUser().id, mEtName.getText().toString(), mEtMobile.getText().toString())
                .as(RxHelper.<Tuijian>handleResult(mContext))
                .subscribe(new ResponceSubscriber<Tuijian>(mContext) {
                    @Override
                    protected void onSucess(Tuijian mJsonObject) {
                        showToast("提交成功");
                        finish();
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }
}
