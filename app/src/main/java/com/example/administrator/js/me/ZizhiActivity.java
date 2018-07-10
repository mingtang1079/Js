package com.example.administrator.js.me;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.DatePickerDialogUtils;
import com.appbaselib.utils.DateUtils;
import com.appbaselib.utils.DialogUtils;
import com.appbaselib.view.datepicker.view.GregorianLunarCalendarView;
import com.appbaselib.view.datepicker.view.OnDateSelectedListener;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.VerifyUser;
import com.example.administrator.js.me.presenter.ZizhiPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZizhiActivity extends BaseActivity implements ZizhiPresenter.ZizhiResponse {

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
    @BindView(R.id.content)
    LinearLayout mLinearLayout;
    private long time;

    VerifyUser mVerifyUser;
    ZizhiPresenter mZizhiPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_zizhi;
    }

    @Override
    protected View getLoadingTargetView() {
        return mLinearLayout;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("教学资质");
        mZizhiPresenter = new ZizhiPresenter(mContext);

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();
        Http.getDefault().getAuth(UserManager.getInsatance().getUser().id)
                .as(RxHelper.<VerifyUser>handleResult(mContext))
                .subscribe(new ResponceSubscriber<VerifyUser>() {
                    @Override
                    protected void onSucess(VerifyUser mVerifyUser) {
                        ZizhiActivity.this.mVerifyUser = mVerifyUser;
                        setData();
                        toggleShowLoading(false);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });
    }

    private void setData() {

        if (mVerifyUser != null && mVerifyUser.workdate != null)
            mTvTime.setText(mVerifyUser.workdate);


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
                        mZizhiPresenter.updateZizhi("workdate", mCalendarData.getTime());

                    }
                }).show();
                break;
            case R.id.ll_lingyu:
                ARouter.getInstance().build("/me/ShanChangActivity")
                        .withObject("mVerifyUser", mVerifyUser)
                        .navigation();

                break;
            case R.id.ll_anlie:

                ARouter.getInstance().build("/me/SuceessExampleActivity")
                        .withObject("mVerifyUser", mVerifyUser)
                        .navigation();
                break;
            case R.id.ll_zhengshu:

                ARouter.getInstance().build("/me/ZhengshuActivity")
                        .withObject("mVerifyUser", mVerifyUser)
                        .navigation();

                break;
            case R.id.ll_geyan:

                ARouter.getInstance().build("/me/GeyanActivity")
                        .withObject("mVerifyUser", mVerifyUser)
                        .navigation();

                break;
            case R.id.ll_xinxiang:

                ARouter.getInstance().build("/me/XingxiangzhaoActivity")
                        .withObject("mVerifyUser", mVerifyUser)
                        .navigation();
                break;
            case R.id.ll_jinsheng:
                DialogUtils.getDefaultDialog(mContext, "提示", "确定申请晋升？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialogInterface, int mI) {

                        shenqing();

                    }
                }).show();

                break;
            case R.id.ll_jianli:

                ARouter.getInstance().build("/me/JianliActivity")
                        .withObject("mVerifyUser", mVerifyUser)
                        .navigation();
                break;
        }
    }

    private void shenqing() {


    }

    @Override
    public void onSuccess() {
      //  finish();
    }

    @Override
    public void onFail(String mes) {

    }
}
