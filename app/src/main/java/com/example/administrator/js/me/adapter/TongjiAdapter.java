package com.example.administrator.js.me.adapter;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.Tongji;

import java.util.List;

public class TongjiAdapter  extends BaseRecyclerViewAdapter<Tongji>{
    public TongjiAdapter(int layoutResId, List<Tongji> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tongji item) {

        helper.setText(R.id.title,item.title);
        helper.setText(R.id.value,item.value);
        helper.setText(R.id.tv_remark,item.remark);
    }
}
