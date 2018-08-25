package com.example.administrator.js.exercise.member;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.adapter.ObjectAdapter;
import com.appbaselib.base.BaseRefreshActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.BottomDialogUtils;
import com.appbaselib.utils.JsonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.LocationManager;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.ItemAdapter;
import com.example.administrator.js.activity.ItemGridDividerItemDecoration;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.DuoxuanTypeAdapter;
import com.example.administrator.js.exercise.adapter.TypeAdapter;
import com.example.administrator.js.exercise.model.Skill;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

@Route(path = "/exercise/NearbyTrainerActivity")
public class NearbyTrainerActivity extends BaseRefreshActivity<User> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_search)
    TextView mTextViewSearch;
    @BindView(R.id.zonghe)
    TextView mZonghe;
    @BindView(R.id.juli)
    TextView mJuli;

    @BindView(R.id.shaixuan)
    TextView mShaixuan;


    int distance;
    String degree;
    String sex;
    String skillids;
    String beginprice;
    String endprice;
    String orderby;

    List<Skill> mSkills = new ArrayList<>();


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_nearby_trainer;
    }

    @Override
    protected void initView() {
        super.initView();


        mSkills = JsonUtil.fromJsonList("[{\n" +
                "id: \"a01\",\n" +
                "name: \"增肌\"\n" +
                "}, {\n" +
                "id: \"a02\",\n" +
                "name: \"减脂\"\n" +
                "}, {\n" +
                "id: \"a03\",\n" +
                "name: \"塑形\"\n" +
                "}, {\n" +
                "id: \"b01\",\n" +
                "name: \"功能性\"\n" +
                "}, {\n" +
                "id: \"b02\",\n" +
                "name: \"康复\"\n" +
                "}, {\n" +
                "id: \"b03\",\n" +
                "name: \"体态修正\"\n" +
                "}, {\n" +
                "id: \"b04\",\n" +
                "name: \"拉伸\"\n" +
                "}, {\n" +
                "id: \"b05\",\n" +
                "name: \"搏击\"\n" +
                "}, {\n" +
                "id: \"c01\",\n" +
                "name: \"竞技健美\"\n" +
                "}]", Skill.class);

        requestData();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    public void initAdapter() {
        mAdapter = new NearbyTrainerAdapter(R.layout.item_nearby_trainer, mList);
        setLoadMoreListener();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build("/vipandtrainer/TrainerDetailActivity")
                        .withString("id", mList.get(position).id)
                        .navigation(mContext);
            }
        });
    }

    @Override
    public void requestData() {


        Map<String, Object> mStringStringMap = new HashMap<>();
        mStringStringMap.put("id", UserManager.getInsatance().getUser().id);
        if (!TextUtils.isEmpty(LocationManager.getInsatance().longitude)) {
            mStringStringMap.put("longitude", LocationManager.getInsatance().longitude);
        }
        if (!TextUtils.isEmpty(LocationManager.getInsatance().latitude)) {
            mStringStringMap.put("latitude", LocationManager.getInsatance().latitude);
        }
        if (!TextUtils.isEmpty(orderby)) {
            mStringStringMap.put("orderby", orderby);
        }
        if (distance != 0) {
            mStringStringMap.put("distance", distance);
        }
        if (!TextUtils.isEmpty(skillids)) {
            mStringStringMap.put("skillids", skillids);
        }

        if (!TextUtils.isEmpty(sex)) {
            mStringStringMap.put("sex", sex);
        }
        if (!TextUtils.isEmpty(beginprice)) {
            mStringStringMap.put("beginprice", beginprice);
        }
        if (!TextUtils.isEmpty(endprice)) {
            mStringStringMap.put("endprice", endprice);
        }
        if (!TextUtils.isEmpty(degree)) {
            mStringStringMap.put("degree", degree);
        }
        mStringStringMap.put("pageNo", pageNo);
        Http.getDefault().seacrchUser(mStringStringMap)
                .as(RxHelper.<WrapperModel<VipUser>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<VipUser>>() {
                    @Override
                    protected void onSucess(WrapperModel<VipUser> mNearbyVipWrapperModel) {

                        loadComplete(mNearbyVipWrapperModel.list);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });

    }

    @OnClick({R.id.zonghe, R.id.juli, R.id.shaixuan, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zonghe:

                showZonghe();


                break;
            case R.id.juli:

                showJuli();

                break;

            case R.id.shaixuan:
                showXiangShaixuan();
                break;

            case R.id.tv_search:
                start(SearchTrianerActivity.class);
                break;
        }
    }

    int p1 = -1;
    int p2 = -1;
    int p3 = -3;
    List<Integer> mIntegers = new ArrayList<>();

    private void showXiangShaixuan() {
        View mView = getLayoutInflater().inflate(R.layout.view_trainer_shaixuan, null, false);
        final BottomSheetDialog mBottomSheetDialog = BottomDialogUtils.showBottomDialog(mContext, mView);

        //展开
        BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) mView.getParent());
        mDialogBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//展开
