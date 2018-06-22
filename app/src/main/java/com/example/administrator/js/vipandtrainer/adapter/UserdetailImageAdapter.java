package com.example.administrator.js.vipandtrainer.adapter;

import android.widget.ImageView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

/**
 * Created by tangming on 2018/5/24.
 */

public class UserdetailImageAdapter extends BaseRecyclerViewAdapter<String> {
    public UserdetailImageAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageLoader.load(mContext, item, (ImageView) helper.getView(R.id.image));

    }
}
