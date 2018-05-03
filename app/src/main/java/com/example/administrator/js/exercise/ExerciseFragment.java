package com.example.administrator.js.exercise;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appbaselib.base.BaseFragment;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tangming on 2018/5/3.
 */

public class ExerciseFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_exercise;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("健身");
      //  mToolbar.setTitleTextColor(ContextCompat.getColor(R.color.));
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


}
