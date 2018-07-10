package com.example.administrator.js.course;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.vipandtrainer.adapter.CourseUserAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tangming on 2018/5/17.
 */
@Route(path = "/course/CourseTrainerFragment")
public class CourseTrainerFragment extends BaseRefreshFragment<CourseModel> {

    @Autowired
    String status;

    @BindView(R.id.et_search)
    TextView mTvSearch;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_user;
    }

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
                ARouter.getInstance().build("/course/CourDetailActivity")
                        .withString("id",mList.get(position).id)
                        .navigation(mContext);
            }
        });
    }

    @Override
    public void requestData() {

        Http.getDefault().getCourse(UserManager.getInsatance().getUser().id, status, pageNo, null)
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


    @OnClick(R.id.et_search)
    public void onViewClicked() {



    }
}