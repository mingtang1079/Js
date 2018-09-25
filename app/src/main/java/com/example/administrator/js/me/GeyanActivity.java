package com.example.administrator.js.me;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.common.SimpleTextWatcher;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.VerifyUser;
import com.example.administrator.js.me.model.Zizhi;
import com.example.administrator.js.me.presenter.ZizhiPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/me/GeyanActivity")
public class GeyanActivity extends BaseActivity implements ZizhiPresenter.ZizhiResponse {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_text)
    EditText mEtText;
    @BindView(R.id.tv_tag)
    TextView mTvTag;

    @Autowired
    VerifyUser mVerifyUser;

    ZizhiPresenter mZizhiPresenter;
    MenuItem mMenuItem;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_geyan;
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

        mToolbar.setTitle("健身格言");
        inteMenu();
        mZizhiPresenter = new ZizhiPresenter(mContext);

        mEtText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                mMenuItem.setEnabled(!TextUtils.isEmpty(mCharSequence));
            }
        });
        if (mVerifyUser != null) {
            if (mVerifyUser.motto != null)
                mEtText.setText(mVerifyUser.motto);
        }
    }

    private void inteMenu() {

        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("保存");
        mMenuItem.setEnabled(false);
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {

                submit();

                return true;
            }
        });

    }

    private void submit() {

        mZizhiPresenter.updateZizhi("motto", mEtText.getText().toString());

    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onFail(String mes) {

    }
}
