package com.example.administrator.js.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.appbaselib.base.BaseRefreshActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.MyBaseRefreshActivity;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.model.Main;
import com.example.administrator.js.me.adapter.CollectionAdapter;
import com.example.administrator.js.me.model.Collection;

public class CollectionActivity extends MyBaseRefreshActivity<Main> {


    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("我的收藏");
    }

    @Override
    public void initAdapter() {
        mAdapter = new CollectionAdapter(R.layout.item_collection, mList);
        setLoadMoreListener();
    }

    @Override
    public void requestData() {

        Http.getDefault().getCollection(UserManager.getInsatance().getUser().id, pageNo)
                .as(RxHelper.<WrapperModel<Main>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<Main>>() {
                    @Override
                    protected void onSucess(WrapperModel<Main> mCollectionWrapperModel) {
                        loadComplete(mCollectionWrapperModel.list);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });

    }
}
