package com.example.administrator.js.me.member;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.adapter.FragmentAdapter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.utils.TablayoutUtils;
import com.example.administrator.js.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    public TabLayout mTab;
    public ViewPager mViewpager;

    FragmentAdapter mFragmentAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_order;
    }

    protected String[] getTabTitle() {

        return new String[]{"全部", "待接单", "待付款", "已完成", "退款中", "已退款"};
    }

    ;

    public List<Fragment> getFragments() {

        List<Fragment> m = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            String status = null;
            switch (i) {
                case 0:
                    status = "all";
                    break;
                case 1:
                    status = "a1";
                    break;
                case 2:
                    status = "b2";
                    break;
                case 3:
                    status = "b3";
                    break;
                case 4:
                    status = "b55";
                    break;
                case 5:
                    status = "b56";
                    break;
            }
            OrderFragment mFragment = (OrderFragment) ARouter.getInstance().build("/me/member/OrderFragment")
                    .withString("status", status)
                    .navigation(mContext);
            m.add(mFragment);
        }

        return m;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("我的订单");
        mTab = (TabLayout) findViewById(R.id.tab);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mViewpager.setOffscreenPageLimit(6);
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragments(), getTabTitle());
        mViewpager.setAdapter(mFragmentAdapter);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTab.setupWithViewPager(mViewpager);
        TablayoutUtils.setTabLine(mTab, 10, 10, mContext);

    }
}
