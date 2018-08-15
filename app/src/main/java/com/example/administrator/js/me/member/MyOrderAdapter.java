package com.example.administrator.js.me.member;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.appbaselib.common.ImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tangming on 2018/6/26.
 */
class MyOrderAdapter extends BaseRecyclerViewAdapter<MyOrder> {
    public MyOrderAdapter(int layoutResId, List<MyOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrder item) {

        if (!TextUtils.isEmpty(item.img)) {
            ImageLoader.load(mContext, item.img, (CircleImageView) helper.getView(R.id.iv_head));
        }
        helper.setText(R.id.tv_name, item.nickname);
        helper.setText(R.id.tv_id, "ID:" + item.no);
        //年龄
        if (item.sex != null) {
            TextView mTextView = helper.getView(R.id.tv_age);
            helper.setVisible(R.id.tv_age, true);
            if (item.age != null) {
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

        //单价
        helper.setText(R.id.tv_number, "订单号：" + item.payno);
        helper.setText(R.id.tv_danjia, "￥ " + item.cprice / 100 + "");
        helper.setText(R.id.tv_course_count, "共" + item.csum + "节课  合计");
        helper.setText(R.id.tv_count, "x" + item.csum);
        helper.setText(R.id.tv_course_type, item.ctypename + "(" + item.coursetypenames + ")");
        helper.setText(R.id.tv_count, "x" + item.csum);
        BigDecimal a;
        BigDecimal b;
        a = new BigDecimal(item.crealprice);
        b = new BigDecimal(100);
        helper.setText(R.id.tv_all_price, "￥ " + a.divide(b, 2, RoundingMode.HALF_UP).toString());

        TextView mTextViewCancel = helper.getView(R.id.tv_cancel);
        TextView mTextViewPay = helper.getView(R.id.tv_pay);
        TextView mTextViewTuikuan = helper.getView(R.id.tv_tuikuan);
        TextView mTextViewQuxiaoTuikuan = helper.getView(R.id.tv_quxiao_tuikuan);


        if ("a1".equals(item.status)) {
            helper.setText(R.id.tv_status, "待接单");
            mTextViewCancel.setVisibility(View.VISIBLE);
            mTextViewPay.setVisibility(View.GONE);
            mTextViewTuikuan.setVisibility(View.GONE);
            mTextViewQuxiaoTuikuan.setVisibility(View.GONE);
        } else if ("b2".equals(item.status)) {
            helper.setText(R.id.tv_status, "待付款");
            mTextViewCancel.setVisibility(View.VISIBLE);
            mTextViewPay.setVisibility(View.VISIBLE);
            mTextViewTuikuan.setVisibility(View.GONE);
            mTextViewQuxiaoTuikuan.setVisibility(View.GONE);


        } else if ("b3".equals(item.status)) {
            helper.setText(R.id.tv_status, "已完成");
            mTextViewCancel.setVisibility(View.GONE);
            mTextViewPay.setVisibility(View.GONE);
            if (!"体验课".equals(item.ctypename)&&(item.cuse!=item.csum)) {
                mTextViewTuikuan.setVisibility(View.VISIBLE);
            } else {
                mTextViewTuikuan.setVisibility(View.GONE);

            }
            mTextViewQuxiaoTuikuan.setVisibility(View.GONE);


        } else if ("b55".equals(item.status)) {
            helper.setText(R.id.tv_status, "退款中");
            mTextViewCancel.setVisibility(View.GONE);
            mTextViewPay.setVisibility(View.GONE);
            mTextViewTuikuan.setVisibility(View.GONE);
            mTextViewQuxiaoTuikuan.setVisibility(View.VISIBLE);


        } else if ("b56".equals(item.status)) {
            helper.setText(R.id.tv_status, "已退款");
            mTextViewCancel.setVisibility(View.GONE);
            mTextViewPay.setVisibility(View.GONE);
            mTextViewTuikuan.setVisibility(View.GONE);
            mTextViewQuxiaoTuikuan.setVisibility(View.GONE);
        } else {
            mTextViewCancel.setVisibility(View.GONE);
            mTextViewPay.setVisibility(View.GONE);
            mTextViewTuikuan.setVisibility(View.GONE);
            mTextViewQuxiaoTuikuan.setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.tv_cancel);
        helper.addOnClickListener(R.id.tv_quxiao_tuikuan);
        helper.addOnClickListener(R.id.tv_pay);
        helper.addOnClickListener(R.id.tv_tuikuan);

    }
}
