package com.example.administrator.test.me;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.test.R;
import com.example.administrator.test.R2;
import com.example.administrator.test.presenter.UserPresenter;
import com.example.core.App;
import com.example.core.UserManager;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

@Route(path = "/me/ChangeNameActivity")

public class ChangeNameActivity extends BaseActivity implements UserPresenter.UserResponse {
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.et_name)
    EditText mEditTextName;

    MenuItem mMenuItem;
    UserPresenter mUserPresenter;

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void initView() {

        mToolbar.setTitle("修改姓名");

        mUserPresenter = new UserPresenter(this);
        mEditTextName.setText(UserManager.getInsatance().getUser().nickname);
        RxTextView.textChanges(mEditTextName).skip(1).subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence mCharSequence) throws Exception {
                if (!TextUtils.isEmpty(mCharSequence))
                    mMenuItem.setEnabled(true);
                else
                    mMenuItem.setEnabled(false);
            }
        });
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setEnabled(false);
        mMenuItem.setTitle("确定");
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                if (mMenuItem.getItemId() == R.id.btn_common) {
                    updateName();
                }
                return true;
            }
        });
    }


    private void updateName() {

        mUserPresenter.updateUser("nickname", mEditTextName.getText().toString());
    }

    @Override
    protected int getContentViewLayoutID() {
        return (R.layout.activity_change_name);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @Override
    public void onSuccess() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onFail(String mes) {
        showToast(mes);

    }
}
