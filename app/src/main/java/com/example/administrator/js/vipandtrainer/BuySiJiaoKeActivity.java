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
import com.amap.api.services.core.PoiItem;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.locaiton.ChooseLocationActivity;
import com.example.administrator.js.base.model.Location;
import com.example.administrator.js.me.model.Collection;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.utils.StringUtils;
import com.example.administrator.js.vipandtrainer.adapter.BigCourse;
import com.example.administrator.js.vipandtrainer.adapter.CourseInfo;
import com.example.administrator.js.vipandtrainer.adapter.CourseType;
import com.example.administrator.js.vipandtrainer.adapter.CourseTypeAdapter;
import com.example.administrator.js.vipandtrainer.trainer.ApplySuccessActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = "/vipandtrainer/BuySiJiaoKeActivity")
public class BuySiJiaoKeActivity extends BaseActivity {

    @Autowired
    String id;//教练id
    @Autowired
    String cardid;//续课用的

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
    TextView mEtCount;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_youhui_price)
    TextView mTvYouhuiPrice;
    @BindView(R.id.tv_all_price)
    TextView mTvAllPrice;
    @BindView(R.id.btn_sure)
    TextView mBtnSure;


    CourseTypeAdapter mCourseTypeAdapterBig;
    CourseTypeAdapter mCourseTypeAdapterSmall;

    BigCourse mBigCourse;//选中的大课
    private String bigType;
    Location mLocation;

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

                initSmallCourse(position);

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

    private void initSmallCourse(int position) {
        mCourseTypeAdapterBig.setSingleChoosed(position);
        mTvCourseName.setText(mBigCourses.get(position).name);
        mTextViewYouhiuInfo.setText("(" + mBigCourses.get(position).onsalelabel + ")");
        mBigCourse = mBigCourses.get(position);
        mTvPrice.setText("￥ " + mBigCourses.get(position).price);
        BigCourse mCourseType = (BigCourse) mCourseTypeAdapterBig.getData().get(position);
        if (mCourseType.list != null) {
            mCourseTypeAdapterSmall.clearSelectedState();
            mCourseTypeAdapterSmall.setNewData2(mCourseType.list);
        }

        caculatePrice();
    }

    List<BigCourse> mBigCourses;

    @Override
    protected void requestData() {
        super.requestData();

//        Http.getDefault().getUser(id)
//                .as(RxHelper.<User>handleResult(mContext))
//                .subscribe(new ResponceSubscriber<User>() {
//                    @Override
//                    protected void onSucess(User mUser) {
//                        setUserData(mUser);
//                    }
//
//                    @Override
//                    protected void onFail(String message) {
//                        loadError();
//                    }
//                });

        Http.getDefault().getcourseinfo(id, cardid)
                .as(RxHelper.<CourseInfo>handleResult(mContext))
                .subscribe(new ResponceSubscriber<CourseInfo>() {
                    @Override
                    protected void onSucess(CourseInfo mCourseTypes) {

                        if (mCourseTypes != null) {
                            mBigCourses = mCourseTypes.ctypes;
//                            if (mBigCourses!=null&&mBigCourses.size()!=0)
//                            {
//                                for (BigCourse m :
//                                        mBigCourses) {
//                                    Collections.reverse(m.onsaledataforapp);
//                                }
//                            }
                            mCourseTypeAdapterBig.setNewData2(mCourseTypes.ctypes);
                            //默认选中第一个
                            if (mCourseTypes.ctypes.size() > 0) {
                                mCourseTypeAdapterBig.setSingleChoosed(0);
                                initSmallCourse(0);

                            }
                            setUserData(mCourseTypes.teacher);
                            //续课地址
                            if (mCourseTypes.location != null) {
                                mTvAddress.setText(mCourseTypes.location.address);
                                mLocation = mCourseTypes.location;
                            } else {
                                mLocation = new Location();
                            }
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
            mTvId.setText("ID：" + mUser.no + "");
            //年龄
            if (mUser.sex != null) {
                if (mUser.age != null) {
                    mTvAge.setText(" " + mUser.age);
                }
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
                    caculatePrice();

                } else {
                    mEtCount.setText("1");
                    caculatePrice();

                }

                break;
            case R.id.tv_add:

                int m2 = Integer.parseInt(mEtCount.getText().toString());
                mEtCount.setText(m2 + 1 + "");
                //计算价格

                caculatePrice();

                break;
            case R.id.btn_sure:

                apply();
                break;
        }
    }

    private void apply() {

        List<String> mStrings = new ArrayList<>();
        for (CourseType mSmallCourse : mCourseTypeAdapterSmall.getSelectedItems()) {
            mStrings.add(((BigCourse.SmallCourse) mSmallCourse).id);
        }
        String mS = StringUtils.listToString(mStrings);

        Http.getDefault().studentsave(id, cardid, UserManager.getInsatance().getUser().id, mBigCourse.id, mS, mEtCount.getText().toString(), mLocation.address, mLocation.clongtitude, mLocation.clatitude)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {

                        start(ApplySuccessActivity.class);
                        finish();
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });


    }

    private void caculatePrice() {
        int mI = Integer.parseInt(mEtCount.getText().toString());
        BigDecimal totalPrice = new BigDecimal(Integer.valueOf(mBigCourse.price) * mI);
        BigDecimal totalPriceCopy = new BigDecimal(Integer.valueOf(mBigCourse.price) * mI);

        if ("1".equals(mBigCourse.onsaletype)) {
            BigDecimal mBigDecimal = new BigDecimal(mBigCourse.onsaledata);
            totalPrice = totalPrice.multiply(mBigDecimal);
            //打折
        } else if ("2".equals(mBigCourse.onsaletype)) {
            //满减
            int priceYouhui = 0;
            if (mBigCourse.onsaledataforapp != null) {
                for (int i = 0; i < mBigCourse.onsaledataforapp.size(); i++) {

                    if (totalPrice.doubleValue() < mBigCourse.onsaledataforapp.get(i).total) {
                        continue;
                    } else {
                        priceYouhui = mBigCourse.onsaledataforapp.get(i).money;
                        break;
                    }
                }
            }
            totalPrice = totalPrice.subtract(new BigDecimal(priceYouhui));

        } else {
            //无优惠
        }
        mTvAllPrice.setText("￥ " + totalPrice);
        mTvYouhuiPrice.setText("-" + totalPriceCopy.subtract(totalPrice).doubleValue());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            //经纬度
            if (data != null) {
                PoiItem mPoiItem = (PoiItem) data.getParcelableExtra("data");
                mLocation.clongtitude = String.valueOf(mPoiItem.getLatLonPoint().getLongitude());
                mLocation.clatitude = String.valueOf(mPoiItem.getLatLonPoint().getLatitude());
                mLocation.address = mPoiItem.getSnippet() + mPoiItem.getTitle();
                mTvAddress.setText(mLocation.address);
            }
        }

    }
}
