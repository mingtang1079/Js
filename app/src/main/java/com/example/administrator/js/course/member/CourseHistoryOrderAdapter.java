package com.example.administrator.js.course.member;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by tangming on 2018/6/25.
 */

class CourseHistoryOrderAdapter extends BaseRecyclerViewAdapter<HistoryOrder> {

    public CourseHistoryOrderAdapter(int layoutResId, List<HistoryOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryOrder item) {

    }
}
