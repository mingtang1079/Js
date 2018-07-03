package com.example.administrator.js.vipandtrainer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.activity.locaiton.ChooseLocationActivity;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.vipandtrainer.adapter.BigCourse;
import com.example.administrator.js.vipandtrainer.adapter.CourseType;
import com.example.administrator.js.vipandtrainer.adapter.CourseTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = "/vipandtrainer/BuySiJiaoKeActivity")
public class BuySiJiaoKeActivity extends BaseActivity {

    @Autowired
    String id;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_choose_price)
    TextView mTvChoosePrice;
    @BindView(R.id.recyclerview_type)
    RecyclerView mRecyclerviewType;
    @BindView(R.id.recyclerview_small_type)
    RecyclerView mRecyclerviewSmallType;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.ll_address)
    LinearLayout mLlAddress;
    @BindView(R.id.tv_course_name)
    TextView mTvCourseName;
    @BindView(R.id.tv_youhui_info)
    TextView mTextViewYouhiuInfo;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_jian)
    TextView mTvJian;
    @BindView(R.id.et_count)
    EditText mEtCount;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_youhui_price)
    TextView mTvYouhuiPrice;
    @BindView(R.id.tv_all_price)
    TextView mTvAllPrice;
    @BindView(R.id.btn_sure)
    Button mBtnSure;


    CourseTypeAdapter mCourseTypeAdapterBig;
    CourseTypeAdapter mCourseTypeAdapterSmall;

    int youhuiType;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_buy_si_jiao_ke;
    }

    @Override
    protected View getLoadingTargetView() {
        return mNestedScrollView;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("购买私教课");

        GridLayoutManager mLinearLayoutManager = new GridLayoutManager(mContext, 3);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mCourseTypeAdapterBig = new CourseTypeAdapter(R.layout.item_course_type, new ArrayList<CourseType>());
        mCourseTypeAdapterBig.isSingleChoose = true;
        mRecyclerviewType.setLayoutManager(mLinearLayoutManager);
        mRecyclerviewType.setAdapter(mCourseTypeAdapterBig);
        mCourseTypeAdapterBig.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                mCourseTypeAdapterBig.setSingleChoosed(position);
                mTvCourseName.setText(mBigCourses.get(position).name);
                mTextViewYouhiuInfo.setText("(" + mBigCourses.get(position).onsalelabel + ")");
                mTvPrice.setText("￥ " + mBigCourses.get(position).price);
                youhuiType = Integer.parseInt(mBigCourses.get(position).onsaletype);
                BigCourse mCourseType = (BigCourse) mCourseTypeAdapterBig.getData().get(position);
                if (mCourseType.list != null) {
                    mCourseTypeAdapterSmall.setNewData2(mCourseType.list);
                }

            }
        });


        GridLayoutManager mLinearLayoutManager1 = new GridLayoutManager(mContext, 3);
        mLinearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mCourseTypeAdapterSmall = new CourseTypeAdapter(R.layout.item_course_type, new ArrayList<CourseType>());
        mRecyclerviewSmallType.setLayoutManager(mLinearLayoutManager1);
        mRecyclerviewSmallType.setAdapter(mCourseTypeAdapterSmall);
        mCourseTypeAdapterSmall.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mCourseTypeAdapterSmall.switchSelectedState(position);
            }
        });


        mEtCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                if (TextUtils.isEmpty(mCharSequence)) {
                    mEtCount.setText("1");
                }
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });

        toggleShowLoading(true);
        requestData();
    }

    List<BigCourse> mBigCourses;

    @Override
    protected void requestData() {
        super.requestData();

        Http.getDefault().getUser(id)
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>() {
                    @Override
                    protected void onSucess(User mUser) {
                        setUserData(mUser);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });

        Http.getDefault().getcourseinfo(id)
                .as(RxHelper.<List<BigCourse>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<List<BigCourse>>() {
                    @Override
                    protected void onSucess(List<BigCourse> mCourseTypes) {

                        if (mCourseTypes != null) {
                            mBigCourses = mCourseTypes;
                            mCourseTypeAdapterBig.setNewData2(mCourseTypes);
                        }
                        toggleShowLoading(false);

                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });

    }

    private void setUserData(User mUser) {

        if (mUser != null) {
            ImageLoader.load(mContext, mUser.img, mIvHead);
            mTvName.setText(mUser.nickname);
            mTvId.setText(mUser.no + "");
            //年龄
            if (mUser.age != null && mUser.sex != null) {
                mTvAge.setText(mUser.age + "");
                if (mUser.sex.equals("1")) {
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
        }
    }

    private void setData(User mData) {


    }

    @OnClick({R.id.ll_address, R.id.tv_jian, R.id.tv_add, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:

                startForResult(ChooseLocationActivity.class, 20);

                break;
            case R.id.tv_jian:
                int m = Integer.parseInt(mEtCount.getText().toString());
                if (m > 1) {
                    mEtCount.setText(m - 1 + "");
                } else {
                    mEtCount.setText("1");

                }
                break;
            case R.id.tv_add:

                int m2 = Integer.parseInt(mEtCount.getText().toString());
                mEtCount.setText(m2 + 1 + "");
                //计算价格



                break;
            case R.id.btn_sure:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            //设置经纬度


        }

    }
}
