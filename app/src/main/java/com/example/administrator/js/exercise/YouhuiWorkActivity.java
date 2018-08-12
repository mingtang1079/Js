package com.example.administrator.js.exercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.MyBaseRefreshActivity;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.YouhuiWordAdapter;
import com.example.administrator.js.exercise.model.Main;

public class YouhuiWorkActivity extends MyBaseRefreshActivity<Main> {

    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("优惠活动");
        setLoadMoreListener();
    }


    @Override
    public void initAdapter() {
        mAdapter = new YouhuiWordAdapter(R.layout.item_youhui, mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build("/exercise/DetailActivity")
                        .withObject("mMain", mList.get(position))
                        .navigation(mContext);
            }
        });
    }

    @Override
    public void requestData() {
        Http.getDefault().getMain(UserManager.getInsatance().getUser().id,1, pageNo, pageSize)
                .as(RxHelper.<WrapperModel<Main>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<Main>>() {
                    @Override
                    protected void onSucess(WrapperModel<Main> mMainWrapperModel) {

                        if (mMainWrapperModel != null && mMainWrapperModel.list != null) {
                            loadComplete(mMainWrapperModel.list);
                        }
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });
    }
}
