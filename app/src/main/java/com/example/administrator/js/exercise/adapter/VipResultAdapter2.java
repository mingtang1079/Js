package com.example.administrator.js.exercise.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.utils.ScreenUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.App;
import com.example.administrator.js.R;
import com.example.administrator.js.exercise.model.Main;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VipResultAdapter2 extends BaseRecyclerViewAdapter<Main> {
    public VipResultAdapter2(int layoutResId, List<Main> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Main item) {
        ImageLoader.load(mContext, item.image, (ImageView) helper.getView(R.id.image));
        ImageLoader.load(mContext, item.userimg, (CircleImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_title, item.title!=null?item.title:"");
        helper.setText(R.id.tv_name, item.nickname!=null?item.nickname:"");
        helper.setText(R.id.tv_summary,item.summary);
        helper.setText(R.id.tv_time,item.updateDate);

    }

}
