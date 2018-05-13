package com.example.administrator.js.exercise.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.utils.ScreenUtils;
import com.appbaselib.view.RatioImageView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.App;
import com.example.administrator.js.R;
import com.example.administrator.js.exercise.model.Main;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VipResultAdapter extends BaseRecyclerViewAdapter<Main> {
    public VipResultAdapter(int layoutResId, List<Main> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Main item) {
        ImageLoader.load(mContext, item.image, (ImageView) helper.getView(R.id.image));
        ImageLoader.load(mContext, item.userimg, (CircleImageView) helper.getView(R.id.iv_head));
        helper.setText(R.id.tv_title, item.title!=null?item.title:"");
        helper.setText(R.id.tv_name, item.nickname!=null?item.nickname:"");

    }

    public static class VipgeDividerItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

//        //如果不是第一个，则设置top的值。
            if (parent.getChildAdapterPosition(view) != 0) {
                //这里直接硬编码为1px
                outRect.left = ScreenUtils.dp2px(App.mInstance, 8);
            }
        }
    }
}
