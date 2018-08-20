package com.example.administrator.js.me.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Html5Activity;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.constant.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class BodyDataActivity extends Html5Activity {

    @BindView(R.id.tv_record)
    TextView mTextView;
    MenuItem mMenuItem;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_body_data;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected void initMenu() {
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("添加");
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                start(AddBodyDataActivity.class);
                return true;
            }
        });
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("身体数据");
        initMenu();
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                start(BodyListActivity.class);
            }
        });

        url = "https://www.cdmuscle.com/h5/bodydata/detail?userid=" + UserManager.getInsatance().getUser().id;
        super.initView();
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatusChange(EventMessage.BodyDataListChange mListStatusChange) {
        mWebView.reload();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
