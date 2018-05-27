package com.example.administrator.js.course;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.adapter.FragmentAdapter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.utils.TablayoutUtils;
import com.example.administrator.js.R;
import com.example.administrator.js.activity.MessageActivity;
import com.example.administrator.js.qrcode.CaptureActivity;
import com.example.administrator.js.vip.VipUserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by tangming on 2018/5/3.
 */

public class CourseFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    FragmentAdapter mFragmentAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("消息");
        mToolbar.setTitle("健身");
        //  mToolbar.setTitleTextColor(ContextCompat.getColor(R.color.));
        mToolbar.inflateMenu(R.menu.main_course);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.scan) {

                    start(CaptureActivity.class);
                }

                if (item.getItemId() == R.id.course) {

                    start(CourseCanlenderActivity.class);
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
        TablayoutUtils.setTabLine(mTab, 50, 50, mContext);
    }

    protected String[] getTabTitle() {

        return new String[]{"进行中", "已结束"};
    }

    ;

    public List<Fragment> getFragments() {

        List<Fragment> mFragments = new ArrayList<>();

        CourseUserFragment mVipUserFragment1 = (CourseUserFragment) ARouter.getInstance().build("/course/CourseUserFragment")
                .withString("status", "1")
                .navigation(mContext);
        mFragments.add(mVipUserFragment1);

        CourseUserFragment mVipUserFragment2 = (CourseUserFragment) ARouter.getInstance().build("/course/CourseUserFragment")
                .withString("status", "2")
                .navigation(mContext);
        mFragments.add(mVipUserFragment2);

        return mFragments;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}
