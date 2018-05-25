package com.example.administrator.js.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

import butterknife.BindView;

public class AddAlipayActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText mEditTextName;
    @BindView(R.id.btn_sure)
    Button mButton;


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
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                sure();
            }
        });
    }

    private void sure() {


    }
}
