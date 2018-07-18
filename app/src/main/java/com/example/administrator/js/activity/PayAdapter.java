package com.example.administrator.js.activity;

import android.support.v4.content.ContextCompat;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

/**
 * Created by tangming on 2018/7/16.
 */

public class PayAdapter extends BaseRecyclerViewAdapter<String> {
    public PayAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_name, item);
        if (mSinglePosition == helper.getAdapterPosition()) {
            helper.setVisible(R.id.image, true);
        } else {
            helper.setVisible(R.id.image, false);
        }
        if (helper.getAdapterPosition() == 0) {
            helper.setImageDrawable(R.id.image_tag, ContextCompat.getDrawable(mContext, R.drawable.iconzhifubao));
        } else {
            helper.setImageDrawable(R.id.image_tag, ContextCompat.getDrawable(mContext, R.drawable.iconweixin));

        }

    }
}
