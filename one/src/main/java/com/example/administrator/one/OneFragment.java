package com.example.administrator.one;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseFragment;

import com.appbaselib.base.BaseFragment;

@Route(path = "/one/OneFragment")
public class OneFragment extends BaseFragment {
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_one;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }
}
