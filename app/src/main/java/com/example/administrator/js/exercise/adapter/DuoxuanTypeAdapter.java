package com.example.administrator.js.exercise.adapter;

import android.widget.TextView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

public class DuoxuanTypeAdapter extends BaseRecyclerViewAdapter<String> {
    public DuoxuanTypeAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_name, item);
        TextView mTextView = helper.getView(R.id.tv_name);
        if (isSelected(helper.getLayoutPosition())) {
            mTextView.setSelected(true);
        } else {
            mTextView.setSelected(false);

        }

    }
}
