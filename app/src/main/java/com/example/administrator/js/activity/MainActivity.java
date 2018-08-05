package com.example.administrator.js.activity;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Navigator;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.LogUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.LocationManager;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.locaiton.ChooseLocationActivity;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.course.MainCourseFragment;
import com.example.administrator.js.exercise.ExerciseFragment;
import com.example.administrator.js.me.MeFragment;
import com.example.administrator.js.me.member.MeMemberFragment;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.vipandtrainer.MainVipFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

@Route(path = "/activity/MainActivity")
public class MainActivity extends BaseActivity {


    @BindView(R.id.bnve)
    BottomNavigationViewEx mBnve;

    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.container)
    FrameLayout mContainer;

    Navigator mNavigator;
    ImageView mIvAdd;

    Fragment mMeFragment;
    ExerciseFragment mExerciseFragment;
    MainVipFragment mMainVipFragment;
    MainCourseFragment mMainCourseFragment;

    @Override
    protected int getContentViewLayoutID() {
        if (UserManager.getInsatance().getUser() != null) {
            if ("0".equals(UserManager.getInsatance().getUser().role)) {

                return R.layout.activity_main_teacher;

            } else {
                return R.layout.activity_main_student;

            }
        }
        return 0;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {

        upadateLocation();
        initIm();

        if (UserManager.getInsatance().getUser() != null) {
            if ("0".equals(UserManager.getInsatance().getUser().role)) {

                //        mIvAdd.setVisibility(View.GONE);
                mMeFragment = new MeFragment();
            } else {
                mIvAdd = findViewById(R.id.iv_add);
                mIvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View mView) {
                        onAddClicked();
                    }
                });
                mIvAdd.setVisibility(View.VISIBLE);
                mMeFragment = new MeMemberFragment();
            }
        }
        mBnve.enableItemShiftingMode(false);
        mBnve.enableShiftingMode(false);
        mBnve.enableAnimation(false);


        mExerciseFragment = new ExerciseFragment();
        mMainVipFragment = new MainVipFragment();
        mMainCourseFragment = new MainCourseFragment();

        mNavigator = new Navigator(getSupportFragmentManager(), R.id.content);
        mNavigator.showFragment(mExerciseFragment);

        mBnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.js) {

                    mNavigator.showFragment(mExerciseFragment);
                } else if (item.getItemId() == R.id.vip) {

                    mNavigator.showFragment(mMainVipFragment);

                } else if (item.getItemId() == R.id.course) {

                    mNavigator.showFragment(mMainCourseFragment);
                } else {


                    mNavigator.showFragment(mMeFragment);

                }

                return true;
            }
        });
    }

    private void initIm() {

        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message mMessage, int mI) {

                EventBus.getDefault().post(new EventMessage.NewMessageReceived(0));
                return false;
            }
        });

    }

    private void upadateLocation() {

        Map<String, String> mStringStringMap = new HashMap<>();
        mStringStringMap.put("id", UserManager.getInsatance().getUser().id);
        mStringStringMap.put("longitude", LocationManager.getInsatance().longitude);
        mStringStringMap.put("latitude", LocationManager.getInsatance().latitude);
        Http.getDefault().userEdit(mStringStringMap)
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>() {
                    @Override
                    protected void onSucess(User mUser) {
                        LogUtils.d("成功上传用户经纬度");
                    }

                    @Override
                    protected void onFail(String message) {
                        LogUtils.d("上传用户经纬度失败了");

                    }
                });

    }

    public void onAddClicked() {

        //   start(NewNeedActivity.class);
        start(NewNeedActivity.class);
    }

}
