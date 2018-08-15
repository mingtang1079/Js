package com.example.administrator.js.me;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.utils.DialogUtils;
import com.example.administrator.js.R;
import com.example.administrator.js.me.presenter.UserPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

@Route( path = "/me/AddAlipayActivity")
public class AddAlipayActivity extends BaseActivity  implements UserPresenter.UserResponse {

    @Autowired
    String name;

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
        mEditTextName.setText(name);
    }

    private void sure() {

        DialogUtils.getDefaultDialog(mContext, "", "该账号将作为您的支付宝收款账号,是否确认正确", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialogInterface, int mI) {
                mUserPresenter.updateUser("alipay", mEditTextName.getText().toString());

            }
        }).show();


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
