package com.example.administrator.js.activity.locaiton;

import com.amap.api.services.core.PoiItem;
import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

import io.reactivex.internal.operators.observable.ObservableSwitchIfEmpty;

/**
 * Created by tangming on 2018/6/27.
 */

public class AddressAdapter extends BaseRecyclerViewAdapter<PoiItem> {
    public AddressAdapter(int layoutResId, List<PoiItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tv_name, item.getTitle());
        if (mSinglePosition == helper.getAdapterPosition()) {
            helper.setVisible(R.id.image, true);
        } else {
            helper.setVisible(R.id.image, false);

        }
        helper.setText(R.id.tv_detail, item.getCityName() + item.getAdName() + item.getSnippet());
    }
}
