package com.example.administrator.js.me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.me.model.ShenheInfo;
import com.example.administrator.js.me.model.VerifyUser;
import com.example.administrator.js.me.presenter.ZizhiPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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
    @BindView(R.id.ll_yaoqingma)
    LinearLayout mLinearLayoutYao;

    @BindView(R.id.tv_jinshen)
    TextView mTextViewJinshen;

    VerifyUser mVerifyUser;
    ZizhiPresenter mZizhiPresenter;

    MenuItem mMenuItem;
    private boolean isFinish = false;

    protected void initMenu() {
        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("保存");
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {

                //必须有四个值，时间，擅长哪个，资助证书，形象照,+j简历口述

                isFinish = true;
                mZizhiPresenter.updateZizhi("teachstatus", "3");

                return true;
            }
        });


    }

    private void panDuanButton() {

        if (mVerifyUser == null) {
            mMenuItem.setEnabled(false);
            return;
        }

        if (!TextUtils.isEmpty(mVerifyUser.workdate) && !
                TextUtils.isEmpty(mVerifyUser.skillname) && !
                TextUtils.isEmpty(mVerifyUser.certpath) && !
                TextUtils.isEmpty(mVerifyUser.photopath) && !
                TextUtils.isEmpty(mVerifyUser.intro)) {
            mMenuItem.setEnabled(true);
        } else {
            mMenuItem.setEnabled(false);

        }
    }

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
        initMenu();
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
                        panDuanButton();
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });

        //晋升状态
        Http.getDefault().getPromotion(UserManager.getInsatance().getUser().id)
                .as(RxHelper.<ShenheInfo>handleResult(mContext))
                .subscribe(new ResponceSubscriber<ShenheInfo>() {
                    @Override
                    protected void onSucess(ShenheInfo mShenheInfo) {

                        if (mShenheInfo != null) {

                            if (!TextUtils.isEmpty(mShenheInfo.promotionstatus)) {
                                if ("3".equals(mShenheInfo.promotionstatus)) {
                                    mTextViewJinshen.setText("审核中");
                                } else if ("2".equals(mShenheInfo.promotionstatus)) {
                                    //未审核
                                    mTextViewJinshen.setText("审核不通过");

                                } else if ("1".equals(mShenheInfo.promotionstatus)) {
                                    //未审核
                                    mTextViewJinshen.setText("审核通过");

                                }
                            }

                        }

                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });

    }

    private void setData() {

        if (mVerifyUser != null && mVerifyUser.workdate != null)
            mTvTime.setText(mVerifyUser.workdate.substring(0, mVerifyUser.workdate.lastIndexOf("-")));


    }

    @OnClick({R.id.ll_time, R.id.ll_lingyu, R.id.ll_anlie, R.id.ll_zhengshu, R.id.ll_geyan, R.id.ll_xinxiang, R.id.ll_jinsheng, R.id.ll_jianli, R.id.ll_yaoqingma})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time:

                DatePickerDialogUtils.getDefaultDatePickerDialog3(mContext, new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(GregorianLunarCalendarView.CalendarData mCalendarData) {
                        time = DateUtils.getTimeLongYmd(mCalendarData.getTime());
                        mTvTime.setText(mCalendarData.pickedYear + "-" + mCalendarData.pickedMonthSway);
                        isFinish = false;
                        mZizhiPresenter.updateZizhi("workdate", mCalendarData.pickedYear + "-" + mCalendarData.pickedMonthSway);

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
                android.support.v7.app.AlertDialog.Builder m = new android.support.v7.app.AlertDialog.Builder(mContext);
                m.setTitle("提示");
                m.setMessage("确定申请晋升？");
                m.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialogInterface, int mI) {
                        mDialogInterface.dismiss();
                    }
                });
                m.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialogInterface, int mI) {
                        shenqing();

                    }
                });
                m.setNeutralButton("申请原则", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialogInterface, int mI) {

                        ARouter.getInstance().build("/web/Html5Activity")
                                .withString("url", "https://www.cdmuscle.com/h5/news/detail?id=yuanze&userid=" + UserManager.getInsatance().getUser().id)
                                .navigation(mContext);
                    }
                });
                m.create().show();

                break;
            case R.id.ll_jianli:

                ARouter.getInstance().build("/me/JianliActivity")
                        .withObject("mVerifyUser", mVerifyUser)
                        .navigation();
                break;

            case R.id.ll_yaoqingma:


                ARouter.getInstance().build("/me/YaoqingmaActivity")
                        .withObject("mVerifyUser", mVerifyUser)
                        .navigation();

                break;
        }
    }

    private void tianxie() {

//0
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        View mView = LayoutInflater.from(mContext).inflate(R.layout.view_input, null, false);
        final TextInputEditText mTextInputEditText = mView.findViewById(R.id.et);
        mBuilder.setTitle("提示");
        mTextInputEditText.setHint("请填写邀请码");
        mTextInputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
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

                isFinish = false;
                mZizhiPresenter.updateZizhi("invitecode", mTextInputEditText.getText().toString());

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


    private void shenqing() {

        if ("审核中".equals(mTextViewJinshen.getText().toString())) {
            return;
        } else {


            Http.getDefault().promotionSave(UserManager.getInsatance().getUser().id)
                    .as(RxHelper.<ShenheInfo>handleResult(mContext))
                    .subscribe(new ResponceSubscriber<ShenheInfo>(mContext) {
                        @Override
                        protected void onSucess(ShenheInfo mShenheInfo) {

                            mTextViewJinshen.setText("审核中");

                        }

                        @Override
                        protected void onFail(String message) {
                            showToast(message);
                        }
                    });
        }

    }

    @Override
    public void onSuccess() {
        if (isFinish) {
            finish();
        }
    }

    @Override
    public void onFail(String mes) {

    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatusChange(EventMessage.ZizhiStatus mZizhiStatus) {
        mMenuItem.setEnabled(true);
    }
}
