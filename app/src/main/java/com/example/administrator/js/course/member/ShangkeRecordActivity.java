package com.example.administrator.js.course.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.MyBaseRefreshActivity;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.course.CourseModel;
import com.example.administrator.js.course.member.adapter.ShangkeRecordAdapter;

public class ShangkeRecordActivity extends MyBaseRefreshActivity<CourseModel> {


    @Override
    public void initAdapter() {
        mAdapter = new ShangkeRecordAdapter(R.layout.item_shangke_record, mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build("/course/MemberCourDetailActivity")
                        .withObject("mCourseModel", mList.get(position))
                        .navigation(mContext);
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("上课记录");
        toggleShowLoading(true);
        requestData();
    }

    @Override
    public void requestData() {
        Http.getDefault().getCourse(UserManager.getInsatance().getUser().id, "2", pageNo, null)
                .as(RxHelper.<WrapperModel<CourseModel>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<CourseModel>>() {
                    @Override
                    protected void onSucess(WrapperModel<CourseModel> mVipUserBaseModelWrapper) {
                        if (mVipUserBaseModelWrapper != null) {
                            loadComplete(mVipUserBaseModelWrapper.list);
                        }

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }
}
