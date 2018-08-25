package com.example.administrator.js.course;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
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
import com.appbaselib.utils.BottomDialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.BuildConfig;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.course.model.CourseDetail;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.utils.Utils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;

import static com.appbaselib.utils.PackageUtil.isAvilible;

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
        if (!TextUtils.isEmpty(mCourseDetail.latitude)) {
            lat = Double.valueOf(mCourseDetail.latitude);
        }
        if (!TextUtils.isEmpty(mCourseDetail.longtitude)) {
            lon = Double.valueOf(mCourseDetail.longtitude);
        }
        // TODO: 2018/5/23
        mTvName.setText(mCourseDetail.nickname);
        ImageLoader.load(mContext, mCourseDetail.img, mIvHead);

        mTvCourseType.setText(mCourseDetail.coursetypenames);
        //  "status": "0", // 课程状态10已预约11进行中2已结束3已取消  14,课程进展应该是 已预约,且11进行中状态,不应该出现取消预约按钮,
        if ("10".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.starttime);
            mTvProgress.setText("已预约");

        } else if ("11".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.starttime);
            mTvProgress.setText("进行中");
            mTvYuyue.setVisibility(View.GONE);

        } else if ("2".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.starttime + "\n" + mCourseDetail.endtime);
            mTvProgress.setText("已结束");
            mTvYuyue.setVisibility(View.GONE);

        } else if ("14".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.starttime);
            mTvProgress.setText("已预约");
            mTvYuyue.setVisibility(View.GONE);

        } else {
            mTvCourseTime.setText(mCourseDetail.starttime);
            mTvProgress.setText("已取消");
            mTvYuyue.setVisibility(View.GONE);

        }
        mTvAddress.setText(mCourseDetail.address);

        setMapLocation();
    }

    private void setMapLocation() {

//        if (BuildConfig.DEBUG) {
//            lat = 30.5851693664;
//            lon = 104.0321159258;
//        }

        LatLng latLng = new LatLng(lat, lon);
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(mCourseDetail.address).snippet(""));
        //可视化区域，将指定位置指定到屏幕中心位置
//        cameraUpdate = CameraUpdateFactory
//                .newCameraPosition(new CameraPosition(new LatLng(30.5851693664,
//                        104.0321159258), 18, 0, 30));


        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker mMarker) {
                showMap();
                return true;
            }
        });
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        aMap.moveCamera(cameraUpdate);

    }

    private void showMap() {
        final List<String> mStrings = new ArrayList<>();
        //百度
        if (isAvilible(getApplicationContext(), "com.baidu.BaiduMap")) {
            mStrings.add("百度地图");
        }
        //高德
        if (isAvilible(getApplicationContext(), "com.autonavi.minimap")) {
            mStrings.add("高德地图");

        }
//        //腾讯
//        if (isAvilible(getApplicationContext(), "com.tencent.map")) {
//            mStrings.add("腾讯地图");
//        }
        if (mStrings.size() == 0) {
            showToast("请安装地图软件");
            return;
        }

        BottomDialogUtils.showBottomDialog(mContext, "请选择导航地图", mStrings, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (mStrings.get(position).contains("百度")) {
                    Intent intent = null;
                    try {
                        intent = Intent.getIntent("intent://map/direction?destination=latlng:" + lat + "," + lon + "|name:&origin=" + "我的位置" + "&mode=driving?ion=" + "我的位置" + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(intent); // 启动调用
                } else if (mStrings.get(position).contains("高德")) {


                    Intent intent = new Intent("android.intent.action.VIEW",
                            android.net.Uri.parse("androidamap://navi?sourceApplication=amap&lat=" + lat + "&lon=" + lon + "&dev=1&style=0"));
                    intent.setPackage("com.autonavi.minimap");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
        }).show();

    }

    @OnClick({R.id.tv_call, R.id.map, R.id.tv_dazhaohu, R.id.tv_yuyue,R.id.iv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_call:

                Utils.callPhone(mContext, mCourseDetail.mobile);
                break;
            case R.id.map:
                break;
            case R.id.tv_dazhaohu:
                RongIM.getInstance().startPrivateChat(this, mCourseDetail.uid, mCourseDetail.nickname);

                break;
            case R.id.tv_yuyue:


                cancelCourse();

                break;
            case R.id.iv_head:

                ARouter.getInstance().build("/activity/LookBigImageActivity")
                        .withString("url",mCourseDetail.img)
                        .navigation();

                break;
        }
    }

    private void cancelCourse() {


        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setTitle("课程取消原因");
        View mView = LayoutInflater.from(mContext).inflate(R.layout.view_input, null, false);
        final TextInputEditText mTextInputEditText = mView.findViewById(R.id.et);
        mBuilder.setView(mView);
        mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialogInterface, int mI) {
                mDialogInterface.dismiss();
            }
        });
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialogInterface, int mI) {

                Http.getDefault().cancelCourse(mCourseDetail.id, UserManager.getInsatance().getUser().id, mTextInputEditText.getText().toString())
                        .as(RxHelper.<String>handleResult(mContext))
                        .subscribe(new ResponceSubscriber<String>() {
                            @Override
                            protected void onSucess(String mS) {

                                EventBus.getDefault().post(new EventMessage.CourseListStatusChange());
                                showToast("取消成功");
                                finish();
                            }

                            @Override
                            protected void onFail(String message) {
                                showToast(message);
                                requestData();
                            }
                        });
            }
        });
        AlertDialog mAlertDialog = mBuilder.create();
        mAlertDialog.show();
        final Button mButton = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        mButton.setEnabled(false);
        RxTextView.textChangeEvents(mTextInputEditText).skip(1)
                .subscribe(new Consumer<TextViewTextChangeEvent>() {
                    @Override
                    public void accept(TextViewTextChangeEvent mTextViewTextChangeEvent) throws Exception {
                        if (!TextUtils.isEmpty(mTextViewTextChangeEvent.text().toString())) {
                            mButton.setEnabled(true);
                        } else {
                            mButton.setEnabled(false);
                        }
                    }
                });


    }
}
