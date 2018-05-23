package com.example.administrator.js.course;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.MapView;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.course.model.CourseDetail;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = "/course/CourDetailActivity")
public class CourDetailActivity extends BaseActivity {

    @Autowired
    String id;

    @BindView(R.id.content)
    LinearLayout mLinearLayoutContent;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_course_type)
    TextView mTvCourseType;
    @BindView(R.id.tv_course_time)
    TextView mTvCourseTime;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_call)
    TextView mTvCall;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.tv_dazhaohu)
    TextView mTvDazhaohu;
    @BindView(R.id.tv_yuyue)
    TextView mTvYuyue;

    CourseDetail mCourseDetail;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_cour_detail;
    }

    @Override
    protected View getLoadingTargetView() {
        return mLinearLayoutContent;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("课程详情");


    }

    @Override
    protected void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    @Override
    protected void requestData() {
        super.requestData();
        User mUser = UserManager.getInsatance().getUser();

        Http.getDefault().courseDetail(mUser.id, id)
                .as(RxHelper.<CourseDetail>handleResult(mContext))
                .subscribe(new ResponceSubscriber<CourseDetail>() {
                    @Override
                    protected void onSucess(CourseDetail m) {
                        mCourseDetail = m;
                        setData();
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });


    }

    private void setData() {
        // TODO: 2018/5/23
    }

    @OnClick({R.id.tv_call, R.id.map, R.id.tv_dazhaohu, R.id.tv_yuyue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_call:

                Utils.callPhone(mContext, mCourseDetail.mobile);
                break;
            case R.id.map:
                break;
            case R.id.tv_dazhaohu:

                break;
            case R.id.tv_yuyue:


                break;
        }
    }
}
