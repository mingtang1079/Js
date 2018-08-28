package com.example.administrator.js.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.constant.EventMessage;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    String targetId;
    String title;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    MenuItem mMenuItem;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_chat;
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
    protected void getIntentData() {
        super.getIntentData();

        targetId = getIntent().getData().getQueryParameter("targetId");
        title = getIntent().getData().getQueryParameter("title");


    }

    @Override
    protected void initView() {
        mToolbar.setTitle(title);
        inteMenu();
        if (UserManager.getInsatance().getUser() != null) {
            if ("0".equals(UserManager.getInsatance().getUser().role)) {

                //        mIvAdd.setVisibility(View.GONE);
                mMenuItem.setVisible(false);

            } else {

                mMenuItem.setVisible(true);

            }
        }
    }
    private void inteMenu() {

        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("预约课程");

        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {

//                ARouter.getInstance().build("/vipandtrainer/BuySiJiaoKeActivity")
//                        .withString("id", targetId)
//                        .navigation(mContext);

                EventBus.getDefault().postSticky(new EventMessage.ChatButtonClick());
                finish();
                return true;
            }
        });

    }
}
