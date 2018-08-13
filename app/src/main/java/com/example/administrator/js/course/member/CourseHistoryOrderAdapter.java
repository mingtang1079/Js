package com.example.administrator.js.course.member;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.example.administrator.js.utils.TimeUtils;

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
    protected void convert(BaseViewHolder helper, final HistoryOrder item) {

        helper.setText(R.id.tv_type, item.ctypename);

        if ("体验课".equals(item.ctypename))
        {
            helper.setVisible(R.id.tv_xuke,false);
        }
        else {
            helper.setVisible(R.id.tv_xuke,true);

        }
        if (!TextUtils.isEmpty(item.img)) {
            ImageLoader.load(mContext, item.img, (CircleImageView) helper.getView(R.id.iv_head));
        }
        helper.setText(R.id.tv_name, item.nickname);
        helper.setText(R.id.tv_id, "ID:" + item.no);
        //年龄
        if ( item.sex!= null) {
            TextView mTextView = helper.getView(R.id.tv_age);
            helper.setVisible(R.id.tv_age, true);
            if (item.age!=null) {
                helper.setText(R.id.tv_age, item.age + "");
            }
            if (item.sex.equals("1")) {
                //男性
                mTextView.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));

                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTextView.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTextView.setCompoundDrawables(drawable, null, null, null);

                mTextView.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));

            }
        } else {
            helper.setVisible(R.id.tv_age, false);

        }

        helper.setText(R.id.tv_shengyukeshi, "(" + item.cuse + "/" + item.csum + ")");
        LinearLayout mLinearLayout = helper.getView(R.id.content);
        if (item.orderlist != null) {
            mLinearLayout.removeAllViews();
            for (int i = 0; i < item.orderlist.size(); i++) {
                final HistoryOrder.OrderList mOrderList = item.orderlist.get(i);
                View mView = LayoutInflater.from(mContext).inflate(R.layout.item_course_history_order_content, mLinearLayout, false);
                TextView mTextViewTime = mView.findViewById(R.id.tv_time);
                TextView mTextViewKeshi = mView.findViewById(R.id.tv_shengyukeshi);
                TextView mTextViewPrice = mView.findViewById(R.id.tv_price);
                mTextViewTime.setText(TimeUtils.getTime(mOrderList.createDate));
                mTextViewKeshi.setText("(" + mOrderList.cuse + "/" + mOrderList.csum + ")");
                mTextViewPrice.setText(mOrderList.cprice + "元");
                mLinearLayout.addView(mView);
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View mView) {
                        ARouter.getInstance().build("/me/member/OrderDetailActivity")
                                .withString("id",mOrderList.id)
                                .navigation(mContext);
                    }
                });
            }
        }

        if (item.cuse == item.csum) {
            helper.setVisible(R.id.tv_yuyue, false);
        } else {
            helper.setVisible(R.id.tv_yuyue, true);
        }
        helper.addOnClickListener(R.id.tv_yuyue);
        helper.addOnClickListener(R.id.tv_xuke);

    }
}
