package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.Tongji;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TongjiActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_shouru)
    TextView mTvShouru;
    @BindView(R.id.tv_huiyuan)
    TextView mTvHuiyuan;
    @BindView(R.id.tv_keshi)
    TextView mTvKeshi;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tongji;
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

        mToolbar.setTitle("统计");
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();

        Http.getDefault().tongji(UserManager.getInsatance().getUser().id)
                .as(RxHelper.<Tongji>handleResult(mContext))
                .subscribe(new ResponceSubscriber<Tongji>() {
                    @Override
                    protected void onSucess(Tongji mTongji) {
                        if (mTongji!=null)
                        {
                            setData(mTongji);
                        }
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });
    }

    private void setData(Tongji mData) {
        mTvShouru.setText("￥ "+mData.money);
        mTvKeshi.setText(mData.course+"课时");
        mTvHuiyuan.setText(mData.member+"个");

    }
}
