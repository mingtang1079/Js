package com.example.administrator.js.exercise;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appbaselib.base.BaseFragment;
import com.example.administrator.js.R;
import com.example.administrator.js.activity.MessageActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tangming on 2018/5/3.
 */

public class ExerciseFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_exercise;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("健身");
      //  mToolbar.setTitleTextColor(ContextCompat.getColor(R.color.));
        mToolbar.inflateMenu(R.menu.main_exercise);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                start(MessageActivity.class);

                return false;
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


}
