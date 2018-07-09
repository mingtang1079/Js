package com.example.administrator.js.course.member;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.course.CourseModel;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.vipandtrainer.adapter.CourseUserAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by tangming on 2018/6/23.
 */
@Route(path = "/member/CourseMemberFragment")
public class CourseMemberFragment extends BaseRefreshFragment<CourseModel> {


    @Autowired
    String status;

    @Override
    protected void initView() {
        super.initView();
        toggleShowLoading(true, "加载中……");
        requestData();

    }

    @Override
    public void initAdapter() {
        mAdapter = new CourseUserAdapter(R.layout.item_course_user, mList);
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
    public void requestData() {

        Http.getDefault().getCourse2(UserManager.getInsatance().getUser().id, status, pageNo, null)
                .as(RxHelper.<WrapperModel<CourseModel>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<CourseModel>>() {
                    @Override
                    protected void onSucess(WrapperModel<CourseModel> mVipUserBaseModelWrapper) {
                        if (mVipUserBaseModelWrapper != null)
                            loadComplete(mVipUserBaseModelWrapper.list);

                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });


    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatusChange(EventMessage.CourseListStatusChange mListStatusChange) {
        refreshData(false);
    }

}
