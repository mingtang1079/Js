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
import com.example.administrator.js.me.model.VerifyUser;
import com.example.administrator.js.me.presenter.UserPresenter;
import com.example.administrator.js.me.presenter.ZizhiPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

@Route(path = "/me/YaoqingmaActivity")
public class YaoqingmaActivity extends BaseActivity implements ZizhiPresenter.ZizhiResponse  {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText mEditTextName;
    @BindView(R.id.btn_sure)
    Button mButton;

    @Autowired
    VerifyUser mVerifyUser;

    ZizhiPresenter mZizhiPresenter;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_yaoqingma;
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
        mToolbar.setTitle("邀请码");
        mZizhiPresenter = new ZizhiPresenter(mContext);

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

        mEditTextName.setText(mVerifyUser.invitecode);
    }

    private void sure() {

        mZizhiPresenter.updateZizhi("invitecode", mEditTextName.getText().toString());


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