//----------------

        final EditText mEditText = mView.findViewById(R.id.et_pricedi);
        mEditText.setText(beginprice);
        final EditText mEditText2 = mView.findViewById(R.id.et_pricegao);
        mEditText2.setText(endprice);
        RxTextView.textChanges(mEditText)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence mCharSequence) throws Exception {
                        beginprice = mCharSequence.toString();
                    }
                });
        RxTextView.textChanges(mEditText2)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence mCharSequence) throws Exception {
                        endprice = mCharSequence.toString();
                    }
                });

        //button
        TextView mTextView = mView.findViewById(R.id.chongzhi);
        TextView mTextView1 = mView.findViewById(R.id.sure);


        final List<String> mItems = new ArrayList<>();
        mItems.add("男");
        mItems.add("女");
        RecyclerView mRecyclerView = mView.findViewById(R.id.recyclerview_sex);
        final TypeAdapter mItemAdapter;
        mItemAdapter = new TypeAdapter(R.layout.item_xiangmu, mItems);
        GridLayoutManager mFlexboxLayoutManager = new GridLayoutManager(mContext, 3);
        mRecyclerView.setLayoutManager(mFlexboxLayoutManager);
        mRecyclerView.addItemDecoration(new ItemGridDividerItemDecoration());
        mRecyclerView.setAdapter(mItemAdapter);
        mItemAdapter.setSingleChoosed(p1);
        mItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mItemAdapter.setSingleChoosed(position);
                p1 = position;
            }
        });
//-----------------------------------------------------------------我是分割线-----------------------------------------------
        final List<String> mItems1 = new ArrayList<>();
        mItems1.add("1km");
        mItems1.add("2km");
        mItems1.add("3km");
        mItems1.add("5km");
        mItems1.add("10km");
        RecyclerView mRecyclerView1 = mView.findViewById(R.id.recyclerview_xiangmu);
        final TypeAdapter mItemAdapter1;
        mItemAdapter1 = new TypeAdapter(R.layout.item_xiangmu, mItems1);
        GridLayoutManager mFlexboxLayoutManager1 = new GridLayoutManager(mContext, 3);
        mRecyclerView1.setLayoutManager(mFlexboxLayoutManager1);
        mRecyclerView1.addItemDecoration(new ItemGridDividerItemDecoration());
        mRecyclerView1.setAdapter(mItemAdapter1);
        mItemAdapter1.setSingleChoosed(p2);
        mItemAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mItemAdapter1.setSingleChoosed(position);
                p2 = position;
            }
        });
