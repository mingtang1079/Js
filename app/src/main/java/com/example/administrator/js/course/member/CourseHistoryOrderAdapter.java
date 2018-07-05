package com.example.administrator.js.course.member;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tangming on 2018/6/25.
 */

class CourseHistoryOrderAdapter extends BaseRecyclerViewAdapter<HistoryOrder> {

    public CourseHistoryOrderAdapter(int layoutResId, List<HistoryOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryOrder item) {

        helper.setText(R.id.tv_type, item.ctypename);

        if (!TextUtils.isEmpty(item.img)) {
            ImageLoader.load(mContext, item.img, (CircleImageView) helper.getView(R.id.iv_head));
        }
        helper.setText(R.id.tv_name, item.nickname);
        helper.setText(R.id.tv_id, "ID:" + item.no);


        helper.setText(R.id.tv_shengyukeshi, "(" + item.cuse + "/" + item.csum + ")");
        LinearLayout mLinearLayout = helper.getView(R.id.content);
        if (item.orderlist != null) {
            mLinearLayout.removeAllViews();
            for (int i = 0; i < item.orderlist.size(); i++) {
                HistoryOrder.OrderList mOrderList = item.orderlist.get(i);
                View mView = LayoutInflater.from(mContext).inflate(R.layout.item_course_history_order_content, mLinearLayout, false);
                TextView mTextViewTime = mView.findViewById(R.id.tv_time);
                TextView mTextViewKeshi = mView.findViewById(R.id.tv_shengyukeshi);
                TextView mTextViewPrice = mView.findViewById(R.id.tv_price);
                mTextViewTime.setText(mOrderList.createDate);
                mTextViewKeshi.setText("("+mOrderList.cuse + "/" + mOrderList.csum + ")");
                mTextViewPrice.setText(mOrderList.cprice + "元");
                mLinearLayout.addView(mView);
            }
        }

        helper.addOnClickListener(R.id.tv_yuyue);
        helper.addOnClickListener(R.id.tv_xuke);

    }
}
