package com.example.administrator.js.course;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseFragment;
import com.example.administrator.js.R;

/**
 * Created by tangming on 2018/5/17.
 */
@Route(path = "/course/CourseUserFragment")

public class CourseUserFragment extends BaseFragment {



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_user;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
