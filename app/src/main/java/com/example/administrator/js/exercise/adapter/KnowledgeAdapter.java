package com.example.administrator.js.exercise.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.utils.ScreenUtils;
import com.appbaselib.utils.SystemUtils;
import com.appbaselib.view.RatioImageView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.App;
import com.example.administrator.js.R;
import com.example.administrator.js.exercise.model.Main;

import java.util.List;

public class KnowledgeAdapter extends BaseRecyclerViewAdapter<Main> {
    public KnowledgeAdapter(int layoutResId, List<Main> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Main item) {
        ImageLoader.load(mContext, item.image, (RatioImageView) helper.getView(R.id.image));
        helper.setText(R.id.tv_title, item.title);
    }

    public static class KnowledgeDividerItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

//        //如果不是第一个，则设置top的值。
            if (parent.getChildAdapterPosition(view) != 0) {
                //这里直接硬编码为1px
                outRect.top = ScreenUtils.dp2px(App.mInstance, 8);
            }
        }
    }
}
