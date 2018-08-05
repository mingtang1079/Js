package com.example.administrator.js.activity;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

public class SystemMessageAdapter extends BaseRecyclerViewAdapter<SystemMessage> {
    public SystemMessageAdapter(int layoutResId, List<SystemMessage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemMessage item) {
        helper.setText(R.id.tv_name, item.title);
        helper.setText(R.id.tv_time, item.timefmt);
        View mView=helper.getView(R.id.view_tag);

        if ("0".equals(item.status)) {
            mView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.circle_calender));
            mView.setVisibility(View.VISIBLE);
        } else {
            mView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.circle_gray));
            mView.setVisibility(View.INVISIBLE);
        }
    }
}
