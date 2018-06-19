package com.example.administrator.js.exercise.member;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseRefreshActivity;
import com.appbaselib.utils.BottomDialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.R;
import com.example.administrator.js.me.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_nearby_trainer;
    }

    @Override
    protected void initView() {
        super.initView();
        requestData();
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public void initAdapter() {
        mAdapter = new NearbyTrainerAdapter(R.layout.item_nearby_trainer, mList);
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.zonghe, R.id.juli, R.id.xiangmu, R.id.jiage, R.id.shaixuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zonghe:

//                distance = 0;
//                mJuli.setText("距离");
//                sex = "";
//                mXingbie.setText("性别");
//                skillids = "";
//                mType.setText("类型");
//                refreshData(true);


                break;
            case R.id.juli:

                showJuli();

                break;
            case R.id.xiangmu:
                showXiangmu();
                break;
            case R.id.jiage:

                showJiage();

                break;
            case R.id.shaixuan:
                showXiangShaixuan();
                break;
        }
    }

    private void showXiangShaixuan() {
        View mView = getLayoutInflater().inflate(R.layout.view_trainer_shaixuan, null, false);
        BottomDialogUtils.showBottomDialog(mContext,mView);

    }


    private void showXiangmu() {

        View mView = getLayoutInflater().inflate(R.layout.view_trainer_xiangmu, null, false);
        BottomDialogUtils.showBottomDialog(mContext,mView);

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
//
//                if (position == 0) {
//                    distance = 0;
//                    mJuli.setText("距离");
//                } else {
//                    distance = position * 1000;
//                    mJuli.setText(mItems.get(position));
//
//                }

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
            }
        }).show();

    }
}
