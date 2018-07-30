package com.example.administrator.js.activity;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appbaselib.utils.ScreenUtils;
import com.example.administrator.js.App;


/**
 * Created by tangming on 2018/6/19.
 */

public class ItemTopDividerItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

//        //如果不是第一个，则设置top的值。
        if (parent.getChildAdapterPosition(view) != 0) {
            //这里直接硬编码为1px
            outRect.top = ScreenUtils.dp2px(App.mInstance, 12);
        }
        outRect.left = ScreenUtils.dp2px(App.mInstance, 8);
        outRect.right = ScreenUtils.dp2px(App.mInstance, 8);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

    }
}
