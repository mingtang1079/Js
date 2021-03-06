package com.example.administrator.js.me.member;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.DialogUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.utils.BigBigDecimalUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = "/me/member/OrderDetailActivity")
public class OrderDetailActivity extends BaseActivity {

    @Autowired
    String id;

    MyOrder mOrder;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_course_type)
    TextView mTvCourseType;
    @BindView(R.id.tv_tuike)
    TextView mTvTuike;
    @BindView(R.id.tv_all_price)
    TextView mTvAllPrice;
    @BindView(R.id.tv_youhui_price)
    TextView mTvYouhuiPrice;
    @BindView(R.id.tv_shiji_price)
    TextView mTvShijiPrice;
    @BindView(R.id.tv_order_number)
    TextView mTvOrderNumber;
    @BindView(R.id.tv_order_way)
    TextView mTvOrderWay;
    @BindView(R.id.tv_order_chuangjian_time)
    TextView mTvOrderChuangjianTime;
    @BindView(R.id.tv_order_chengjiao_time)
    TextView mTvOrderChengjiaoTime;
    @BindView(R.id.tv_order_status)
    TextView mTvOrderStatus;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_quxiao_tuikuan)
    TextView mTvQuxiaoTuikuan;
    @BindView(R.id.tv_pay)
    TextView mTvPay;
    @BindView(R.id.tv_tuikuan)
    TextView mTvTuikuan;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_shengyukeshi)
    TextView mTextViewShengyekeshi;
    @BindView(R.id.content)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.tv_record)
    TextView mTextViewRecord;

    @BindView(R.id.ll_tuike_content)
    LinearLayout mLinearLayoutTuike;
    @BindView(R.id.tv_tuike_count)
    TextView mTextViewTuikeCount;
    @BindView(R.id.tv_tuikuan_price)
    TextView mTextViewTuikePrice;

    @BindView(R.id.ll_pay_way)
    LinearLayout mLinearLayoutPayWay;
    @BindView(R.id.ll_time)
    LinearLayout mLinearLayoutTime;

    @BindView(R.id.ll_button)
    LinearLayout mLinearLayoutLlbutton;//底部

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected View getLoadingTargetView() {
        return mNestedScrollView;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("订单详情");
        toggleShowLoading(true);
        requestData();

        mTextViewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                ARouter.getInstance().build("/member/ShangkeRecordActivity")
                        .withString("orderId", mOrder.id)
                        .navigation(mContext);
            }
        });
    }

    @Override
    protected void requestData() {
        super.requestData();

        Http.getDefault().getOrderDetail(UserManager.getInsatance().getUser().id, id)
                .as(RxHelper.<MyOrder>handleResult(mContext))
                .subscribe(new ResponceSubscriber<MyOrder>() {
                    @Override
                    protected void onSucess(MyOrder m) {

                        if (m != null) {
                            mOrder = m;
                            setData();
                        }
                        toggleShowLoading(false);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });


    }

    private void setData() {

        ImageLoader.load(mContext, mOrder.img, mIvHead);
        mTvName.setText(mOrder.nickname);
        mTvId.setText("ID：" + mOrder.no + "");
        //年龄
        if (mOrder.age != null && mOrder.sex != null) {
            mTvAge.setText(" " + mOrder.age);
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

        mTvAddress.setText(mOrder.address);
        mTvCourseType.setText(mOrder.ctypename + "(" + mOrder.coursetypenames + ")");
        mTextViewShengyekeshi.setText("已上课时 (" + mOrder.cuse + "/" + mOrder.csum + ")");
        if (mOrder.ctotalprice != null)
            mTvAllPrice.setText(BigBigDecimalUtils.divide(new BigDecimal(mOrder.ctotalprice), new BigDecimal(100)) + "元");
        if (mOrder.crealprice != null)
            mTvShijiPrice.setText(BigBigDecimalUtils.divide(new BigDecimal(mOrder.crealprice), new BigDecimal(100)) + "元");
        if (mOrder.ctotalprice != null && mOrder.crealprice != null) {
            mTvYouhuiPrice.setText("-" + BigBigDecimalUtils.divide(new BigDecimal(mOrder.ctotalprice - mOrder.crealprice), new BigDecimal(100)) + "元");
        }
        mTvOrderNumber.setText(mOrder.payno + "");

        mTvOrderChuangjianTime.setText(mOrder.createDate);
        mTvOrderChengjiaoTime.setText(mOrder.paydate);

        if ("b3".equals(mOrder.status)) {
            mTvOrderStatus.setText("待接单");

            mTvCancel.setVisibility(View.VISIBLE);
            mTvPay.setVisibility(View.GONE);
            mLinearLayoutLlbutton.setVisibility(View.VISIBLE);
            //============

            mTvTuikuan.setVisibility(View.GONE);
            mTvQuxiaoTuikuan.setVisibility(View.GONE);
            //成交时间
            mLinearLayoutTime.setVisibility(View.GONE);
            mLinearLayoutPayWay.setVisibility(View.GONE);

            mLinearLayoutTuike.setVisibility(View.GONE);
            mTextViewRecord.setVisibility(View.GONE);
        } else if ("a1".equals(mOrder.status)) {
            mTvOrderStatus.setText("待付款");

            mTvCancel.setVisibility(View.VISIBLE);
            mTvPay.setVisibility(View.VISIBLE);
            mLinearLayoutLlbutton.setVisibility(View.VISIBLE);
            //============
            mTvTuikuan.setVisibility(View.GONE);
            mTvQuxiaoTuikuan.setVisibility(View.GONE);

            mLinearLayoutTime.setVisibility(View.GONE);
            mLinearLayoutPayWay.setVisibility(View.GONE);

            mLinearLayoutTuike.setVisibility(View.GONE);
            mTextViewRecord.setVisibility(View.GONE);

        } else if ("b2".equals(mOrder.status)) {
            mTvOrderStatus.setText("已完成");
            //===========
            mTvCancel.setVisibility(View.GONE);
            mTvPay.setVisibility(View.GONE);
            mLinearLayoutLlbutton.setVisibility(View.GONE);
            //============
            //.私教课 && cuse<csum && status = 'b3'
            if (!"体验课".equals(mOrder.ctypename) && (mOrder.cuse != mOrder.csum)) {
                mTvTuikuan.setVisibility(View.VISIBLE);

            } else {
                mTvTuikuan.setVisibility(View.GONE);

            }

            mTvQuxiaoTuikuan.setVisibility(View.GONE);

            mLinearLayoutTime.setVisibility(View.VISIBLE);
            mLinearLayoutPayWay.setVisibility(View.VISIBLE);
            mTextViewRecord.setVisibility(View.VISIBLE);
            mLinearLayoutTuike.setVisibility(View.GONE);

        } else if ("b55".equals(mOrder.status)) {
            mTvOrderStatus.setText("退款中");
            //============
            mTvCancel.setVisibility(View.GONE);
            mTvPay.setVisibility(View.GONE);
            mLinearLayoutLlbutton.setVisibility(View.GONE);
            //============

            mTvTuikuan.setVisibility(View.GONE);
            mTvQuxiaoTuikuan.setVisibility(View.VISIBLE);
            mTvTuike.setVisibility(View.VISIBLE);
            mTvTuikuan.setVisibility(View.GONE);
            mTextViewRecord.setVisibility(View.VISIBLE);
            //   mTvTuikuan.setText("退课详情");
            //退款显示的内容
            mLinearLayoutTuike.setVisibility(View.VISIBLE);
            mTextViewTuikeCount.setText(mOrder.csum - mOrder.cuse + "节");
            BigDecimal mBigDecimal = new BigDecimal(mOrder.refundmoney);
            mTextViewTuikePrice.setText(mBigDecimal.divide(new BigDecimal(100)).doubleValue() + "元");
//------------------
            mLinearLayoutTime.setVisibility(View.VISIBLE);
            mLinearLayoutPayWay.setVisibility(View.VISIBLE);

        } else if ("b56".equals(mOrder.status)) {
            mTvOrderStatus.setText("已退款");

            mTvCancel.setVisibility(View.GONE);
            mTvPay.setVisibility(View.GONE);
            mLinearLayoutLlbutton.setVisibility(View.GONE);
            //============

            mTvTuikuan.setVisibility(View.GONE);
            mTvQuxiaoTuikuan.setVisibility(View.GONE);
            //退款显示的内容
            mLinearLayoutTuike.setVisibility(View.VISIBLE);
            mTextViewTuikeCount.setText(mOrder.csum - mOrder.cuse + "节");
            BigDecimal mBigDecimal = new BigDecimal(mOrder.refundmoney);
            mTextViewTuikePrice.setText(mBigDecimal.divide(new BigDecimal(100)).doubleValue() + "元");
//------------------
            mLinearLayoutTime.setVisibility(View.VISIBLE);
            mLinearLayoutPayWay.setVisibility(View.VISIBLE);
            mTextViewRecord.setVisibility(View.VISIBLE);

        } else {
            mTvCancel.setVisibility(View.GONE);

            mTvPay.setVisibility(View.GONE);
            mTvTuikuan.setVisibility(View.GONE);
            mLinearLayoutLlbutton.setVisibility(View.GONE);
            //============
            mTvQuxiaoTuikuan.setVisibility(View.GONE);
            mLinearLayoutTime.setVisibility(View.GONE);
            mLinearLayoutPayWay.setVisibility(View.GONE);
            mLinearLayoutTuike.setVisibility(View.GONE);
        }
        if ("1".equals(mOrder.paytype))
            mTvOrderWay.setText("微信支付");
        else if ("0".equals(mOrder.paytype)) {
            mTvOrderWay.setText("支付宝支付");
        } else {
            mLinearLayoutPayWay.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_tuike, R.id.tv_cancel, R.id.tv_quxiao_tuikuan, R.id.tv_pay, R.id.tv_tuikuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tuike:

                //    if ("b55".equals(mOrder.status)) {
                ARouter.getInstance().build("/member/TuikeDetailActivity")
                        .withObject("mMyOrder", mOrder)
                        .navigation(mContext);
//                } else {
//                    ARouter.getInstance().build("/member/TuikeActivity")
//                            .withObject("mMyOrder", mOrder)
//                            .navigation(mContext);
//                }
                break;
            case R.id.tv_cancel:

                DialogUtils.getDefaultDialog(mContext, "提示", "确定取消该订单吗？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialogInterface, int mI) {

                        cancel("c70");
                    }
                }).show();

                break;
            case R.id.tv_quxiao_tuikuan:

                cancel("b2");

                break;
            case R.id.tv_pay:

                ARouter.getInstance().build("/activity/PayActivity")
                        .withString("orderId", mOrder.id)
                        .withString("orderType", "0")
                        .withInt("price", mOrder.crealprice)
                        .withInt("totalPrice", mOrder.ctotalprice)
                        .withString("title", mOrder.ctypename)
                        .withTransition(R.anim.alpha_enter, 0)
                        .navigation(mContext);

                break;
            case R.id.tv_tuikuan:
                ARouter.getInstance().build("/member/TuikeActivity")
                        .withObject("mMyOrder", mOrder)
                        .navigation(mContext);

                break;
        }
    }

    private void cancel(final String status) {

        Map<String, Object> mMap = new HashMap<>();
        mMap.put("uid", UserManager.getInsatance().getUser().id);
        mMap.put("id", mOrder.id);
        mMap.put("status", status);

        Http.getDefault().applyYuyueke(mMap)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {
                        finish();
                        EventBus.getDefault().post(new EventMessage.PaySucessResult());
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatusChange(EventMessage.PaySucessResult mPaySucessResult) {
       finish();
    }
}
