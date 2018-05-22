package com.example.administrator.js.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.base.BaseRefreshFragment;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by tangming on 2018/5/17.
 */
@Route(path = "/course/CourseUserFragment")

public class CourseUserFragment extends BaseRefreshFragment<User>{


    @BindView(R.id.et_search)
    TextView mTvSearch;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_user;
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void requestData() {

    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }


    @OnClick(R.id.et_search)
    public void onViewClicked() {
    }
}
