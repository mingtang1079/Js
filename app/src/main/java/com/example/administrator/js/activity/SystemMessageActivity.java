package com.example.administrator.js.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.MyBaseRefreshActivity;
import com.example.administrator.js.base.model.WrapperModel;

@Route(path = "/activity/SystemMessageActivity")
public class SystemMessageActivity extends MyBaseRefreshActivity<SystemMessage> {


    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("系统消息");
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SystemMessage mSystemMessage = (SystemMessage) mList.get(position);
                ARouter.getInstance().build("/web/Html5Activity")
                        .withString("url", mSystemMessage.url)
                        .navigation(mContext);

                Http.getDefault().readmsg(mSystemMessage.id);
                mSystemMessage.status="1";
                mAdapter.notifyItemChanged(position);
            }
        });
        setLoadMoreListener();
    }

    @Override
    public void initAdapter() {
        mAdapter = new SystemMessageAdapter(R.layout.item_system_message, mList);
    }

    @Override
    public void requestData() {

        Http.getDefault().msgList(UserManager.getInsatance().getUser().id, pageNo)
                .as(RxHelper.<WrapperModel<SystemMessage>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<SystemMessage>>() {
                    @Override
                    protected void onSucess(WrapperModel<SystemMessage> mSystemMessageWrapperModel) {
                        if (mSystemMessageWrapperModel != null) {
                            loadComplete(mSystemMessageWrapperModel.list);
                        }
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });
    }
}
