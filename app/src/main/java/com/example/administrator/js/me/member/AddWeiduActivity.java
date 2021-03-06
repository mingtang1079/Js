package com.example.administrator.js.me.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/member/AddWeiduActivity")
public class AddWeiduActivity extends BaseActivity {

    @Autowired
    BodyData mBodyData;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_xiong)
    EditText mEtXiong;
    @BindView(R.id.et_yao)
    EditText mEtYao;
    @BindView(R.id.et_xiaotui)
    EditText mEtXiaotui;
    @BindView(R.id.et_datui)
    EditText mEtDatui;
    @BindView(R.id.et_dabi)
    EditText mEtDabi;
    @BindView(R.id.et_xiaobi)
    EditText mEtXiaobi;
    @BindView(R.id.et_tun)
    EditText mEtTun;
    @BindView(R.id.btn_sure)
    Button mBtnSure;


    @BindView(R.id.et_xiaotui_right)
    EditText mEtXiaotuiRight;
    @BindView(R.id.et_datui_right)
    EditText mEtDatuiRight;
    @BindView(R.id.et_dabi_right)
    EditText mEtDabiRight;
    @BindView(R.id.et_xiaobi_right)
    EditText mEtXiaobiRight;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_add_weidu;
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

        mToolbar.setTitle("维度");
        if (mBodyData != null) {
            mEtXiong.setText(mBodyData.wdxiong);
            mEtYao.setText(mBodyData.wdyao);
            mEtXiaotui.setText(mBodyData.wdxiaotui);
            mEtDatui.setText(mBodyData.wddatui);
            mEtDabi.setText(mBodyData.wddabi);
            mEtXiaobi.setText(mBodyData.wdxiaobi);
            mEtTun.setText(mBodyData.wdtun);

            mEtXiaotuiRight.setText(mBodyData.rightwdxiaotui);
            mEtDatuiRight.setText(mBodyData.rightwddatui);
            mEtDabiRight.setText(mBodyData.rightwddabi);
            mEtXiaobiRight.setText(mBodyData.rightwdxiaobi);

        } else {
            mBodyData = new BodyData();
        }
        mEtXiong.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.wdxiong = mCharSequence.toString();
            }
        });
        mEtYao.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.wdyao = mCharSequence.toString();

            }
        });
        mEtXiaotui.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.wdxiaotui = mCharSequence.toString();

            }
        });
        mEtDatui.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.wddatui = mCharSequence.toString();

            }
        });
        mEtDabi.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.wddabi = mCharSequence.toString();

            }
        });
        mEtXiaobi.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.wdxiaobi = mCharSequence.toString();

            }
        });
        mEtTun.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.wdtun = mCharSequence.toString();

            }
        });

        //
        mEtXiaotuiRight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.rightwdxiaotui = mCharSequence.toString();

            }
        });
        mEtDatuiRight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.rightwddatui = mCharSequence.toString();

            }
        });
        mEtDabiRight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.rightwddabi = mCharSequence.toString();

            }
        });
        mEtXiaobiRight.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mBodyData.rightwdxiaobi = mCharSequence.toString();

            }
        });

    }

    @OnClick(R.id.btn_sure)
    public void onViewClicked() {

        Intent mIntent = new Intent();
        mIntent.putExtra("data", mBodyData);
        setResult(Activity.RESULT_OK, mIntent);
        finish();
    }

    @OnClick({R.id.et_xiaotui_right, R.id.et_datui_right, R.id.et_dabi_right, R.id.et_xiaobi_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_xiaotui_right:
                break;
            case R.id.et_datui_right:
                break;
            case R.id.et_dabi_right:
                break;
            case R.id.et_xiaobi_right:
                break;
        }
    }


    public abstract static class SimpleTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

        }

        @Override
        public void afterTextChanged(Editable mEditable) {

        }
    }
}
