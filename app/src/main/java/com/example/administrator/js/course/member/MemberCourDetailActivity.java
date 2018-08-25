package com.example.administrator.js.course.member;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps2d.UiSettings;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.BottomDialogUtils;
import com.appbaselib.utils.DialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.course.CourseModel;
import com.example.administrator.js.course.model.CourseDetail;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.utils.Utils;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;

import static com.appbaselib.utils.PackageUtil.isAvilible;

@Route(path = "/course/MemberCourDetailActivity")
public class MemberCourDetailActivity extends BaseActivity {

    @Autowired
    CourseModel mCourseModel;

    @BindView(R.id.content)
    LinearLayout mLinearLayoutContent;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_id)
    TextView mTvId;
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
    @BindView(R.id.tv_shangke)
    TextView mTvShangke;

    CourseDetail mCourseDetail;
    double lat;
    double lon;

    private CameraUpdate cameraUpdate;
    AMap aMap = null;

    MenuItem mMenuItem;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_member_cour_detail;
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
        //设置显示定位按钮 并且可以点击
        com.amap.api.maps.UiSettings settings = aMap.getUiSettings();
        settings.setZoomControlsEnabled(false);//缩放按钮

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
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("投诉");
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                start(TousuActivity.class);
                return false;
            }
        });

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

        Http.getDefault().courseDetail2(mUser.id, this.mCourseModel.id)
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
        mTvId.setText("ID：" + mCourseDetail.no + "");

        //年龄
        if (mCourseDetail.sex != null) {
            if (mCourseDetail.age != null) {
                mTvAge.setText(" " + mCourseDetail.age);
            }
            if (mCourseDetail.sex.equals("1")) {
                //男性
                mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvAge.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvAge.setCompoundDrawables(drawable, null, null, null);
                mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));

            }
        } else {
            mTvAge.setVisibility(View.GONE);
        }

        mTvCourseType.setText(mCourseDetail.coursetypenames);
        //  "status": "0", // 课程状态10已预约11进行中2已结束3已取消

        mTvAddress.setText(mCourseDetail.address);

        setStatus();
        setMapLocation();
    }

    void setStatus() {
        if ("10".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.starttime);
            mTvProgress.setText("已预约");
            mTvShangke.setVisibility(View.VISIBLE);
            mTvShangke.setText("取消预约");
        } else if ("11".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.starttime);
            mTvProgress.setText("进行中");
            mTvShangke.setText("下课");
            mTvShangke.setVisibility(View.VISIBLE);
        } else if ("2".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.starttime + "\n" + mCourseDetail.endtime);
            mTvProgress.setText("已结束");
            mTvShangke.setVisibility(View.VISIBLE);
            mTvShangke.setText("评价");

        } else if ("14".equals(mCourseDetail.status)) {
            mTvCourseTime.setText(mCourseDetail.starttime);
            mTvProgress.setText("已预约");
            mTvShangke.setVisibility(View.VISIBLE);
            mTvShangke.setText("开始上课");

        } else {
            mTvCourseTime.setText(mCourseDetail.starttime);
            mTvProgress.setText("已取消");
            mTvShangke.setVisibility(View.GONE);
        }
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

    @OnClick({R.id.tv_call, R.id.map, R.id.tv_dazhaohu, R.id.tv_shangke, R.id.iv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_call:

                Utils.callPhone(mContext, mCourseDetail.mobile);
                break;
            case R.id.map:
                break;
            case R.id.tv_dazhaohu:
                RongIM.getInstance().startPrivateChat(this, mCourseDetail.tid, mCourseDetail.nickname);

                break;
            case R.id.tv_shangke:


                xiake();

                break;
            case R.id.iv_head:

                ARouter.getInstance().build("/activity/LookBigImageActivity")
                        .withString("url", mCourseDetail.img)
                        .navigation();

                break;
        }
    }

    private void xiake() {

        String status = "";
        String title = "";
        if (mTvShangke.getText().toString().equals("开始上课")) {
            status = "11";
            title = "确定开始上课吗？";
        } else if (mTvShangke.getText().toString().equals("下课")) {
            status = "2";
            title = "确定下课吗？";
        } else if (mTvShangke.getText().toString().equals("取消预约")) {
            cancelCourse();
            return;
        } else if (mTvShangke.getText().toString().equals("评价")) {
            ARouter.getInstance().build("/member/PingjiaActivity")
                    .withString("tid", this.mCourseModel.tid)
                    .withString("id", mCourseDetail.id)
                    .navigation(mContext);
            //  finish();
            return;
        }
        final String finalStatus = status;
        final String finalStatus1 = status;
        DialogUtils.getDefaultDialog(mContext, "提示", title, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialogInterface, int mI) {
//上课11,下课2
                Http.getDefault().xiake(UserManager.getInsatance().getUser().id, MemberCourDetailActivity.this.mCourseModel.id, finalStatus)
                        .as(RxHelper.<String>handleResult(mContext))
                        .subscribe(new ResponceSubscriber<String>(mContext) {
                            @Override
                            protected void onSucess(String mS) {
                                mCourseDetail.status = finalStatus1;
                                EventBus.getDefault().post(new EventMessage.CourseListStatusChange());
                                setStatus();
                            }

                            @Override
                            protected void onFail(String message) {
                                showToast(message);
                            }
                        });

            }
        }).show();

    }

    private void cancelCourse() {


        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        mBuilder.setTitle("课程取消原因");
        View mView = LayoutInflater.from(mContext).inflate(R.layout.view_member_input, null, false);
        TextView mTextView=mView.findViewById(R.id.tv_remark);
        mTextView.setText("本月还可取消预约"+mCourseDetail.canceltimes+"次");
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

                Http.getDefault().cancelCourse2(mCourseDetail.id, UserManager.getInsatance().getUser().id, mTextInputEditText.getText().toString())
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
