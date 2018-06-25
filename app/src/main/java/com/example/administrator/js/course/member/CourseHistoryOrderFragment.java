package com.example.administrator.js.course.member;

import com.appbaselib.base.BaseRefreshFragment;
import com.example.administrator.js.R;

/**
 * Created by tangming on 2018/6/25.
 */

public class CourseHistoryOrderFragment extends BaseRefreshFragment<HistoryOrder> {


    @Override
    public void initAdapter() {
        mAdapter=new CourseHistoryOrderAdapter(R.layout.item_course_history_order,mList);
    }

    @Override
    public void requestData() {

    }
}
