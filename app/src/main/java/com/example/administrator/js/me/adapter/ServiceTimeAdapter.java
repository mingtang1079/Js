package com.example.administrator.js.me.adapter;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.ServiceTime;

import java.util.List;

/**
 * Created by tangming on 2018/5/28.
 */

public class ServiceTimeAdapter extends BaseRecyclerViewAdapter<ServiceTime> {
    public ServiceTimeAdapter(int layoutResId, List<ServiceTime> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceTime item) {

        helper.setText(R.id.time, item.starttime + " - " + item.endtime);
        StringBuilder mStringBuilder = new StringBuilder();

        if ("1".equals(item.day1))
            mStringBuilder.append("星期一,");
        if ("1".equals(item.day2))
            mStringBuilder.append("星期二,");
        if ("1".equals(item.day3))
            mStringBuilder.append("星期三,");
        if ("1".equals(item.day4))
            mStringBuilder.append("星期四,");
        if ("1".equals(item.day5))
            mStringBuilder.append("星期五,");
        if ("1".equals(item.day6))
            mStringBuilder.append("星期六,");
        if ("1".equals(item.day7))
            mStringBuilder.append("星期日");

        helper.setText(R.id.tv_week, item.day1);

    }
}
