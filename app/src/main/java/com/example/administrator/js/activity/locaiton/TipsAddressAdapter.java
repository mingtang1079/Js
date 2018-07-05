package com.example.administrator.js.activity.locaiton;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Tip;
import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

/**
 * Created by tangming on 2018/6/27.
 */
@Deprecated
public class TipsAddressAdapter extends BaseRecyclerViewAdapter<Tip> {
    public TipsAddressAdapter(int layoutResId, List<Tip> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tip item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_detail, item.getDistrict() + item.getAddress());
    }
}
