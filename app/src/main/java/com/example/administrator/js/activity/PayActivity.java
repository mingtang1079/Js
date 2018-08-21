package com.example.administrator.js.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.PayTask;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.JsonUtil;
import com.appbaselib.utils.PreferenceUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.locaiton.PayResult;
import com.example.administrator.js.constant.Constans;
import com.example.administrator.js.constant.EventMessage;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = "/activity/PayActivity")
public class PayActivity extends BaseActivity {

    @Autowired
    String orderId;
    @Autowired
    String orderType;
    @Autowired
    int price;//实际价格
    @Autowired
    int totalPrice;
    @Autowired
    String title;

    @BindView(R.id.iv_close)
    ImageView mImageViewClose;
    View mSpace;
    @BindView(R.id.tv_title)
    TextView mTextViewTitle;
    @BindView(R.id.tv_all_price)
    TextView mTvShijiPrice;//实际价格
    @BindView(R.id.tv_total_price)
    TextView mTextViewTotolPrice;//原始价格
    @BindView(R.id.tv_youhui_price)
    TextView mTextViewYouhuijiage;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.btn_sure)
    Button mBtnSure;

    PayAdapter mPayAdapter;
    List<String> mStrings = new ArrayList<>();

    int type = 0;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_pay;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {
        mSpace = findViewById(R.id.space);
        mSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                finish();
            }
        });
        mImageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                finish();
            }
        });
        mTextViewTitle.setText(title);
        mStrings.add("支付宝支付");
        mStrings.add("微信支付");
        mPayAdapter = new PayAdapter(R.layout.item_pay, mStrings);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setAdapter(mPayAdapter);
        mPayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                mPayAdapter.setSingleChoosed(position);
                type = position;

            }
        });
        //默认支付宝支付
        mPayAdapter.setSingleChoosed(0);
        //实际价格
        BigDecimal a;
        BigDecimal b;
        a = new BigDecimal(price);
        b = new BigDecimal(100);
        mTvShijiPrice.setText("￥" + a.divide(b, 2, RoundingMode.HALF_UP).toString() + "元");

        //订单情况
        if (!"押金".equals(title)) {
            if (totalPrice == price) {
                mTextViewYouhuijiage.setText("无优惠");
            } else {
                BigDecimal a1;
                BigDecimal b1;
                a1 = new BigDecimal(totalPrice - price);
                b1 = new BigDecimal(100);
                mTextViewYouhuijiage.setText(a1.divide(b1, 2, RoundingMode.HALF_UP).toString() + "元");
            }
            BigDecimal aa = new BigDecimal(totalPrice);
            mTextViewTotolPrice.setText(aa.divide(new BigDecimal(100)).doubleValue() + "元");
        } else {

            BigDecimal aa = new BigDecimal(price);//押金的实际价格 就是总价
            mTextViewTotolPrice.setText(aa.divide(new BigDecimal(100)).doubleValue() + "元");
            mTextViewYouhuijiage.setText("无优惠");

        }
        //临时存放 orderId
        PreferenceUtils.setPrefString(mContext, Constans.ORDERID, orderId);
    }

    @OnClick(R.id.btn_sure)
    public void onViewClicked() {

        if (type == 0) {
            //支付宝

            startPay();
        } else {

            startWeixinPay();

        }

    }


    private void startPay() {

        Http.getDefault().pay("0".equals(orderType) ? null : UserManager.getInsatance().getUser().id, orderId, String.valueOf(type), orderType)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {

                        startAlipay(mS);

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });
    }

    private void startAlipay(final String mS) {
        Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, String>> e) throws Exception {

                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(mS, true);
                e.onNext(result);

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String, String>>() {
                    @Override
                    public void accept(Map<String, String> mMap) throws Exception {
                        PayResult mPayResult = JsonUtil.fromJson(mMap.get("result"), PayResult.class);
                        String resultStatus = mMap.get("resultStatus");
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // MyUtil.showToast("支付成功");
                            //更新订单列表状态
                            EventBus.getDefault().post(new EventMessage.ListStatusChange());
                            ARouter.getInstance().build("/activity/PaySuccessfulActivity")
                                    .withString("orderId", orderId)
                                    .navigation(mContext);
                            finish();
                        } else {
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服				务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                            } else if (TextUtils.equals(resultStatus, "6001")) {
                                //   MyUtil.showToast("支付取消");
                                showToast("支付取消");
                            } else if (TextUtils.equals(resultStatus, "6002")) {
                                //   MyUtil.showToast("网络异常");
                                showToast("网络异常");
                            } else if (TextUtils.equals(resultStatus, "5000")) {
                                //   MyUtil.showToast("重复请求");
                            } else {
                                // 其他值就可以判断为支付失败
                                //   MyUtil.showToast("支付失败");
                                showToast("支付失败");

                            }

                        }
                    }
                });


    }

    private IWXAPI iwxapi; //微信支付api

    private void startWeixinPay() {


        Http.getDefault().payWeixin("0".equals(orderType) ? null : UserManager.getInsatance().getUser().id, orderId, String.valueOf(type), orderType)
                .as(RxHelper.<WeixinResult>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WeixinResult>(mContext) {
                    @Override
                    protected void onSucess(WeixinResult mS) {

                        weixinPay(mS);

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });
    }


    private void weixinPay(final WeixinResult mWeixinResult) {

        Runnable payRunnable = new Runnable() {  //这里注意要放在子线程
            @Override
            public void run() {

                iwxapi = WXAPIFactory.createWXAPI(PayActivity.this, null); //初始化微信api
                iwxapi.registerApp(Constans.weixin); //注册appid  appid可以在开发平台获取
                PayReq request = new PayReq(); //调起微信APP的对象
                //下面是设置必要的参数，也就是前面说的参数,这几个参数从何而来请看上面说明
                request.appId = mWeixinResult.appid;
                request.partnerId = mWeixinResult.partnerid;
                request.prepayId = mWeixinResult.prepayid;
                request.packageValue = "Sign=WXPay";
                request.nonceStr = mWeixinResult.noncestr;
                request.timeStamp = mWeixinResult.timestamp;
                request.sign = mWeixinResult.sign;
                iwxapi.sendReq(request);//发送调起微信的请求
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatusChange(EventMessage.closePayActivity mListStatusChange) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.alpha_out);
    }

}
