package com.example.administrator.js.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alipay.sdk.app.PayTask;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.JsonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.BuildConfig;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.locaiton.PayResult;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    String price;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_all_price)
    TextView mTvAllPrice;
    @BindView(R.id.tv_youhui)
    TextView mTvYouhui;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
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
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("支付订单");
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

        if (BuildConfig.DEBUG) {
            mTvPrice.setText("0.1");
        }
        mTvAllPrice.setText(price);
    }

    @OnClick(R.id.btn_sure)
    public void onViewClicked() {

        if (type == 0) {
            //支付宝

            alipay();
        } else {


        }

    }

    private void alipay() {

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
                        String resultStatus = mPayResult.resultStatus;
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // MyUtil.showToast("支付成功");
                        } else {
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服				务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                            } else if (TextUtils.equals(resultStatus, "6001")) {
                                //   MyUtil.showToast("支付取消");
                            } else if (TextUtils.equals(resultStatus, "6002")) {
                                //   MyUtil.showToast("网络异常");
                            } else if (TextUtils.equals(resultStatus, "5000")) {
                                //   MyUtil.showToast("重复请求");
                            } else {
                                // 其他值就可以判断为支付失败
                                //   MyUtil.showToast("支付失败");
                            }

                        }
                    }
                });


    }
}
