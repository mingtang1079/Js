package com.example.administrator.js.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.me.presenter.UserPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class AddAlipayActivity extends BaseActivity  implements UserPresenter.UserResponse {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText mEditTextName;
    @BindView(R.id.btn_sure)
    Button mButton;

    UserPresenter mUserPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_add_alipay;
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
        mToolbar.setTitle("添加支付宝账号");
        mUserPresenter=new UserPresenter(mContext);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                sure();
            }
        });
        RxTextView.textChanges(mEditTextName).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence mCharSequence) throws Exception {
                if (!TextUtils.isEmpty(mCharSequence))
                {
                    mButton.setEnabled(true);
                }
                else {
                    mButton.setEnabled(false);
                }
            }
        });
    }

    private void sure() {

        mUserPresenter.updateUser("alipay", mEditTextName.getText().toString());

    }

    @Override
    public void onSuccess() {
        finish();

    }

    @Override
    public void onFail(String mes) {
        showToast(mes);

    }
}
