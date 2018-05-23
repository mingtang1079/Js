package com.example.administrator.js.exercise.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.LogUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.base.adapter.BaseLifeCycleView;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.model.Main;
import com.example.administrator.js.me.model.Zizhi;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangming on 2018/5/3.
 */

public class MainHeaderView extends BaseLifeCycleView {


    @BindView(R.id.banner)
    com.youth.banner.Banner mBanner;
    @BindView(R.id.tv_vip)
    TextView mTvVip;
    @BindView(R.id.tv_shop)
    TextView mTvShop;
    @BindView(R.id.tv_work)
    TextView mTvWork;

    public MainHeaderView(Context context) {
        super(context);
    }

    public MainHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context mContext) {
        super.init(mContext);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.view_main_header, this, false);
        ButterKnife.bind(this, mView);
        addView(mView);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        requestData();
    }

    private void requestData() {

        Http.getDefault().getMain(1, 1, 3)
                .as(RxHelper.<WrapperModel<Main>>handleResult(getContext()))
                .subscribe(new ResponceSubscriber<WrapperModel<Main>>() {
                    @Override
                    protected void onSucess(final WrapperModel<Main> mMainWrapperModel) {

                        if (mMainWrapperModel != null && mMainWrapperModel.list != null) {
                            List<String> mStrings = new ArrayList<>();
                            for (Main mMain : mMainWrapperModel.list) {
                                mStrings.add(mMain.image);
                            }

                            //设置图片加载器
                            mBanner.setImageLoader(new GlideImageLoader());
                            //设置图片集合
                            mBanner.setImages(mStrings);
                            //banner设置方法全部调用完毕时最后调用
                            mBanner.setDelayTime(3000);
                            mBanner.start();
                            mBanner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(int mI) {
                                    ARouter.getInstance().build("/exercise/DetailActivity")
                                            .withObject("mMain", mMainWrapperModel.list.get(mI))
                                            .navigation(getContext());
                                }
                            });
                        }
                    }

                    @Override
                    protected void onFail(String message) {

                        LogUtils.d("轮播图----》", message);

                    }
                });

    }

    @OnClick({R.id.tv_vip, R.id.tv_shop, R.id.tv_work})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_vip:

                break;
            case R.id.tv_shop:
                ARouter.getInstance().build("/web/Html5Activity")
                        .withString("url", "http://www.qq.com")
                        .navigation(getContext());
                break;
            case R.id.tv_work:

                break;
        }
    }

    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context mContext, Object mO, ImageView mImageView) {
            com.appbaselib.common.ImageLoader.load(mContext, mO.toString(), mImageView);
        }
    }
}