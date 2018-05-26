package com.example.administrator.js.me;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.adapter.FragmentAdapter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.LogUtils;
import com.appbaselib.utils.TablayoutUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.NearByVipFragment;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.model.Main;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VipSupplyActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.pager)
    ViewPager mViewpager;

    FragmentAdapter mFragmentAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_vip_supply;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("会员申请");
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragments(), getTabTitle());
        mViewpager.setAdapter(mFragmentAdapter);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mViewpager);
        mViewpager.setOffscreenPageLimit(4);
           TablayoutUtils.setTabLine(mTab,20,20,mContext);
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    private String[] getTabTitle() {

        return new String[]{"体验课", "私教课", "申请记录"};
    }

    private List<Fragment> getFragments() {
        List<Fragment> mFragments = new ArrayList<>();

        VipSupplyFragment mVipUserFragment1 = (VipSupplyFragment) ARouter.getInstance().build("/me/VipSupplyFragment")
                .withString("status", "1")
                .navigation(mContext);
        mFragments.add(mVipUserFragment1);

        VipSupplyFragment mVipUserFragment2 = (VipSupplyFragment) ARouter.getInstance().build("/me/VipSupplyFragment")
                .withString("status", "2")
                .navigation(mContext);
        mFragments.add(mVipUserFragment2);

        VipSupplyFragment mVipUserFragment3 = (VipSupplyFragment) ARouter.getInstance().build("/me/VipSupplyFragment")
                .withString("status", "3")
                .navigation(mContext);
        mFragments.add(mVipUserFragment3);


        return mFragments;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}
