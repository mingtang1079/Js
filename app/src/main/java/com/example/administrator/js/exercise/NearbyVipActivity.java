package com.example.administrator.js.exercise;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.example.administrator.js.exercise.adapter.NearbyVipAdapter;
import com.example.administrator.js.exercise.model.Skill;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/exercise/NearbyVipActivity")
public class NearbyVipActivity extends BaseRefreshActivity<User> {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_search)
    TextView mTextViewSearch;
    @BindView(R.id.zonghe)
    TextView mZonghe;
    @BindView(R.id.juli)
    TextView mJuli;
    @BindView(R.id.xingbie)
    TextView mXingbie;
    @BindView(R.id.type)
    TextView mType;

    int distance;
    String sex;
    String skillids;

    List<Skill> mSkills = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_nearby_vip;
    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("附近会员");
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
        toggleShowLoading(true, "加载中……");
        requestData();
    }

    @Override
    public void initAdapter() {
        mAdapter = new NearbyVipAdapter(R.layout.item_nearby_vip, mList);
        setLoadMoreListener();
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
        mStringStringMap.put("pageNo",pageNo);
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

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }


    @OnClick({R.id.zonghe, R.id.juli, R.id.xingbie, R.id.type, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zonghe:

                distance = 0;
                mJuli.setText("距离");
                sex = "";
                mXingbie.setText("性别");
                skillids = "";
                mType.setText("类型");
                refreshData(true);

                break;
            case R.id.juli:

                showJuli();

                break;
            case R.id.xingbie:

                showSex();
                break;
            case R.id.type:

                showType();

                break;

            case R.id.tv_search:
                start(SearchUserActivity.class);
                break;
        }
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

    private void showSex() {
        List<String> mItems = new ArrayList<>();
        mItems.add("不限");
        mItems.add("男");
        mItems.add("女");

        BottomDialogUtils.showBottomDialog(mContext, mItems, new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position == 0) {
                    sex = "";
                    mXingbie.setText("性别");
                } else if (position == 1) {
                    sex = "1";
                    mXingbie.setText("男");

                } else {
                    sex = "2";
                    mXingbie.setText("女");

                }
                refreshData(true);
            }
        }).show();

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
                    mType.setText("类型");
                } else {
                    skillids = ((Skill) mItems.get(position)).id;
                    mType.setText(mItems.get(position).getValue());

                }
                refreshData(true);
            }
        }).show();

    }


}
