package com.example.administrator.js.vipandtrainer.adapter;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.vipandtrainer.trainer.Workdate;

import java.util.List;

/**
 * Created by tangming on 2018/7/2.
 */

public class WorkDateAdapter extends BaseRecyclerViewAdapter<String> {

    public WorkDateAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_name, item);
    }
}
