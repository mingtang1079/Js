package com.example.administrator.js.activity;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

public class SystemMessageAdapter extends BaseRecyclerViewAdapter<SystemMessage> {
    public SystemMessageAdapter(int layoutResId, List<SystemMessage> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, SystemMessage item) {
        helper.setText(R.id.tv_name, item.title);
        helper.setText(R.id.tv_time, item.timefmt);
    }
}
