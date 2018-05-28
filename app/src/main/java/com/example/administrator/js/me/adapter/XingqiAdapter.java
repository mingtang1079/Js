package com.example.administrator.js.me.adapter;

import android.view.View;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.Xingqi;

import java.util.List;

/**
 * Created by tangming on 2018/5/28.
 */

public class XingqiAdapter extends BaseRecyclerViewAdapter<Xingqi> {
    public XingqiAdapter(int layoutResId, List<Xingqi> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Xingqi item) {
        helper.setText(R.id.name,item.name);
        int p=mData.indexOf(item);
        if (isSelected(p))
        {
            helper.setVisible(R.id.iv_image,true);
        }
        else {
            helper.setVisible(R.id.iv_image,false);

        }

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switchSelectedState(position);
            }
        });
    }
}
