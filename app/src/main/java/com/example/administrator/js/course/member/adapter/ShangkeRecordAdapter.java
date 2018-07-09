package com.example.administrator.js.course.member.adapter;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.course.CourseModel;

import java.util.List;

public class ShangkeRecordAdapter extends BaseRecyclerViewAdapter<CourseModel> {
    public ShangkeRecordAdapter(int layoutResId, List<CourseModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseModel item) {
        helper.setText(R.id.tv_name,item.starttime);
    }
}
