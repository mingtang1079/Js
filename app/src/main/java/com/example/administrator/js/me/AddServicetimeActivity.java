package com.example.administrator.js.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

        if (mServiceTime == null) {
            mToolbar.setTitle("添加服务时间");
        } else {
            mToolbar.setTitle("编辑服务时间");

        }

        mToolbar.inflateMenu(R.menu.toolbar_menu_common);
        mMenuItem = mToolbar.getMenu().findItem(R.id.btn_common);
        mMenuItem.setTitle("保存");

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
                if (mXingqi.name.equals("星期一")) {
                    mMap.put("day1", "1");
                }
                if (mXingqi.name.equals("星期二")) {
                    mMap.put("day2", "1");
                }
                if (mXingqi.name.equals("星期三")) {
                    mMap.put("day3", "1");
                }
                if (mXingqi.name.equals("星期四")) {
                    mMap.put("day4", "1");
                }
                if (mXingqi.name.equals("星期五")) {
                    mMap.put("day5", "1");
                }
                if (mXingqi.name.equals("星期六")) {
                    mMap.put("day6", "1");
                }
                if (mXingqi.name.equals("星期日")) {
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

    List<Xingqi> mXingqis;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {

            mXingqis = (List<Xingqi>) data.getSerializableExtra("data");
            if (mXingqis != null) {
                StringBuilder mStringBuilder = new StringBuilder();
                for (int i = 0; i < mXingqis.size(); i++) {
                    mStringBuilder.append(mXingqis.get(i).name + " ");
                    mTvDays.setText(mStringBuilder.toString());

                }

            }

        }
    }


}
