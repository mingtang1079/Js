package com.example.administrator.js.vip;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.adapter.FragmentAdapter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.utils.TablayoutUtils;
import com.example.administrator.js.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by tangming on 2018/5/3.
 */

public class VipFragment extends BaseFragment {
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

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragments(), getTabTitle());
        mViewpager.setAdapter(mFragmentAdapter);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mViewpager);
        TablayoutUtils.setTabLine(mTab,30,30,mContext);
    }

    private String[] getTabTitle() {

        return new String[]{
                "附近会员", "我的会员", "我的关注", "黑名单"
        };
    }

    private List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<>();

        //one
//        Map<String, String> mStringStringMap = new HashMap<>();
//        VipUserFragment mVipUserFragment = (VipUserFragment) ARouter.getInstance().build("/vip/VipUserFragment")
//                .withObject("mMap", mStringStringMap)
//                .navigation(mContext);
//        mFragments.add(mVipUserFragment);

        VipUserFragment mVipUserFragment1 = (VipUserFragment) ARouter.getInstance().build("/vip/VipUserFragment")
                .withString("status", "1")
                .navigation(mContext);
        mFragments.add(mVipUserFragment1);

        VipUserFragment mVipUserFragment2 = (VipUserFragment) ARouter.getInstance().build("/vip/VipUserFragment")
                .withString("status", "3")
                .navigation(mContext);
        mFragments.add(mVipUserFragment2);

        VipUserFragment mVipUserFragment3 = (VipUserFragment) ARouter.getInstance().build("/vip/VipUserFragment")
                .withString("status", "2")
                .navigation(mContext);
        mFragments.add(mVipUserFragment3);


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
