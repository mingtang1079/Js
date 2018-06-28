package com.example.administrator.js.exercise.member;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseRefreshFragment;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.User;

/**
 * Created by tangming on 2018/6/22.
 */
@Route(path = "/trainer/NearByTrainerFragment")
public class NearByTrainerFragment extends BaseRefreshFragment<User> {

    @Override
    protected void initView() {
        super.initView();
        toggleShowLoading(true, "加载中……");
        requestData();

    }
    @Override
    public void initAdapter() {
        mAdapter = new NearbyTrainerAdapter(R.layout.item_nearby_trainer, mList);
        setLoadMoreListener();

    }

    @Override
    public void requestData() { 

        // TODO: 2018/6/22

    }
}
