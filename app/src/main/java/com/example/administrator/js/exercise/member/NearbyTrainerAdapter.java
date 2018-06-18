package com.example.administrator.js.exercise.member;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.me.model.User;

import java.util.List;

public class NearbyTrainerAdapter extends BaseRecyclerViewAdapter<User> {

    public NearbyTrainerAdapter(int layoutResId, List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

    }
}
