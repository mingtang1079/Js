package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.BuildConfig;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherBiDuActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_zhize)
    LinearLayout mLlZhize;
    @BindView(R.id.ll_my_fuwu)
    LinearLayout mLlMyFuwu;
    @BindView(R.id.ll_pingjia)
    LinearLayout mLlPingjia;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_teacher_bi_du;
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
        mToolbar.setTitle("教练必读");

    }

    @OnClick({R.id.ll_zhize, R.id.ll_my_fuwu, R.id.ll_pingjia,R.id.ll_xuzhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case  R.id.ll_xuzhi:

                ARouter.getInstance().build("/web/Html5Activity")
                        .withString("url",  "https://www.cdmuscle.com/h5/news/detail?id=xuzhi")
                        .navigation(mContext);

                break;

            case R.id.ll_zhize:

                ARouter.getInstance().build("/web/Html5Activity")
                        .withString("url",  "https://www.cdmuscle.com/h5/news/detail?id=zhize")
                        .navigation(mContext);

                break;
            case R.id.ll_my_fuwu:


                ARouter.getInstance().build("/web/Html5Activity")
                        .withString("url", "https://www.cdmuscle.com/h5/news/detail?id=liucheng")
                        .navigation(mContext);

                break;
            case R.id.ll_pingjia:

                ARouter.getInstance().build("/web/Html5Activity")
                        .withString("url", "https://www.cdmuscle.com/h5/news/detail?id=jizhi")
                        .navigation(mContext);


                break;
        }
    }
}
