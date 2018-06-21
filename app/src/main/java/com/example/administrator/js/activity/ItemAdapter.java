package com.example.administrator.js.activity;

import android.widget.TextView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

/**
 * Created by tangming on 2018/6/21.
 */

public class ItemAdapter extends BaseRecyclerViewAdapter<String> {
    public ItemAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_name, item);
        TextView mTextView = helper.getView(R.id.tv_name);
        if (mSinglePosition == (helper.getLayoutPosition())) {
            mTextView.setSelected(true);
        } else {
            mTextView.setSelected(false);

        }

    }
}
