package com.example.administrator.js.exercise;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appbaselib.base.BaseFragment;
import com.appbaselib.base.BaseModel;
import com.example.administrator.js.R;
import com.example.administrator.js.activity.MessageActivity;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.model.Main;
import com.example.administrator.js.exercise.view.ExerciseKnowlegaView;
import com.example.administrator.js.exercise.view.MainHeaderView;
import com.example.administrator.js.exercise.view.SkillView;
import com.example.administrator.js.exercise.view.VipResultView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DefaultObserver;

/**
 * Created by tangming on 2018/5/3.
 */

public class ExerciseFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.hearview)
    MainHeaderView mHearview;
    @BindView(R.id.knowview)
    ExerciseKnowlegaView mKnowview;
    @BindView(R.id.resultview)
    VipResultView mResultview;
    @BindView(R.id.skillview)
    SkillView mSkillview;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_exercise;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("健身");
        //  mToolbar.setTitleTextColor(ContextCompat.getColor(R.color.));
        mToolbar.inflateMenu(R.menu.main_exercise);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                start(MessageActivity.class);

                return false;
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refresh();

                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });
    }

    private void refresh() {

        mHearview.requestData();
        mKnowview.requestData();
        mResultview.requestData();
        mSkillview.requestData();

//        Observable.zip(mHearview.mBaseModelObservable, mKnowview.mBaseModelObservable, new BiFunction<BaseModel<WrapperModel<Main>>, BaseModel<WrapperModel<Main>>, Boolean>() {
//            @Override
//            public Boolean apply(BaseModel<WrapperModel<Main>> mWrapperModelBaseModel, BaseModel<WrapperModel<Main>> mWrapperModelBaseModel2) throws Exception {
//                return true;
//            }
//        }).subscribe(new DefaultObserver<Boolean>() {
//            @Override
//            public void onNext(Boolean mBoolean) {
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mSwipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onComplete() {
//                mSwipeRefreshLayout.setRefreshing(false);
//
//            }
//        });

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}
