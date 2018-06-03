package com.example.administrator.js.exercise.adapter;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.exercise.model.Main;

import java.util.List;

public class ExerciseSkillAdapter extends BaseRecyclerViewAdapter<Main> {
    public ExerciseSkillAdapter(int layoutResId, List<Main> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Main item) {

        ImageLoader.load(mContext, item.image, (ImageView) helper.getView(R.id.image));
        helper.setText(R.id.tv_title, item.title != null ? item.title : "");
        helper.setText(R.id.tv_content, item.summary != null ? item.summary : "");
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Main mMain=getData().get(position);
                ARouter.getInstance().build("/exercise/DetailActivity")
                        .withObject("mMain", mMain)
                        .navigation(mContext);
            }
        });
    }
}
