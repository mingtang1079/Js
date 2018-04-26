package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.utils.DatePickerDialogUtils;
import com.appbaselib.utils.DateUtils;
import com.appbaselib.view.datepicker.view.GregorianLunarCalendarView;
import com.appbaselib.view.datepicker.view.OnDateSelectedListener;
import com.example.administrator.js.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZizhiActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_time)
    LinearLayout mLlTime;
    @BindView(R.id.ll_lingyu)
    LinearLayout mLlLingyu;
    @BindView(R.id.ll_anlie)
    LinearLayout mLlAnlie;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.ll_zhengshu)
    LinearLayout mLlZhengshu;
    @BindView(R.id.ll_geyan)
    LinearLayout mLlGeyan;
    @BindView(R.id.ll_xinxiang)
    LinearLayout mLlXinxiang;
    @BindView(R.id.ll_jinsheng)
    LinearLayout mLlJinsheng;
    @BindView(R.id.ll_jianli)
    LinearLayout mLlJianli;
    private long time;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_zizhi;
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

        mToolbar.setTitle("资质教学");

    }

    @OnClick({R.id.ll_time, R.id.ll_lingyu, R.id.ll_anlie, R.id.ll_zhengshu, R.id.ll_geyan, R.id.ll_xinxiang, R.id.ll_jinsheng, R.id.ll_jianli})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time:

                DatePickerDialogUtils.getDefaultDatePickerDialog(mContext, new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(GregorianLunarCalendarView.CalendarData mCalendarData) {
                        time = DateUtils.getTimeLongYmd(mCalendarData.getTime());
                        mTvTime.setText(mCalendarData.getTime());
                    }
                }).show();
                break;
            case R.id.ll_lingyu:
                start(ShanChangActivity.class);

                break;
            case R.id.ll_anlie:
                break;
            case R.id.ll_zhengshu:
                break;
            case R.id.ll_geyan:
                break;
            case R.id.ll_xinxiang:
                break;
            case R.id.ll_jinsheng:
                break;
            case R.id.ll_jianli:
                break;
        }
    }
}
