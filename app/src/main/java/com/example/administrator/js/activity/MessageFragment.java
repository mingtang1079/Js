package com.example.administrator.js.activity;

import android.view.View;

import com.appbaselib.base.BaseFragment;
import com.appbaselib.base.BaseRefreshFragment;
import com.example.administrator.js.R;

public class MessageFragment extends BaseRefreshFragment<UserMessage>{



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void initAdapter() {

    }

    @Override
    public void requestData() {

    }

}
