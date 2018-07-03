package com.example.administrator.js.vipandtrainer.adapter;

import android.widget.TextView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

public class CourseTypeAdapter extends BaseRecyclerViewAdapter<CourseType> {

    public boolean isSingleChoose = false;

    public CourseTypeAdapter(int layoutResId, List<CourseType> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseType item) {
        helper.setText(R.id.tv_name, item.getName());

        TextView mTextView = helper.getView(R.id.tv_name);

        int p = helper.getAdapterPosition();
        if (isSingleChoose) {
            if (mSinglePosition == p) {
                mTextView.setSelected(true);

            } else {
                mTextView.setSelected(false);

            }
        } else {
            if (isSelected(p)) {
                mTextView.setSelected(true);
            } else {
                mTextView.setSelected(false);

            }
        }


    }

    public void setNewData2(List<? extends CourseType> mList) {
        getData().clear();
        getData().addAll(mList);
        notifyDataSetChanged();
    }
}
