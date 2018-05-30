package com.example.administrator.js.exercise.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.LogUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.adapter.BaseLifeCycleView;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.VipResultAdapter;
import com.example.administrator.js.exercise.model.Main;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangming on 2018/5/3.
 */

public class VipResultView extends BaseLifeCycleView {

    @BindView(R.id.iv_vip_result)
    TextView mTvVipResult;
    @BindView(R.id.recyclerview_vip)
    RecyclerView mRecyclerView;

    VipResultAdapter mVipResultAdapter;
    List<Main> mMains = new ArrayList<>();

    public VipResultView(Context context) {
        super(context);
    }

    public VipResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VipResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context mContext) {
        super.init(mContext);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.view_vip_result, this, false);
        ButterKnife.bind(this, mView);
        addView(mView);

        LinearLayoutManager mLinearLayoutManage = new LinearLayoutManager(getContext());
        mLinearLayoutManage.setOrientation(LinearLayoutManager.HORIZONTAL);
        mVipResultAdapter = new VipResultAdapter(R.layout.item_main_vip_result, mMains);
        mRecyclerView.setLayoutManager(mLinearLayoutManage);
        mRecyclerView.setAdapter(mVipResultAdapter);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.addItemDecoration(new VipResultAdapter.VipgeDividerItemDecoration());
        mRecyclerView.setNestedScrollingEnabled(false);
        requestData();

    }

    public void requestData() {

        Http.getDefault().getMain(UserManager.getInsatance().getUser().id,3, 1, 3)
                .as(RxHelper.<WrapperModel<Main>>handleResult(getContext()))
                .subscribe(new ResponceSubscriber<WrapperModel<Main>>() {
                    @Override
                    protected void onSucess(WrapperModel<Main> mMainWrapperModel) {

                        if (mMainWrapperModel != null && mMainWrapperModel.list != null) {
                            mVipResultAdapter.setNewData(mMainWrapperModel.list);
                        }
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });

    }
    @OnClick(R.id.iv_vip_result)
    public void onViewClicked() {

        ARouter.getInstance().build("/exercise/VipResultActivity")
                .navigation();

    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        LogUtils.d("visi--"+visibility);
    }
}
