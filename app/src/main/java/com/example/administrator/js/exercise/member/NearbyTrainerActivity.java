package com.example.administrator.js.exercise.member;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
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
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.SearchUserActivity;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.model.Skill;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.xiangmu)
    TextView mXiangmu;
    @BindView(R.id.jiage)
    TextView mJiage;
    @BindView(R.id.shaixuan)
    TextView mShaixuan;


    int distance;
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
        if (distance != 0) {
            mStringStringMap.put("distance", distance);
        }
        if (!TextUtils.isEmpty(sex)) {
            mStringStringMap.put("sex", sex);
        }
        if (!TextUtils.isEmpty(skillids)) {
            mStringStringMap.put("skillids", skillids);
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

    @OnClick({R.id.zonghe, R.id.juli, R.id.xiangmu, R.id.jiage, R.id.shaixuan, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zonghe:


                mJuli.setText("距离");
                mXiangmu.setText("项目");
                mJiage.setText("价格");

                skillids = "";
                distance = 0;
                sex = "";
                beginprice = "";
                endprice = "";

                refreshData(true);


                break;
            case R.id.juli:

                showJuli();

                break;
            case R.id.xiangmu:
                showType();

                //       showXiangmu();
                break;
            case R.id.jiage:

                showJiage();

                break;
            case R.id.shaixuan:
                showXiangShaixuan();
                break;

            case R.id.tv_search:
                start(SearchTrianerActivity.class);
                break;
        }
    }


    private void showType() {

        final List<ObjectAdapter.Item> mItems = new ArrayList<>();
        mItems.add(new Skill(null, "不限"));
        mItems.addAll(mSkills);

        BottomDialogUtils.showBottomDialog2(mContext, mItems, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position == 0) {
                    skillids = "";
                    mXiangmu.setText("项目");
                } else {
                    skillids = ((Skill) mItems.get(position)).id;
                    mXiangmu.setText(mItems.get(position).getValue());

                }
                refreshData(true);
            }
        }).show();

    }


    private void showXiangShaixuan() {
        View mView = getLayoutInflater().inflate(R.layout.view_trainer_shaixuan, null, false);
        BottomDialogUtils.showBottomDialog(mContext, mView);

    }

    @Deprecated
    private void showXiangmu() {

        View mView = getLayoutInflater().inflate(R.layout.view_trainer_xiangmu, null, false);
        BottomDialogUtils.showBottomDialog(mContext, mView);

    }

    private void showJuli() {

        final List<String> mItems = new ArrayList<>();
        mItems.add("不限");
        mItems.add("1km");
        mItems.add("2km");
        mItems.add("3km");
        mItems.add("4km");
        mItems.add("5km");
        mItems.add("6km");

        BottomDialogUtils.showBottomDialog(mContext, mItems, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position == 0) {
                    distance = 0;
                    mJuli.setText("距离");
                } else {
                    distance = position * 1000;
                    mJuli.setText(mItems.get(position));

                }

                refreshData(true);
            }
        }).show();

    }

    private void showJiage() {

        final List<String> mItems = new ArrayList<>();
        mItems.add("不限");
        mItems.add("价格升序");
        mItems.add("价格降序");

        BottomDialogUtils.showBottomDialog(mContext, mItems, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position == 0) {
                    orderby = "";
                    mJiage.setText("价格");
                } else if (position == 1) {
                    orderby = "a.courseprice";
                    mJiage.setText("价格升序");
                }
                else {

                    orderby = "a.a.courseprice desc";
                    mJiage.setText("价格降序");
                }

                refreshData(true);
            }
        }).show();

    }
}
