package com.example.administrator.js.vipandtrainer;

import android.Manifest;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.adapter.FragmentAdapter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.TablayoutUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.NearByVipFragment;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.MessageActivity;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.model.Main;
import com.example.administrator.js.exercise.member.NearByTrainerFragment;
import com.example.administrator.js.qrcode.CaptureActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by tangming on 2018/5/3.
 */

public class MainVipFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.pager)
    ViewPager mViewpager;

    FragmentAdapter mFragmentAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_vip;
    }

    @Override
    protected void initView() {

        mToolbar.inflateMenu(R.menu.main_vip);
        if (UserManager.getInsatance().getRole().equals("0")) {
            mToolbar.setTitle("会员");

        } else {
            mToolbar.setTitle("教练");
        }
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.scan) {

                    saoyisao();
                } else {
                    start(MessageActivity.class);
                }

                return false;
            }
        });

        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), getFragments(), getTabTitle());
        mViewpager.setAdapter(mFragmentAdapter);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mViewpager);
        mViewpager.setOffscreenPageLimit(4);
        TablayoutUtils.setTabLine(mTab, 20, 20, mContext);
        requestData();
    }

    private void saoyisao() {

        RxPermissions mRxPermissions = new RxPermissions(getActivity());
        mRxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean mBoolean) throws Exception {
                        if (mBoolean) {

                            start(CaptureActivity.class);


                        } else {
                            showToast("请开启摄像头权限");
                        }
                    }
                });
    }

    @Override
    protected void requestData() {

        Http.getDefault().getMain(UserManager.getInsatance().getUser().id, 5, 1, 1)
                .as(RxHelper.<WrapperModel<Main>>handleResult(getContext()))
                .subscribe(new ResponceSubscriber<WrapperModel<Main>>() {
                    @Override
                    protected void onSucess(final WrapperModel<Main> mMainWrapperModel) {
                        if (mMainWrapperModel != null && mMainWrapperModel.list != null && mMainWrapperModel.list.size() != 0) {
                            ImageLoader.load(mContext, mMainWrapperModel.list.get(0).image, mIvAdd);
                        }
                        mIvAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View mView) {
                                ARouter.getInstance().build("/exercise/DetailActivity")
                                        .withObject("mMain", mMainWrapperModel.list.get(0))
                                        .navigation(mContext);
                            }
                        });

                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });

    }

    private String[] getTabTitle() {
        if (UserManager.getInsatance().getRole().equals("0")) {
            return new String[]{
                    "附近会员", "我的会员", "我的关注", "黑名单"
            };

        } else {
            return new String[]{
                    "附近教练", "接单教练", "我的关注", "黑名单"
            };
        }

    }

    private List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<>();

        if (UserManager.getInsatance().getRole().equals("0")) {
            NearByVipFragment mVipUserFragment = (NearByVipFragment) ARouter.getInstance().build("/commen/NearByVipFragment")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment);

            VipUserFragment mVipUserFragment1 = (VipUserFragment) ARouter.getInstance().build("/vip/VipUserFragment")
                    .withString("status", "3")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment1);

            VipUserFragment mVipUserFragment2 = (VipUserFragment) ARouter.getInstance().build("/vip/VipUserFragment")
                    .withString("status", "1")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment2);

            VipUserFragment mVipUserFragment3 = (VipUserFragment) ARouter.getInstance().build("/vip/VipUserFragment")
                    .withString("status", "2")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment3);
        } else {
            NearByTrainerFragment mNearByTrainerFragment = (NearByTrainerFragment) ARouter.getInstance().build("/trainer/NearByTrainerFragment")
                    .navigation(mContext);
            mFragments.add(mNearByTrainerFragment);

            TrainerFragment mVipUserFragment1 = (TrainerFragment) ARouter.getInstance().build("/trainer/TrainerFragment")
                    .withString("status", "3")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment1);

            TrainerFragment mVipUserFragment2 = (TrainerFragment) ARouter.getInstance().build("/trainer/TrainerFragment")
                    .withString("status", "1")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment2);

            TrainerFragment mVipUserFragment3 = (TrainerFragment) ARouter.getInstance().build("/trainer/TrainerFragment")
                    .withString("status", "2")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment3);
        }

        return mFragments;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick(R.id.iv_add)
    public void onViewClicked() {
    }
}
