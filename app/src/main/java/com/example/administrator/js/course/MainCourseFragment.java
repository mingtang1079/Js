package com.example.administrator.js.course;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.adapter.FragmentAdapter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.utils.TablayoutUtils;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.MessageActivity;
import com.example.administrator.js.course.member.CourseMemberFragment;
import com.example.administrator.js.qrcode.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tangming on 2018/5/3.
 */

public class MainCourseFragment extends BaseFragment {

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
        mToolbar.setTitle("课程");
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

        if (UserManager.getInsatance().getRole().equals("0")) {
            return new String[]{"进行中", "已结束"};


        } else {
            return new String[]{"进行中", "历史订单"};

        }

    }

    ;

    public List<Fragment> getFragments() {

        List<Fragment> mFragments = new ArrayList<>();


        if (UserManager.getInsatance().getRole().equals("0")) {

            CourseTrainerFragment mVipUserFragment1 = (CourseTrainerFragment) ARouter.getInstance().build("/course/CourseTrainerFragment")
                    .withString("status", "1")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment1);

            CourseTrainerFragment mVipUserFragment2 = (CourseTrainerFragment) ARouter.getInstance().build("/course/CourseTrainerFragment")
                    .withString("status", "2")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment2);



        } else {

            CourseMemberFragment mVipUserFragment1 = (CourseMemberFragment) ARouter.getInstance().build("/member/CourseMemberFragment")
                    .withString("status", "1")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment1);

            CourseMemberFragment mVipUserFragment2 = (CourseMemberFragment) ARouter.getInstance().build("/member/CourseMemberFragment")
                    .withString("status", "2")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment2);


        }

        return mFragments;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}
