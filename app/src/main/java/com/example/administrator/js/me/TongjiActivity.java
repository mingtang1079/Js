package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.MyBaseRefreshActivity;
import com.example.administrator.js.me.adapter.TongjiAdapter;
import com.example.administrator.js.me.model.Tongji;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TongjiActivity extends MyBaseRefreshActivity<Tongji> {


    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("统计");
    }

    @Override
    public void initAdapter() {
        mAdapter = new TongjiAdapter(R.layout.item_tongji, mList);
    }

    @Override
    public void requestData() {

        Http.getDefault().tongji(UserManager.getInsatance().getUser().id)
                .as(RxHelper.<List<Tongji>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<List<Tongji>>() {
                    @Override
                    protected void onSucess(List<Tongji> mTongjis) {
                        loadComplete(mTongjis);
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });

    }
}
