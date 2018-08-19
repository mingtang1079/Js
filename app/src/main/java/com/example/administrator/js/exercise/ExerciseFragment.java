package com.example.administrator.js.exercise;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appbaselib.base.BaseFragment;
import com.appbaselib.base.BaseModel;
import com.example.administrator.js.R;
import com.example.administrator.js.activity.MessageActivity;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.exercise.model.Main;
import com.example.administrator.js.exercise.view.ExerciseKnowlegaView;
import com.example.administrator.js.exercise.view.MainHeaderView;
import com.example.administrator.js.exercise.view.SkillView;
import com.example.administrator.js.exercise.view.VipResultView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.iv_message)
    ImageView mImageViewMes;
    @BindView(R.id.view_tag)
    View mViewTag;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_exercise;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("首页");
        //  mToolbar.setTitleTextColor(ContextCompat.getColor(R.color.));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refresh();

                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //
                        if (mSwipeRefreshLayout != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 2000);

            }
        });
        mImageViewMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                EventBus.getDefault().post(new EventMessage.NewMessageReceived(1));
                start(MessageActivity.class);
                mViewTag.setVisibility(View.GONE);
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

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMessage(EventMessage.NewMessageReceived mm) {

        if (mViewTag != null) {
            if (mm.message == 0) {
                mViewTag.setVisibility(View.VISIBLE);
            } else if (mm.message == 1) {
                mViewTag.setVisibility(View.GONE);

            }
        }

    }
}
