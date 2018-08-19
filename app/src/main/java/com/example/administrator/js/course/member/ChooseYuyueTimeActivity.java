package com.example.administrator.js.course.member;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseRecyclerViewAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.js.R;
import com.ldf.calendar.view.Week;

import java.util.List;

import butterknife.BindView;

@Route(path = "/member/ChooseYuyueTimeActivity")
public class ChooseYuyueTimeActivity extends BaseActivity {

    @Autowired
    List<WeekTimeList> mWeekTimeLists;


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_sure)
    Button mButtonSure;
    @BindView(R.id.tv_name)
    TextView mTextViewName;

    ItemAdapter mItemAdapter;
    Integer position = null;//第几列
    int p1;
    int p2;
    String startdate;
    String starttime;
    String time;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_choose_yuyue_time;
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

        mToolbar.setTitle("选择时间");
        mItemAdapter = new ItemAdapter(R.layout.item_recycleview, mWeekTimeLists);
        final LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);// 将SnapHelper attach 到RecyclrView
        mRecyclerView.setAdapter(mItemAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mButtonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                Intent intent = new Intent();
                intent.putExtra("startdate", startdate);
                intent.putExtra("starttime", starttime);
                intent.putExtra("time", time);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int p = manager.findLastCompletelyVisibleItemPosition();
                    if (p != -1 && p < mWeekTimeLists.size()) {
                        mTextViewName.setText("周"+mWeekTimeLists.get(p).weekindex +" "+ mWeekTimeLists.get(p).month + "." + mWeekTimeLists.get(p).day);
                    }

                }
            }
        });
        mTextViewName.setText("周"+mWeekTimeLists.get(0).weekindex + mWeekTimeLists.get(0).month + "." + mWeekTimeLists.get(0).day);
    }


    public class ViewAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, final int p) {
            RecyclerView mRecyclerView = new RecyclerView(mContext);

            final TimeChooseAdapter mTimeChooseAdapter = new TimeChooseAdapter(R.layout.item_choose_time, mWeekTimeLists.get(p).timelist);
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 2);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mRecyclerView.setAdapter(mTimeChooseAdapter);

            container.addView(mRecyclerView);
            return mRecyclerView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return mWeekTimeLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            WeekTimeList mWeekTimeList = mWeekTimeLists.get(position);
            return "周" + mWeekTimeList.weekindex + " " + mWeekTimeList.month + "." + mWeekTimeList.day;
        }
    }


    public class TimeChooseAdapter extends BaseRecyclerViewAdapter<DayTimeList> {

        public Integer p;

        public TimeChooseAdapter(int layoutResId, List<DayTimeList> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DayTimeList item) {

            helper.setText(R.id.tv_name, item.time);
            TextView mTextView = helper.getView(R.id.tv_name);
            if ("0".equals(item.status)) {
                mTextView.setEnabled(true);
            } else {
                mTextView.setEnabled(false);

            }
            if (position != null && position == p)
                if (helper.getAdapterPosition() == p1 || helper.getAdapterPosition() == p2) {
                    mTextView.setSelected(true);
                } else {
                    mTextView.setSelected(false);
                }

        }

    }

    public class ItemAdapter extends BaseRecyclerViewAdapter<WeekTimeList> {


        public ItemAdapter(int layoutResId, List<WeekTimeList> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(final BaseViewHolder helper, WeekTimeList item) {
            RecyclerView mRecyclerView = helper.getView(R.id.recyclerview);
            final TimeChooseAdapter mTimeChooseAdapter = new TimeChooseAdapter(R.layout.item_choose_time, item.timelist);
            mTimeChooseAdapter.p = helper.getAdapterPosition();
            GridLayoutManager mGridLayoutManager = new GridLayoutManager(mContext, 3);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mRecyclerView.setAdapter(mTimeChooseAdapter);
            mTimeChooseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

//0可选 1不可选
                    ChooseYuyueTimeActivity.this.position = helper.getAdapterPosition();

                    startdate = mTimeChooseAdapter.getData().get(position).date;
                    time = mTimeChooseAdapter.getData().get(position).time;
                    String mS = mTimeChooseAdapter.getData().get(position).starttime;//starttime前半段
                    String bortherTime = mTimeChooseAdapter.getData().get(position).brothertime;
                    String mS1 = null;//starttime后半段
                    p1 = position;
                    for (int i = 0; i < mTimeChooseAdapter.getData().size(); i++) {
                        DayTimeList mDayTime = mTimeChooseAdapter.getData().get(i);
                        if (mDayTime.time.equals(bortherTime)) {
                            mS1 = mDayTime.starttime;
                            p2 = i;
                            break;
                        }
                    }
                    starttime = mS + "," + mS1;
                    mItemAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
