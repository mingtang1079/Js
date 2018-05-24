package com.example.administrator.js.exercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.MyBaseRefreshActivity;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.ExerciseSkillAdapter;
import com.example.administrator.js.exercise.model.Main;

@Route(path = "/exercise/ExerciseSkillActivity")

public class ExerciseSkillActivity extends MyBaseRefreshActivity<Main> {

    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("训练技巧");
        setLoadMoreListener();
    }

    @Override
    public void initAdapter() {
        mAdapter = new ExerciseSkillAdapter(R.layout.item_exercise_skill, mList);
    }

    @Override
    public void requestData() {
        Http.getDefault().getMain(UserManager.getInsatance().getUser().id,4, pageNo, pageSize)
                .as(RxHelper.<WrapperModel<Main>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<Main>>() {
                    @Override
                    protected void onSucess(WrapperModel<Main> mMainWrapperModel) {

                        if (mMainWrapperModel != null && mMainWrapperModel.list != null) {
                            loadComplete(mMainWrapperModel.list);
                        }
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });
    }
}
