package com.example.administrator.js.me.member;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.DatePickerDialogUtils;
import com.appbaselib.utils.DateUtils;
import com.appbaselib.view.datepicker.view.GregorianLunarCalendarView;
import com.appbaselib.view.datepicker.view.OnDateSelectedListener;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.constant.EventMessage;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function5;

@Route(path = "/member/AddBodyDataActivity")
public class AddBodyDataActivity extends BaseActivity {


    @Autowired
    BodyData mBodyData;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_time)
    LinearLayout mLlReason;
    @BindView(R.id.et_tizhong)
    EditText mEtTizhong;
    @BindView(R.id.et_shengao)
    EditText mEtShengao;
    @BindView(R.id.et_tizhi)
    EditText mEtTizhi;
    @BindView(R.id.et_daixie)
    EditText mEtDaixie;
    @BindView(R.id.et_Jirou)
    EditText mEtJirou;
    @BindView(R.id.tv_weidu)
    TextView mTvWeidu;

    //    @BindView(R.id.et_zhifang)
//    EditText mEtZhifang;
    @BindView(R.id.et_bodyzhishu)
    TextView mEtZhishu;

    @BindView(R.id.btn_sure)
    Button mBtnSure;

    String date;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_add_body_data;
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

        mToolbar.setTitle("添加数据");
        if (mBodyData != null) {
            mTvTime.setText(mBodyData.writedate);
            mEtTizhong.setText(mBodyData.weight);
            mEtShengao.setText(mBodyData.height);
            mEtTizhi.setText(mBodyData.fat);
            mEtDaixie.setText(mBodyData.bmr);
            mEtZhishu.setText(mBodyData.bmi);
            mEtJirou.setText(mBodyData.muscle);
            date = mBodyData.writedate;
            mTvTime.setText(date);
        } else {
            date = DateUtils.getCurrentTimeInString();
            mTvTime.setText(date);
        }

        Observable<CharSequence> mObservable1 = RxTextView.textChanges(mEtTizhong);
        Observable<CharSequence> mObservable2 = RxTextView.textChanges(mEtShengao);
        Observable<CharSequence> mObservable3 = RxTextView.textChanges(mEtTizhi);
        Observable<CharSequence> mObservable4 = RxTextView.textChanges(mEtDaixie);
        Observable<CharSequence> mObservable5 = RxTextView.textChanges(mEtJirou);

        Observable.combineLatest(mObservable1, mObservable2, mObservable3, mObservable4, mObservable5,
                new Function5<CharSequence, CharSequence, CharSequence, CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(CharSequence mCharSequence, CharSequence mCharSequence2, CharSequence mCharSequence3, CharSequence mCharSequence4, CharSequence mCharSequence5) throws Exception {

                        return !TextUtils.isEmpty(mCharSequence) || !TextUtils.isEmpty(mCharSequence2) || !TextUtils.isEmpty(mCharSequence3) ||
                                !TextUtils.isEmpty(mCharSequence4) || !TextUtils.isEmpty(mCharSequence5);
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean mBoolean) throws Exception {
                mBtnSure.setEnabled(mBoolean);
            }
        });


    }

    @OnClick({R.id.tv_weidu, R.id.btn_sure, R.id.ll_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_weidu:

                ARouter.getInstance().build("/member/AddWeiduActivity")
                        .withObject("mBodyData", mBodyData)
                        .navigation((Activity) mContext, 20);
                break;
            case R.id.btn_sure:

                save();

                break;
            case R.id.ll_time:

                DatePickerDialogUtils.getDefaultDatePickerDialog5(mContext, new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(GregorianLunarCalendarView.CalendarData mCalendarData) {
                        long selectedTime = DateUtils.getLongTime(mCalendarData.getTimeWithHms());
                        if (selectedTime > DateUtils.getCurrentTime()) {
                            showToast("选择的时间不能大于当前时间");
                        } else {
                            date = mCalendarData.getTimeWithHms();
                            mTvTime.setText(date);

                        }

                    }
                }).show();

                break;
        }
    }

    private void save() {

        Map<String, String> mStringStringMap = new HashMap<>();

        mStringStringMap.put("userid", UserManager.getInsatance().getUser().id);
        mStringStringMap.put("writedate", date);
        if (mBodyData != null && !TextUtils.isEmpty(mBodyData.id)) {
            mStringStringMap.put("id", mBodyData.id);
        }


        if (!TextUtils.isEmpty(mEtTizhong.getText().toString())) {
            mStringStringMap.put("weight", mEtTizhong.getText().toString());
        }
        if (!TextUtils.isEmpty(mEtShengao.getText().toString())) {
            mStringStringMap.put("height", mEtShengao.getText().toString());

        }
        if (!TextUtils.isEmpty(mEtTizhi.getText().toString())) {
            mStringStringMap.put("fat", mEtTizhi.getText().toString());

        }
        if (!TextUtils.isEmpty(mEtDaixie.getText().toString())) {
            mStringStringMap.put("bmr", mEtDaixie.getText().toString());

        }
        if (!TextUtils.isEmpty(mEtJirou.getText().toString())) {
            mStringStringMap.put("muscle", mEtJirou.getText().toString());

        }

//        if (!TextUtils.isEmpty(mEtZhifang.getText().toString())) {
//            mStringStringMap.put("visceralfat", mEtZhifang.getText().toString());
//
//        }
        if (!TextUtils.isEmpty(mEtZhishu.getText().toString())) {
            mStringStringMap.put("bmi", mEtZhishu.getText().toString());

        }

        //第二个页面的数据
        if (mBodyData != null) {
            if (!TextUtils.isEmpty(mBodyData.wdxiong)) {
                mStringStringMap.put("wdxiong", mBodyData.wdxiong);
            }
            if (!TextUtils.isEmpty(mBodyData.wdyao)) {
                mStringStringMap.put("wdyao", mBodyData.wdyao);
            }
            if (!TextUtils.isEmpty(mBodyData.wdxiaotui)) {
                mStringStringMap.put("wdxiaotui", mBodyData.wdxiaotui);
            }
            if (!TextUtils.isEmpty(mBodyData.wddatui)) {
                mStringStringMap.put("wddatui", mBodyData.wddatui);
            }
            if (!TextUtils.isEmpty(mBodyData.wddabi)) {
                mStringStringMap.put("wddabi", mBodyData.wddabi);
            }
            if (!TextUtils.isEmpty(mBodyData.wdxiaobi)) {
                mStringStringMap.put("wdxiaobi", mBodyData.wdxiaobi);
            }
            if (!TextUtils.isEmpty(mBodyData.wdtun)) {
                mStringStringMap.put("wdtun", mBodyData.wdtun);
            }

            //新增加的
            if (!TextUtils.isEmpty(mBodyData.rightwddabi)) {
                mStringStringMap.put("rightwddabi", mBodyData.rightwddabi);
            }
            if (!TextUtils.isEmpty(mBodyData.rightwddatui)) {
                mStringStringMap.put("rightwddatui", mBodyData.rightwddatui);
            }
            if (!TextUtils.isEmpty(mBodyData.rightwdxiaobi)) {
                mStringStringMap.put("rightwdxiaobi", mBodyData.rightwdxiaobi);
            }
            if (!TextUtils.isEmpty(mBodyData.rightwdxiaotui)) {
                mStringStringMap.put("rightwdxiaotui", mBodyData.rightwdxiaotui);
            }

        }


        Http.getDefault().bodydataSave(mStringStringMap)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>() {
                    @Override
                    protected void onSucess(String mS) {
                        EventBus.getDefault().post(new EventMessage.BodyDataListChange());
                        showToast("保存成功");
                        finish();
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            mBodyData = (BodyData) data.getSerializableExtra("data");
        }
    }
}
