package com.example.administrator.js.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.base.BaseModelWrapper;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.vip.adapter.CourseUserAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by tangming on 2018/5/17.
 */
@Route(path = "/course/CourseUserFragment")
public class CourseUserFragment extends BaseRefreshFragment<User> {

    @Autowired
    String status;

    @BindView(R.id.et_search)
    TextView mTvSearch;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_user;
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
                .as(RxHelper.<WrapperModel<User>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<User>>() {
                    @Override
                    protected void onSucess(WrapperModel<User> mVipUserBaseModelWrapper) {
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
