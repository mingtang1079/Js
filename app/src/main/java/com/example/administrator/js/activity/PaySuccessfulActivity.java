package com.example.administrator.js.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/activity/PaySuccessfulActivity")
public class PaySuccessfulActivity extends BaseActivity {

    @Autowired
    String orderId;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_back_home)
    TextView mTvBackHome;
    @BindView(R.id.tv_back_top)
    TextView mTvBackTop;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_pay_successful;
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
        mToolbar.setTitle("支付结果");

    }

    @OnClick({R.id.tv_back_home, R.id.tv_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_home:
                finish();
                break;
            case R.id.tv_back_top:

                ARouter.getInstance().build("/me/member/OrderDetailActivity")
                        .withString("id", orderId)
                        .navigation(mContext);
                finish();
                break;
        }
    }
}
