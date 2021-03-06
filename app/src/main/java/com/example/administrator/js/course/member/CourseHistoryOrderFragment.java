package com.example.administrator.js.course.member;

import android.view.View;

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
import com.example.administrator.js.constant.EventMessage;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by tangming on 2018/6/25.
 */
@Route(path = "/member/CourseHistoryOrderFragment")
public class CourseHistoryOrderFragment extends BaseRefreshFragment<HistoryOrder> {


    @Override
    protected void initView() {
        super.initView();
        toggleShowLoading(true, "加载中……");
        requestData();
        setLoadMoreListener();
    }

    @Override
    public void initAdapter() {
        mAdapter = new CourseHistoryOrderAdapter(R.layout.item_course_history_order, mList);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_yuyue) {

                    ARouter.getInstance().build("/member/YuyueCourseActivity")
                            .withString("id", mList.get(position).id)
                            .navigation(mContext);

                } else if (view.getId() == R.id.tv_xuke) {

                    ARouter.getInstance().build("/vipandtrainer/BuySiJiaoKeActivity")
                            .withString("id", mList.get(position).tid)
                            .withString("cardid", mList.get(position).id)
                            .navigation(mContext);

                }
            }
        });

    }

    @Override
    public void requestData() {

        Http.getDefault().cardlist(UserManager.getInsatance().getUser().id, String.valueOf(pageNo))
                .as(RxHelper.<WrapperModel<HistoryOrder>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<HistoryOrder>>() {
                    @Override
                    protected void onSucess(WrapperModel<HistoryOrder> mHistoryOrderWrapperModel) {
                        if (mHistoryOrderWrapperModel != null) {
                            loadComplete(mHistoryOrderWrapperModel.list);
                        }
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });


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
