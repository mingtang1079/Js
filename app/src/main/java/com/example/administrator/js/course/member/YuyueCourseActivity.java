package com.example.administrator.js.course.member;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.services.core.PoiItem;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.AdressHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.locaiton.ChooseLocationActivity;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.me.model.User;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mic.adressselectorlib.City;
import com.mic.adressselectorlib.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

@Route(path = "/member/YuyueCourseActivity")
public class YuyueCourseActivity extends BaseActivity {

    @Autowired
    String id;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.content)
    LinearLayout mLinearLayoutContent;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    //    @BindView(R.id.tv_age)
//    TextView mTvAge;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_course_type)
    TextView mTvCourseType;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_time)
    LinearLayout mLlTime;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.ll_address)
    LinearLayout mLlAddress;
    @BindView(R.id.btn_sure)
    Button mButtonSure;

    private String areacode;
    YuyueInfo mYuyueInfo;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_yuyue_course;
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

        mToolbar.setTitle("预约课程");
        mButtonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {


                Http.getDefault().saveYuekeCourse(UserManager.getInsatance().getUser().id, id, startdate, starttime, address, lat, lon)
                        .as(RxHelper.<String>handleResult(mContext))
                        .subscribe(new ResponceSubscriber<String>(mContext) {
                            @Override
                            protected void onSucess(String mS) {
                                EventBus.getDefault().post(new EventMessage.CourseListStatusChange());
                                start(YuyueSuccessActivity.class);
                                finish();
                            }

                            @Override
                            protected void onFail(String message) {
                                showToast(message);
                            }
                        });

            }
        });

        RxTextView.textChanges(mTvTime)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence mCharSequence) throws Exception {
                   mButtonSure.setEnabled(!TextUtils.isEmpty(mCharSequence.toString()));
                    }
                });
        toggleShowLoading(true);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();

        Http.getDefault().getYuyuTime(UserManager.getInsatance().getUser().id, id)
                .as(RxHelper.<YuyueInfo>handleResult(mContext))
                .subscribe(new ResponceSubscriber<YuyueInfo>() {
                    @Override
                    protected void onSucess(YuyueInfo m) {
                        mYuyueInfo = m;
                        setUserData(mYuyueInfo);

                        toggleShowLoading(false);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });

    }

    @OnClick({R.id.ll_time, R.id.ll_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time:


                ARouter.getInstance().build("/member/ChooseYuyueTimeActivity")
                        .withObject("mWeekTimeLists", mYuyueInfo.timelist)
                        .navigation(YuyueCourseActivity.this, 30);

                break;
            case R.id.ll_address:
                startForResult(ChooseLocationActivity.class, 20);


                break;
        }
    }

    private void setUserData(YuyueInfo mUser) {
        if (mUser != null) {
            ImageLoader.load(mContext, mUser.img, mIvHead);
            mTvName.setText(mUser.nickname);
            mTvId.setText("ID" + mUser.no + "");
//            //年龄
//            if (mUser.age != null && mUser.sex != null) {
//                mTvAge.setText(mUser.age + "");
//                if (mUser.sex.equals("1")) {
//                    //男性
//                    mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));
//                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
//                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//                    mTvAge.setCompoundDrawables(drawable, null, null, null);
//                } else {
//                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
//                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//                    mTvAge.setCompoundDrawables(drawable, null, null, null);
//                    mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));
//
//                }
//            } else {
//                mTvAge.setVisibility(View.GONE);
//            }
        }

        mTvCourseType.setText(mUser.ctypename + "(" + mUser.coursetypenames + ")");
        mTvAddress.setText(mUser.address);
    }

    private String address;
    private String lon;
    private String lat;
    String startdate;
    String starttime;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            //经纬度
            if (data != null) {
                PoiItem mPoiItem = (PoiItem) data.getParcelableExtra("data");
                lon = String.valueOf(mPoiItem.getLatLonPoint().getLongitude());
                lat = String.valueOf(mPoiItem.getLatLonPoint().getLatitude());
                address = mPoiItem.getSnippet() + mPoiItem.getTitle();
                mTvAddress.setText(address);
            }
        } else if (requestCode == 30 && resultCode == Activity.RESULT_OK) {

            startdate = data.getStringExtra("startdate");
            starttime = data.getStringExtra("starttime");
            mTvTime.setText(startdate+" "+data.getStringExtra("time"));
        }

    }
}
