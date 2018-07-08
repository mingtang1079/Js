package com.example.administrator.js.vipandtrainer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.DialogUtils;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.course.member.YuyueCourseActivity;
import com.example.administrator.js.login.LoginActivity;
import com.example.administrator.js.me.model.UserDetail;
import com.example.administrator.js.view.SquareLayout;
import com.example.administrator.js.vipandtrainer.adapter.WorkDateAdapter;
import com.example.administrator.js.vipandtrainer.trainer.TrainerDetail;
import com.example.administrator.js.vipandtrainer.trainer.Workdate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;

@Route(path = "/vipandtrainer/TrainerDetailActivity")
public class TrainerDetailActivity extends BaseActivity {


    @Autowired
    String id;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_shanchang)
    TextView mTvShanchang;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.btn_sixin)
    TextView mBtnSixin;
    @BindView(R.id.btn_guanzhu)
    TextView mBtnGuanzhu;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.ll_date_one)
    LinearLayout mLlDateOne;
    @BindView(R.id.ll_date_two)
    RadioGroup mLlDateTwo;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.ll_detail)
    LinearLayout mLlDetail;
    @BindView(R.id.tv_yuyue)
    TextView mTvYuyue;
    @BindView(R.id.view_line)
    View mViewLine;
    @BindView(R.id.tv_goumai)
    TextView mTvGoumai;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.tv_degree)
    TextView mTvDegree;

    TrainerDetail mTrainerDetail;

    MenuItem mMenuItem;

    WorkDateAdapter mWorkDateAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_trainer_detail;
    }

    @Override
    protected View getLoadingTargetView() {
        return mContent;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("教练详情");
        mToolbar.inflateMenu(R.menu.user_detail);
        mMenuItem = mToolbar.getMenu().findItem(R.id.add_hei);
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                if (mMenuItem.getTitle().equals("加入黑名单")) {
                    handleUser("2");
                } else {
                    handleUser("0");

                }
                return false;
            }
        });
        mWorkDateAdapter = new WorkDateAdapter(R.layout.item_work_date, null);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setAdapter(mWorkDateAdapter);
       // mLlDateTwo.removeAllViews();
        mLlDateTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup mRadioGroup, int mI) {

                mWorkDateAdapter.setNewData(mTrainerDetail.workdatelist.get(mI).timelist);
            }
        });
        toggleShowLoading(true);
        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();

        Http.getDefault().teacherDetail(id, UserManager.getInsatance().getUser().id)
                .as(RxHelper.<TrainerDetail>handleResult(mContext))
                .subscribe(new ResponceSubscriber<TrainerDetail>() {
                    @Override
                    protected void onSucess(TrainerDetail m) {
                        if (m != null) {
                            mTrainerDetail = m;
                            setData(mTrainerDetail);
                        }
                        toggleShowLoading(false);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });
    }

    private void setData(final TrainerDetail mTrainerDetail) {
        if (mTrainerDetail.userinfo != null) {
            ImageLoader.load(mContext, mTrainerDetail.userinfo.img, mIvHead);
            mTvName.setText(mTrainerDetail.userinfo.nickname);
            mTvShanchang.setText(mTrainerDetail.userinfo.skillname);
            mTvId.setText("ID" + mTrainerDetail.userinfo.no + "");
            //年龄
            if (mTrainerDetail.userinfo.age != null && mTrainerDetail.userinfo.sex != null) {
                mTvAge.setText(mTrainerDetail.userinfo.age + "");
                if (mTrainerDetail.userinfo.sex.equals("1")) {
                    //男性
                    mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));
                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    mTvAge.setCompoundDrawables(drawable, null, null, null);
                } else {
                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    mTvAge.setCompoundDrawables(drawable, null, null, null);
                    mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));

                }
            } else {
                mTvAge.setVisibility(View.GONE);

            }
            mTvArea.setText(mTrainerDetail.userinfo.address);
            mTvArea.setText(mTrainerDetail.userinfo.address);
        }

        // 与我的关系          //是否接单tradestatus:0否1是, status:0没有关系,1已关注,2已拉黑

        if (mTrainerDetail.relation != null) {


            if (mTrainerDetail.relation.status.equals("2")) {
                mBtnGuanzhu.setVisibility(View.GONE);
                mBtnSixin.setVisibility(View.GONE);
                mMenuItem.setTitle("取消黑名单");
            } else {
                mBtnGuanzhu.setVisibility(View.VISIBLE);
                mMenuItem.setTitle("加入黑名单");
                mBtnSixin.setVisibility(View.VISIBLE);
            }
            //已接单不能拉黑
            if (!TextUtils.isEmpty(mTrainerDetail.relation.tradestatus) && mTrainerDetail.relation.tradestatus.equals("1")) {

                mMenuItem.setVisible(false);

            }

            if (mTrainerDetail.relation.status.equals("1")) {
                mBtnGuanzhu.setText("取消关注");
            } else {
                mBtnGuanzhu.setText("关注");
            }

        }

        //填充日期数据
        if (mTrainerDetail.workdatelist != null && mTrainerDetail.workdatelist.size() != 0) {
            for (int i = 0; i < mTrainerDetail.workdatelist.size(); i++) {

                if (i > 7)
                    return;//虽然不应该大于7
                Workdate mWorkdate = mTrainerDetail.workdatelist.get(i);
                //添加日期
                TextView mTextView = (TextView) mLlDateOne.getChildAt(i);
                if (mTextView != null) {
                    mTextView.setText(mWorkdate.weekindex);
                }
                //  final SquareLayout mSquareLayout = (SquareLayout) mLlDateTwo.getChildAt(i);
                RadioButton mChildAt = (RadioButton) mLlDateTwo.getChildAt(i);
                if (mChildAt != null) {
                    mChildAt.setText(mWorkdate.day);
                    mChildAt.setId(i);
                    if (i == 0) {
                        mChildAt.setChecked(true);
                    }
                }

            }
        }


    }


    @OnClick({R.id.btn_sixin, R.id.btn_guanzhu, R.id.tv_yuyue, R.id.tv_goumai, R.id.content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sixin:

                RongIM.getInstance().startPrivateChat(this, id, mTrainerDetail.userinfo.nickname);

                break;
            case R.id.btn_guanzhu:
//操作 0取消关注,取消拉黑,1关注,2拉黑
                if (mBtnGuanzhu.getText().equals("关注")) {

                    handleUser("1");

                } else if (mBtnGuanzhu.getText().equals("取消关注")) {
                    handleUser("0");

                }
                break;
            case R.id.tv_yuyue:

                DialogUtils.getDefaultDialog(mContext, "提示", "是否要预约体验课?", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialogInterface, int mI) {

                        apply();

                    }
                }).show();

                break;
            case R.id.tv_goumai:

                ARouter.getInstance().build("/vipandtrainer/BuySiJiaoKeActivity")
                        .withString("id", id)
                        .navigation(mContext);

                break;
            case R.id.content:
                break;
        }
    }

    private void apply() {

        Map<String, Object> mMap = new HashMap<>();
        mMap.put("tid", id);
        mMap.put("uid", UserManager.getInsatance().getUser().id);
        mMap.put("tryflag", "1");

        Http.getDefault().applyYuyueke(mMap)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {
                        showToast("成功预约体验课！");
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

    private void handleUser(String mS) {
        Http.getDefault().handleUser(UserManager.getInsatance().getUser().id, id, mS)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {

                        if (mTrainerDetail.relation == null) {
                            mTrainerDetail.relation = new UserDetail.Relation();
                        }
                        mTrainerDetail.relation.status = mS;
                        setData(mTrainerDetail);

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });


    }
}
