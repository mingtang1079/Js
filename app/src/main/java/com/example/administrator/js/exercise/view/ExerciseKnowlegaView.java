package com.example.administrator.js.exercise.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseModel;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.LogUtils;
import com.appbaselib.utils.ToastUtils;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.adapter.BaseLifeCycleView;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.KnowledgeAdapter;
import com.example.administrator.js.exercise.model.Main;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by tangming on 2018/5/3.
 */

public class ExerciseKnowlegaView extends BaseLifeCycleView {

    public Observable<BaseModel<WrapperModel<Main>>> mBaseModelObservable;


    @BindView(R.id.tv_knowledge)
    TextView mTvKnowledge;
    @BindView(R.id.recyclerview_knowledge)
    RecyclerView mRecyclerviewKnowledge;

    KnowledgeAdapter mKnowledgeAdapter;
    List<Main> mMains = new ArrayList<>();

    public ExerciseKnowlegaView(Context context) {
        super(context);
    }

    public ExerciseKnowlegaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExerciseKnowlegaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context mContext) {
        super.init(mContext);
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.view_exercise_knowledge, this, false);
        ButterKnife.bind(this, mView);
        addView(mView);

        LinearLayoutManager mLinearLayoutManage = new LinearLayoutManager(getContext());
        mLinearLayoutManage.setOrientation(LinearLayoutManager.VERTICAL);
        mKnowledgeAdapter = new KnowledgeAdapter(R.layout.item_knowledge, mMains);
        mRecyclerviewKnowledge.setLayoutManager(mLinearLayoutManage);
        mRecyclerviewKnowledge.addItemDecoration(new KnowledgeAdapter.KnowledgeDividerItemDecoration());
        mRecyclerviewKnowledge.setAdapter(mKnowledgeAdapter);
        mRecyclerviewKnowledge.setNestedScrollingEnabled(false);
        requestData();
    }

    public void requestData() {

        mBaseModelObservable=       Http.getDefault().getMain(UserManager.getInsatance().getUser().id,2, 1, 3);

        mBaseModelObservable        .as(RxHelper.<WrapperModel<Main>>handleResult(getContext()))
                .subscribe(new ResponceSubscriber<WrapperModel<Main>>() {
                    @Override
                    protected void onSucess(WrapperModel<Main> mMainWrapperModel) {

                        if (mMainWrapperModel != null && mMainWrapperModel.list != null) {
                            mKnowledgeAdapter.setNewData(mMainWrapperModel.list);
                        }
                    }

                    @Override
                    protected void onFail(String message) {
                        LogUtils.d("健身知识---》"+message);
                    }
                });

    }


    @OnClick(R.id.tv_knowledge)
    public void onViewClicked() {

       // ToastUtils.showShort(getContext(), "健身知识");
        ARouter.getInstance().build("/exercise/KnowledgeActivity")
                .navigation();

    }
}
