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
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.adapter.BaseLifeCycleView;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.ExerciseSkillAdapter;
import com.example.administrator.js.exercise.adapter.KnowledgeAdapter;
import com.example.administrator.js.exercise.adapter.VipResultAdapter;
import com.example.administrator.js.exercise.model.Main;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SkillView extends BaseLifeCycleView {
    public SkillView(Context context) {
        super(context);
    }

    public SkillView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SkillView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @BindView(R.id.iv_skill)
    TextView mTvVipResult;
    @BindView(R.id.recyclerview_vip)
    RecyclerView mRecyclerView;

    ExerciseSkillAdapter mExerciseSkillAdapter;
    List<Main> mMains = new ArrayList<>();


    @Override
    public void init(Context mContext) {
        super.init(mContext);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.view_exercise_skill, this, false);
        ButterKnife.bind(this, mView);
        addView(mView);

        LinearLayoutManager mLinearLayoutManage = new LinearLayoutManager(getContext());
        mLinearLayoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        mExerciseSkillAdapter = new ExerciseSkillAdapter(R.layout.item_exercise_skill, mMains);
        mRecyclerView.setLayoutManager(mLinearLayoutManage);
        mRecyclerView.setAdapter(mExerciseSkillAdapter);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.addItemDecoration(new KnowledgeAdapter.KnowledgeDividerItemDecoration());
        mRecyclerView.setNestedScrollingEnabled(false);
        requestData();

    }

    private void requestData() {

        Http.getDefault().getMain(UserManager.getInsatance().getUser().id,4, 1, 3)
                .as(RxHelper.<WrapperModel<Main>>handleResult(getContext()))
                .subscribe(new ResponceSubscriber<WrapperModel<Main>>() {
                    @Override
                    protected void onSucess(WrapperModel<Main> mMainWrapperModel) {

                        if (mMainWrapperModel != null && mMainWrapperModel.list != null) {
                            mExerciseSkillAdapter.addData(mMainWrapperModel.list);
                        }
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });

    }


    @OnClick(R.id.iv_skill)
    public void onViewClicked() {

        ARouter.getInstance().build("/exercise/ExerciseSkillActivity")
                .navigation();

    }
}
