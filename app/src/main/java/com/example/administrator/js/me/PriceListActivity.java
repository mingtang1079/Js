package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.Price;
import com.example.administrator.js.me.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PriceListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_putong)
    TextView mTvPutong;
    @BindView(R.id.ll_putong)
    LinearLayout mLlPutong;
    @BindView(R.id.tv_zhuanye)
    TextView mTvZhuanye;
    @BindView(R.id.ll_zhuanye)
    LinearLayout mLlZhuanye;
    @BindView(R.id.tv_tese)
    TextView mTvTese;
    @BindView(R.id.ll_tese)
    LinearLayout mLlTese;

    Price m;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_price_list;
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
        mToolbar.setTitle("课程定价");
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        User mUser = UserManager.getInsatance().getUser();
        String id = mUser.id;
        Http.getDefault().getprice(id)
                .as(RxHelper.<Price>handleResult(mContext))
                .subscribe(new ResponceSubscriber<Price>() {
                    @Override
                    protected void onSucess(Price mPrice) {
                        m = mPrice;
                        setData();
                    }

                    @Override
                    protected void onFail(String message) {
                        //   loadError();
                    }
                });

    }

    private void setData() {

        if (m != null && m.userprice != null) {
            mTvPutong.setText(m.userprice.pricea);
            mTvZhuanye.setText(m.userprice.priceb);
            mTvTese.setText(m.userprice.pricec);
        }
    }

    @OnClick({R.id.ll_putong, R.id.ll_zhuanye, R.id.ll_tese})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_putong:


                ARouter.getInstance().build("/me/PriceSettingActivity")
                        .withString("type", "pricea")
                        .withObject("mPrice", m)
                        .navigation(mContext);


                break;
            case R.id.ll_zhuanye:

                ARouter.getInstance().build("/me/PriceSettingActivity")
                        .withString("type", "priceb")
                        .withObject("mPrice", m)
                        .navigation(mContext);


                break;
            case R.id.ll_tese:

                ARouter.getInstance().build("/me/PriceSettingActivity")
                        .withString("type", "pricec")
                        .withObject("mPrice", m)
                        .navigation(mContext);


                break;
        }
    }
}
