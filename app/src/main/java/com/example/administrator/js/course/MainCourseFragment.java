package com.example.administrator.js.course;

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
import com.appbaselib.utils.TablayoutUtils;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.MessageActivity;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.course.member.CourseHistoryOrderFragment;
import com.example.administrator.js.course.member.CourseMemberFragment;
import com.example.administrator.js.qrcode.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.iv_course)
    ImageView mImageViewCourse;
    @BindView(R.id.iv_message)
    ImageView mImageViewMes;
    @BindView(R.id.view_tag)
    View mViewTag;

    FragmentAdapter mFragmentAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("课程");
        //  mToolbar.setTitleTextColor(ContextCompat.getColor(R.color.));
        mImageViewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                start(CourseCanlenderActivity.class);

            }
        });
        mImageViewMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                EventBus.getDefault().post(new EventMessage.NewMessageReceived(1));
                start(MessageActivity.class);
                mViewTag.setVisibility(View.GONE);
            }
        });
        mFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), getFragments(), getTabTitle());
        mViewpager.setAdapter(mFragmentAdapter);
        mViewpager.setOffscreenPageLimit(3);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        mTab.setupWithViewPager(mViewpager);
        TablayoutUtils.setTabLine(mTab, 10, 10, mContext);
    }

    protected String[] getTabTitle() {

        if (UserManager.getInsatance().getRole().equals("0")) {
            return new String[]{"进行中", "已结束"};


        } else {
            return new String[]{"进行中","已结束", "历史订单"};

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

            CourseMemberFragment mVipUserFragment = (CourseMemberFragment) ARouter.getInstance().build("/member/CourseMemberFragment")
                    .withString("status", "2")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment);
            CourseHistoryOrderFragment mVipUserFragment2 = (CourseHistoryOrderFragment) ARouter.getInstance().build("/member/CourseHistoryOrderFragment")
                    .withString("status", "2")
                    .navigation(mContext);
            mFragments.add(mVipUserFragment2);


        }

        return mFragments;
    }
    @Override
    protected boolean registerEventBus() {
        return true;
    }
    @Override
    protected View getLoadingTargetView() {
        return null;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMessage(EventMessage.NewMessageReceived mm) {

        if (mViewTag != null) {
            if (mm.message == 0) {
                mViewTag.setVisibility(View.VISIBLE);
            } else if (mm.message==1){
                mViewTag.setVisibility(View.GONE);

            }
        }


    }
}
