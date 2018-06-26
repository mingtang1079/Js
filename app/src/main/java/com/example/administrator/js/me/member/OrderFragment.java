package com.example.administrator.js.me.member;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseRefreshFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.R;

/**
 * Created by tangming on 2018/6/26.
 */
@Route(path = "/me/member/OrderFragment")
public class OrderFragment extends BaseRefreshFragment<MyOrder> {

    @Override
    protected void initView() {
        super.initView();
        toggleShowLoading(true);
        requestData();
    }

    @Override
    public void initAdapter() {
        mAdapter = new MyOrderAdapter(R.layout.item_my_order, mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                ARouter.getInstance().build("/me/member/OrderDetailActivity")
                        .withObject("mOrder", mList.get(position))
                        .navigation(mContext);

            }
        });
    }

    @Override
    public void requestData() {


    }
}
