package com.example.administrator.js.me.member;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by tangming on 2018/6/26.
 */

class MyOrderAdapter extends BaseRecyclerViewAdapter<MyOrder> {
    public MyOrderAdapter(int layoutResId, List<MyOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrder item) {

    }
}
