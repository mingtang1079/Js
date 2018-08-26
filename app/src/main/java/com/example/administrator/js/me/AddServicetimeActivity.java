package com.example.administrator.js.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.example.administrator.js.me.model.ServiceTime;
import com.example.administrator.js.me.model.Xingqi;
import com.example.administrator.js.utils.MyDateUtils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

@Route(path = "/me/AddServicetimeActivity")
public class AddServicetimeActivity extends BaseActivity {

    @Autowired
    ServiceTime mServiceTime;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.tv_days)
    TextView mTvDays;

    String startTime;
    String endTime;

    MenuItem mMenuItem;
    List<Xingqi> mXingqis;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_add_servicetime;
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

        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("保存");

        if (mServiceTime == null) {
            mToolbar.setTitle("添加服务时间");
            mMenuItem.setEnabled(false);
        } else {
            mMenuItem.setEnabled(true);
            mToolbar.setTitle("编辑服务时间");
            mTvStartTime.setText(mServiceTime.starttime);
            mTvEndTime.setText(mServiceTime.endtime);
//组装数据....
            startTime = mServiceTime.starttime;
            endTime = mServiceTime.endtime;

            mXingqis = new ArrayList<>();
            if ("1".equals(mServiceTime.day1))
                mXingqis.add(new Xingqi("周一"));
            if ("1".equals(mServiceTime.day2))
                mXingqis.add(new Xingqi("周二"));
            if ("1".equals(mServiceTime.day3))
                mXingqis.add(new Xingqi("周三"));
            if ("1".equals(mServiceTime.day4))
                mXingqis.add(new Xingqi("周四"));
            if ("1".equals(mServiceTime.day5))
                mXingqis.add(new Xingqi("周五"));
            if ("1".equals(mServiceTime.day6))
                mXingqis.add(new Xingqi("周六"));
            if ("1".equals(mServiceTime.day7))
                mXingqis.add(new Xingqi("周日"));

            convert();
        }


        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                if (mMenuItem.getItemId() == R.id.btn_common) {

                    int s1 = Integer.parseInt(startTime.replace(":", ""));
                    int s2 = Integer.parseInt(endTime.replace(":", ""));
                    if (s2 - s1 >= 100) {
                        save();
                    } else {
                        showToast("时间至少间隔一小时哦");
                    }
                }
                return true;
            }
        });

        Observable<CharSequence> mObservablePhon1 = RxTextView.textChanges(mTvStartTime);
        Observable<CharSequence> mObservablePhon2 = RxTextView.textChanges(mTvEndTime);
        Observable<CharSequence> mObservablePhon3 = RxTextView.textChanges(mTvDays);
        Observable.combineLatest(mObservablePhon1, mObservablePhon2, mObservablePhon3, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence mCharSequence, CharSequence mCharSequence2, CharSequence mCharSequence3) throws Exception {
                return !TextUtils.isEmpty(mCharSequence.toString()) && !TextUtils.isEmpty(mCharSequence2) && !TextUtils.isEmpty(mCharSequence3);
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean mBoolean) throws Exception {
                mMenuItem.setEnabled(mBoolean);
            }
        });

    }

    private void save() {

        Map<String, String> mMap = new HashMap<>();
        mMap.put("tid", UserManager.getInsatance().getUser().id);

        if (mServiceTime != null) {
            mMap.put("id", mServiceTime.id);

        }
        mMap.put("starttime", startTime);
        mMap.put("endtime", endTime);

        if (mXingqis == null) {
            showToast("请选择星期");
            return;
        } else {

            for (Xingqi mXingqi : mXingqis) {
                if (mXingqi.name.equals("周一")) {
                    mMap.put("day1", "1");
                }
                if (mXingqi.name.equals("周二")) {
                    mMap.put("day2", "1");
                }
                if (mXingqi.name.equals("周三")) {
                    mMap.put("day3", "1");
                }
                if (mXingqi.name.equals("周四")) {
                    mMap.put("day4", "1");
                }
                if (mXingqi.name.equals("周五")) {
                    mMap.put("day5", "1");
                }
                if (mXingqi.name.equals("周六")) {
                    mMap.put("day6", "1");
                }
                if (mXingqi.name.equals("周日")) {
                    mMap.put("day7", "1");
                }
            }
        }


        Http.getDefault().servicetimeSaveAndAlter(mMap)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {
                        showToast("保存成功！");
                        finish();
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);

                    }
                });


    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.ll_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start_time:


                MyDateUtils.getDefaultDatePickerDialog2(mContext, new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(GregorianLunarCalendarView.CalendarData mCalendarData) {
                        startTime = mCalendarData.hour + ":" + mCalendarData.minute2;
                        mTvStartTime.setText(startTime);


                    }
                }).show();
                break;
            case R.id.tv_end_time:

                MyDateUtils.getDefaultDatePickerDialog2(mContext, new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(GregorianLunarCalendarView.CalendarData mCalendarData) {
                        endTime = mCalendarData.hour + ":" + mCalendarData.minute2;

                        mTvEndTime.setText(endTime);

                    }
                }).show();

                break;
            case R.id.ll_add:

                startForResult(ChooseDayActivity.class, 20);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {

            mXingqis = (List<Xingqi>) data.getSerializableExtra("data");
            convert();

        }
    }

    private void convert() {
        if (mXingqis != null) {
            StringBuilder mStringBuilder = new StringBuilder();
            for (int i = 0; i < mXingqis.size(); i++) {
                mStringBuilder.append(mXingqis.get(i).name + " ");
                mTvDays.setText(mStringBuilder.toString());

            }

        }
    }


}
