package com.example.administrator.js.me.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Html5Activity;
import com.example.administrator.js.R;

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

            }
        });

    }
}
