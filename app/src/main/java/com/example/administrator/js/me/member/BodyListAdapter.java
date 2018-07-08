package com.example.administrator.js.me.member;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

public class BodyListAdapter extends BaseRecyclerViewAdapter<BodyData> {
    public BodyListAdapter(int layoutResId, List<BodyData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BodyData item) {

        helper.setText(R.id.tv_name,item.writedate);
    }
}
