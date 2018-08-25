package com.example.administrator.js.me.member;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.example.administrator.js.R;

import org.w3c.dom.Text;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = "/member/TuikeDetailActivity")
public class TuikeDetailActivity extends BaseActivity {

    @Autowired
    MyOrder mMyOrder;
    @Autowired
    boolean isFirst;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tv_tuikuan_price)
    TextView mTvTuikuanPrice;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_course_type)
    TextView mTvCourseType;
    @BindView(R.id.tv_reason)
    TextView mTvReason;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.tv_step_one)
    TextView mTvStepOne;
    @BindView(R.id.tv_step_two)
    TextView mTvStepTwo;
    @BindView(R.id.tv_step_three)
    TextView mTvStepThree;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_tips)
    TextView mTextViewTips;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tuike_detail;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("退款详情");

        setData(mMyOrder);
        if (isFirst)
            mTextViewTips.setVisibility(View.VISIBLE);
        else
            mTextViewTips.setVisibility(View.GONE);
    }

    private void setData(MyOrder mOrder) {
        ImageLoader.load(mContext, mOrder.img, mIvHead);
        mTvName.setText(mOrder.nickname);
        mTvId.setText("ID：" + mOrder.no + "");
        //年龄
        if (mOrder.age != null && mOrder.sex != null) {
            mTvAge.setText(" "+mOrder.age);
            if (mOrder.sex.equals("1")) {
                //男性
                mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvAge.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvAge.setCompoundDrawables(drawable, null, null, null);
                mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));
            }
        } else {
            mTvAge.setVisibility(View.GONE);
        }
        mTvReason.setText("退款原因：" + mOrder.refundtype);
        mTvTime.setText("申请时间：" + mOrder.updateDate);
        mTvNumber.setText("订单号：" + mOrder.payno + "");

        BigDecimal mBigDecimal=new BigDecimal(mOrder.refundmoney);
        mTvTuikuanPrice.setText(mBigDecimal.divide(new BigDecimal(100)).doubleValue()+"元");

    }
}
