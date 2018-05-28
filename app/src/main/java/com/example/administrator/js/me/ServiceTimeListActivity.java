package com.example.administrator.js.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.DialogUtils;
import com.appbaselib.utils.PreferenceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.MyBaseRefreshActivity;
import com.example.administrator.js.login.LoginActivity;
import com.example.administrator.js.me.adapter.ServiceTimeAdapter;
import com.example.administrator.js.me.model.ServiceTime;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;

import io.reactivex.functions.Consumer;

public class ServiceTimeListActivity extends MyBaseRefreshActivity<ServiceTime> {

    MenuItem mMenuItem;

    @Override
    protected void initView() {
        super.initView();

        mToolbar.setTitle("服务时间");
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("新增");

        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                if (mMenuItem.getItemId() == R.id.btn_common) {
                    start(AddServicetimeActivity.class);

                }
                return true;
            }
        });
    }

    @Override
    public void initAdapter() {

        mAdapter = new ServiceTimeAdapter(R.layout.item_service_time, mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                //编辑
                ARouter.getInstance().build("/me/AddServicetimeActivity")
                        .withObject("mServiceTime", mList.get(position))
                        .navigation(mContext);


            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {

                DialogUtils.getDefaultDialog(mContext, "提示", "确定删除吗？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialogInterface, int mI) {

                        ServiceTime mServiceTime = (ServiceTime) mList.get(position);
                        delete(mServiceTime.id,position);

                    }
                }).show();
                return false;
            }
        });
    }

    private void delete(String id, final int p) {

        Http.getDefault().servicetimeDelete(id)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {
                        mAdapter.remove(p);
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirstReresh) {
            refreshData(false);
        }
    }

    @Override
    public void requestData() {

        Http.getDefault().servicetimeList(UserManager.getInsatance().getUser().id)
                .as(RxHelper.<List<ServiceTime>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<List<ServiceTime>>() {
                    @Override
                    protected void onSucess(List<ServiceTime> mServiceTimes) {

                        loadComplete(mServiceTimes);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);

                    }
                });

    }


}
