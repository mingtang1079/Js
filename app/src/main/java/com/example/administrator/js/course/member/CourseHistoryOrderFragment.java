package com.example.administrator.js.course.member;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;

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

    }

    @Override
    public void initAdapter() {
        mAdapter = new CourseHistoryOrderAdapter(R.layout.item_course_history_order, mList);
    }

    @Override
    public void requestData() {

        Http.getDefault().cardlist(UserManager.getInsatance().getUser().id)
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
}
