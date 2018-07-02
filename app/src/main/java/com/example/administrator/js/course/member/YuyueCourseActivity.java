package com.example.administrator.js.course.member;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.utils.AdressHelper;
import com.example.administrator.js.R;
import com.mic.adressselectorlib.City;
import com.mic.adressselectorlib.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class YuyueCourseActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
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
    private String areacode;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_yuyue_course;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {


    }

    @OnClick({R.id.ll_time, R.id.ll_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time:
                break;
            case R.id.ll_address:
                AdressHelper.showAddressSelector(mContext, new OnItemClickListener() {
                    @Override
                    public void itemClick(City mProvice, City mCity, City mCounty) {
                        mTvAddress.setText(mProvice.name + " " + mCity.name + " " + mCounty.name);

                        areacode = mCounty.id;

                    }
                });

                break;
        }
    }
}
