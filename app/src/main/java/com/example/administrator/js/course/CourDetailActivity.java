package com.example.administrator.js.course;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.BuildConfig;
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
    MapView mMapView;
    @BindView(R.id.tv_dazhaohu)
    TextView mTvDazhaohu;
    @BindView(R.id.tv_yuyue)
    TextView mTvYuyue;

    CourseDetail mCourseDetail;
    double lat;
    double lon;

    private CameraUpdate cameraUpdate;
    AMap aMap = null;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        //初始化地图控制器对象

        if (aMap == null) {
            aMap = mMapView.getMap();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("课程详情");
        toggleShowLoading(true);
        requestData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
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
                        toggleShowLoading(false);

                        setData();
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });


    }

    private void setData() {
        lat = Double.valueOf(mCourseDetail.latitude);
        lon = Double.valueOf(mCourseDetail.longtitude);
        // TODO: 2018/5/23
        mTvName.setText(mCourseDetail.nickname);
        ImageLoader.load(mContext, mCourseDetail.img, mIvHead);

        mTvCourseType.setText(mCourseDetail.coursetypenames);
        //  "status": "0", // 课程状态10已预约11进行中2已结束3已取消
        if ("10".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.beginStarttime);
            mTvProgress.setText("已预约");
            mTvYuyue.setText("取消预约");

        } else if ("11".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.beginStarttime);
            mTvProgress.setText("进行中");

        } else if ("2".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.beginStarttime + "-" + mCourseDetail.endEndtime);
            mTvProgress.setText("已结束");

        } else {
            mTvCourseTime.setText(mCourseDetail.beginStarttime);
            mTvProgress.setText("已取消");

        }
        mTvAddress.setText(mCourseDetail.address);

        setMapLocation();
    }

    private void setMapLocation() {

        if (BuildConfig.DEBUG) {
            lat = 30.5851693664;
            lon = 104.0321159258;
        }

        LatLng latLng = new LatLng(lat, lon);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("石羊场客运站").snippet(""));
        //可视化区域，将指定位置指定到屏幕中心位置
//        cameraUpdate = CameraUpdateFactory
//                .newCameraPosition(new CameraPosition(new LatLng(30.5851693664,
//                        104.0321159258), 18, 0, 30));

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        aMap.moveCamera(cameraUpdate);
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
