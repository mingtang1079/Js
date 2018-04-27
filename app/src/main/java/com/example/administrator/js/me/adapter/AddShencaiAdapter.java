package com.example.administrator.js.me.adapter;

import android.widget.ImageView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.view.RatioImageView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

public class AddShencaiAdapter extends BaseRecyclerViewAdapter<String> {

    public AddShencaiAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageLoader.load(mContext, item, (RatioImageView) helper.getView(R.id.iv_image));
    }
}
