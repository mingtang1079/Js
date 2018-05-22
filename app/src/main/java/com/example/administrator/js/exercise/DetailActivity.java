package com.example.administrator.js.exercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.Html5Activity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.exercise.model.Main;
import com.umeng.commonsdk.statistics.UMServerURL;

@Route(path = "/exercise/DetailActivity")
public class DetailActivity extends Html5Activity {

    @Autowired
    Main mMain;
    MenuItem mMenuItem;

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        if (mMain != null) {
            if (mMain.favorite.equals("1")) {
                mMenuItem.setTitle("取消收藏");
            } else {
                mMenuItem.setTitle("收藏");

            }
        } else {
            mMenuItem.setTitle("收藏");

        }
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem mMenuItem) {

                Http.getDefault().collection(UserManager.getInsatance().getUser().id, mMain.id, mMenuItem.getTitle().toString().equals("收藏") ? "1" : "0")
                        .as(RxHelper.<String>handleResult(mContext))
                        .subscribe(new ResponceSubscriber<String>(mContext) {
                            @Override
                            protected void onSucess(String mS) {
                                if (mMenuItem.getTitle().toString().equals("收藏")) {
                                    mMenuItem.setTitle("取消收藏");
                                } else {
                                    mMenuItem.setTitle("收藏");

                                }

                            }

                            @Override
                            protected void onFail(String message) {

                            }
                        });

                return false;
            }
        });
        url = mMain.linkurl;
        super.initView();

    }
}
