package com.example.administrator.js.course;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.DateUtils;
import com.appbaselib.utils.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.view.CustomDayView;
import com.example.administrator.js.view.ThemeDayView;
import com.example.administrator.js.vipandtrainer.adapter.CourseUserAdapter;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class CourseCanlenderActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.tv_yue)
    TextView mTvYue;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.calendar_view)
    MonthPager mCalendarView;
    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.content)
    CoordinatorLayout mContent;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private CalendarDate currentDate;
    private boolean initiated = false;


    CourseUserAdapter mCourseUserAdapter;

    String currenttime;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_course_canlender;
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

        mToolbar.setTitle("我的日程");
        mCalendarView.setViewHeight(ScreenUtils.dp2px(mContext, 270));
        mList.setHasFixedSize(true);
        //这里用线性显示 类似于listview
        mList.setLayoutManager(new LinearLayoutManager(this));
        mCourseUserAdapter = new CourseUserAdapter(R.layout.item_course_user, null);
        mCourseUserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build("/course/CourDetailActivity")
                        .withString("id",mCourseUserAdapter.getData().get(position).id)
                        .navigation(mContext);
            }
        });
        mList.setAdapter(mCourseUserAdapter);

        initCurrentDate();
        initCalendarView();

        View mView = LayoutInflater.from(mContext).inflate(R.layout.view_header_course, mList, false);
        mCourseUserAdapter.addHeaderView(mView);

        mIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarView.setCurrentItem(mCalendarView.getCurrentPosition() + 1);
            }
        });
        mIvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarView.setCurrentItem(mCalendarView.getCurrentPosition() - 1);
            }
        });
        currenttime= DateUtils.getCurrentTimeYmd();
        requestData();
    }

    @Override
    public void requestData() {

        Http.getDefault().getCourse(UserManager.getInsatance().getUser().id, "1", 1,currenttime)
                .as(RxHelper.<WrapperModel<User>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<User>>(mContext) {
                    @Override
                    protected void onSucess(WrapperModel<User> mVipUserBaseModelWrapper) {
                        if (mVipUserBaseModelWrapper != null) {
                            mCourseUserAdapter.setNewData(mVipUserBaseModelWrapper.list);
                        }

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });


    }

    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        mTvYue.setText(currentDate.getMonth() + "");
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        initListener();
        ThemeDayView themeDayView = new ThemeDayView(mContext, R.layout.custom_day_focus);
        CustomDayView customDayView = new CustomDayView(mContext, R.layout.custom_day);


        calendarAdapter = new CalendarViewAdapter(mContext, onSelectDateListener, CalendarAttr.CalendarType.MONTH, CalendarAttr.WeekArrayType.Monday, customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(new CalendarViewAdapter.OnCalendarTypeChanged() {
            @Override
            public void onCalendarTypeChanged(CalendarAttr.CalendarType type) {
                mList.scrollToPosition(0);
            }
        });
        //  initMarkData();
        initMonthPager();
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     * 如果存在异步的话，在使用setMarkData之后调用 calendarAdapter.notifyDataChanged();
     */
    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        markData.put("2017-8-9", "1");
        markData.put("2017-7-9", "0");
        markData.put("2017-6-9", "1");
        markData.put("2017-6-10", "0");
        calendarAdapter.setMarkData(markData);
    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
                currenttime=date.year+"-"+date.month+"-"+date.day;
                requestData();
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                mCalendarView.selectOtherMonth(offset);
            }
        };
    }


    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        mTvYue.setText(date.getMonth() + "月");
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        mCalendarView.setAdapter(calendarAdapter);
        mCalendarView.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        mCalendarView.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        mCalendarView.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    mTvYue.setText(date.getMonth() + "月");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
     *
     * @return void
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }

    public void onClickBackToDayBtn() {
        refreshMonthPager();
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
        mTvYue.setText(today.getMonth() + "月");
    }

    private void refreshSelectBackground() {
        ThemeDayView themeDayView = new ThemeDayView(mContext, R.layout.custom_day_focus);
        calendarAdapter.setCustomDayRenderer(themeDayView);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter.notifyDataChanged(new CalendarDate());
    }
}
