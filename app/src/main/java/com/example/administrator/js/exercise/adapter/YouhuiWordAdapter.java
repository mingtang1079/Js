package com.example.administrator.js.exercise.adapter;

import android.widget.ImageView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.view.RatioImageView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.exercise.model.Main;

import java.util.List;

public class YouhuiWordAdapter extends BaseRecyclerViewAdapter<Main> {
    public YouhuiWordAdapter(int layoutResId, List<Main> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Main item) {

        ImageLoader.load(mContext,item.image, (RatioImageView) helper.getView(R.id.iv_image));
    }
}