//-----------------------------------------------------------------我是分割线-----------------------------------------------
        final List<String> mItems2 = new ArrayList<>();
        mItems2.add("PT1-PT3");
        mItems2.add("PT4-PT6");
        mItems2.add("PT7-PT10");
        RecyclerView mRecyclerView2 = mView.findViewById(R.id.recyclerview_dengji);
        final TypeAdapter mItemAdapter2;
        mItemAdapter2 = new TypeAdapter(R.layout.item_xiangmu, mItems2);
        GridLayoutManager mFlexboxLayoutManager2 = new GridLayoutManager(mContext, 3);
        mRecyclerView2.setLayoutManager(mFlexboxLayoutManager2);
        mRecyclerView2.addItemDecoration(new ItemGridDividerItemDecoration());
        mRecyclerView2.setAdapter(mItemAdapter2);
        mItemAdapter2.setSingleChoosed(p3);
        mItemAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mItemAdapter2.setSingleChoosed(position);
                p3 = position;
            }
        });
        //-----------------------------------------------------------------我是分割线-----------------------------------------------
        final List<String> mItems3 = new ArrayList<>();

        for (Skill mSkill : mSkills) {
            mItems3.add(mSkill.name);
        }

        RecyclerView mRecyclerView3 = mView.findViewById(R.id.recyclerview_shanchang);
        final DuoxuanTypeAdapter mItemAdapter3;
        mItemAdapter3 = new DuoxuanTypeAdapter(R.layout.item_xiangmu, mItems3);
        GridLayoutManager mFlexboxLayoutManager3 = new GridLayoutManager(mContext, 3);
        mRecyclerView3.setLayoutManager(mFlexboxLayoutManager3);
        mRecyclerView3.addItemDecoration(new ItemGridDividerItemDecoration());
        mRecyclerView3.setAdapter(mItemAdapter3);
        for (Integer mInteger : mIntegers) {
            mItemAdapter3.switchSelectedState(mInteger);

        }
        mItemAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mItemAdapter3.switchSelectedState(position);
                mIntegers.add(position);
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                //view重置
                mEditText.setText("");
                mEditText2.setText("");
                beginprice = "";
                endprice = "";
                p1 = -1;
                p2 = -1;
                p3 = -1;
                mIntegers.clear();

                mItemAdapter.setSingleChoosed(-1);
                mItemAdapter1.setSingleChoosed(-1);
                mItemAdapter2.setSingleChoosed(-1);
                mItemAdapter3.clearSelectedState();

                //参数重置
                distance = 0;
                degree = "";
                sex = "";
                skillids = "";
                orderby = "";
                pageNo = 1;
                mBottomSheetDialog.dismiss();
                refreshData(true);
            }
        });
        mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if (mItemAdapter.mSinglePosition == 0) {
                    sex = "1";
                } else {
                    sex = "2";
                }

                //------分割线-------

                if (mItemAdapter1.mSinglePosition == 0) {
                    distance = 1 * 1000;
                } else if (mItemAdapter1.mSinglePosition == 1) {
                    distance = 2 * 1000;
                } else if (mItemAdapter1.mSinglePosition == 2) {
                    distance = 3 * 1000;
                } else if (mItemAdapter1.mSinglePosition == 3) {
                    distance = 5 * 1000;
                } else if (mItemAdapter1.mSinglePosition == 4) {
                    distance = 10 * 1000;
                }

                //------分割线-------
                if (mItemAdapter2.mSinglePosition == 0) {
                    degree = "1";
                } else if (mItemAdapter2.mSinglePosition == 1) {
                    degree = "2";
                } else if (mItemAdapter2.mSinglePosition == 2){
                    degree = "3";
                }

                if (mItemAdapter3.getSelectedItemsKey() != null && mItemAdapter3.getSelectedItemsKey().size() != 0) {
                    StringBuilder mStringBuilder = new StringBuilder();
                    for (Integer m : mItemAdapter3.getSelectedItemsKey()) {
                        mStringBuilder.append(mSkills.get(m).id + ",");
                    }
                    skillids = mStringBuilder.toString().substring(0, mStringBuilder.toString().length() - 1);
                }

                mBottomSheetDialog.dismiss();
                refreshData(true);

            }
        });

    }

    @Deprecated
    private void showXiangmu() {

        View mView = getLayoutInflater().inflate(R.layout.view_trainer_xiangmu, null, false);
        BottomDialogUtils.showBottomDialog(mContext, mView);

    }

    private void showJuli() {

//        final List<String> mItems = new ArrayList<>();
//        mItems.add("不限");
//        mItems.add("1km");
//        mItems.add("2km");
//        mItems.add("3km");
//        mItems.add("4km");
//        mItems.add("5km");
//        mItems.add("6km");
//
//        BottomDialogUtils.showBottomDialog(mContext, mItems, new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//                if (position == 0) {
//                    distance = 0;
//                    mJuli.setText("距离");
//                } else {
//                    distance = position * 1000;
//                    mJuli.setText(mItems.get(position));
//
//                }
//
//                refreshData(true);
//            }
//        }).show();
        final List<String> mItems = new ArrayList<>();
        mItems.add("不限");
        mItems.add("距离升序");
        mItems.add("距离降序");
        BottomDialogUtils.showBottomDialog(mContext, mItems, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position == 0) {
                    orderby = "";
                    mJuli.setText("距离");
                } else if (position == 1) {
                    orderby = "a.distance";
                    mJuli.setText(mItems.get(position));

                } else if (position == 2) {
                    orderby = "a.distance desc";
                    mJuli.setText(mItems.get(position));
                }

                refreshData(true);
            }
        }).show();
    }

    private void showZonghe() {

        final List<String> mItems = new ArrayList<>();
        mItems.add("综合");
        mItems.add("价格升序");
        mItems.add("价格降序");
        mItems.add("好评升序");
        mItems.add("好评降序");
        mItems.add("等级升序");
        mItems.add("等级降序");


        BottomDialogUtils.showBottomDialog(mContext, mItems, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                mZonghe.setText(mItems.get(position));
                if (position == 0) {
                    //清空所有的条件
                    mJuli.setText("距离");
                    distance = 0;
                    skillids = "";
                    degree = "";
                    sex = "";
                    beginprice = "";
                    endprice = "";
                    orderby = "";

                } else if (position == 1) {
                    orderby = "a.courseprice";
                } else if (position == 2) {
                    orderby = "a.courseprice desc";
                } else if (position == 3) {
                    orderby = "a.score";
                } else if (position == 4) {
                    orderby = "a.score desc";
                } else if (position == 5) {
                    orderby = "a.degree";
                } else if (position == 6) {
                    orderby = "a.degree desc";
                }

                refreshData(true);
            }
        }).show();

    }

}
