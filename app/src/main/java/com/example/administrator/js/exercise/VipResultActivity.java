package com.example.administrator.js.exercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.appbaselib.base.BaseRefreshActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.base.MyBaseRefreshActivity;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.VipResultAdapter;
import com.example.administrator.js.exercise.model.Main;

public class VipResultActivity extends MyBaseRefreshActivity<Main> {

    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("会员成果");
        setLoadMoreListener();
    }

    @Override
    public void initAdapter() {
        mAdapter = new VipResultAdapter(R.layout.item_main_vip_result, mList);
    }


    @Override
    public void requestData() {
        Http.getDefault().getMain(1, pageNo, pageSize)
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