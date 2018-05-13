package com.example.administrator.js.exercise.adapter;

import android.widget.ImageView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NearbyVipAdapter extends BaseRecyclerViewAdapter<User> {
    public NearbyVipAdapter(int layoutResId, List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

        ImageLoader.load(mContext, item.img, (CircleImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_name,item.nickname);
        helper.setText(R.id.tv_skill,item.skillname);
        helper.setText(R.id.tv_time_juli,item.distancefmt);

    }
}
