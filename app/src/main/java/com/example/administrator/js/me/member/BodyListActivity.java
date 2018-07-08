package com.example.administrator.js.me.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseRefreshActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.constant.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class BodyListActivity extends BaseRefreshActivity<BodyData> {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_body_list;
    }

    @Override
    public void initAdapter() {
        mAdapter = new BodyListAdapter(R.layout.item_body, mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                ARouter.getInstance().build("/member/AddBodyDataActivity")
                        .withObject("mBodyData", mList.get(position))
                        .navigation(mContext);
            }
        });
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void requestData() {

        Http.getDefault().getBodyList(UserManager.getInsatance().getUser().id, pageNo)
                .as(RxHelper.<WrapperModel<BodyData>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<BodyData>>() {
                    @Override
                    protected void onSucess(WrapperModel<BodyData> mBodyDataWrapperModel) {
                        if (mBodyDataWrapperModel != null)
                            loadComplete(mBodyDataWrapperModel.list);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });
    }

    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("身体数据记录");
        toggleShowLoading(true);
        requestData();
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatusChange(EventMessage.BodyDataListChange mListStatusChange) {
       refreshData(false);
    }
}
