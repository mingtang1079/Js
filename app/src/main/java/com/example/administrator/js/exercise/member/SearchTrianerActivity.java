package com.example.administrator.js.exercise.member;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appbaselib.base.BaseRefreshActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SearchTrianerActivity  extends BaseRefreshActivity<User> {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search_trianer;
    }


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_search)
    EditText mTextViewSearch;
    @BindView(R.id.btn_sure)
    Button mButton;

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    protected void initView() {
        super.initView();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if (!TextUtils.isEmpty(mTextViewSearch.getText().toString())) {
                    refreshData(true);
                }
            }
        });
    }

    @Override
    public void initAdapter() {
        mAdapter = new NearbyTrainerAdapter(R.layout.item_nearby_trainer, mList);
        setLoadMoreListener();
    }

    @Override
    public void requestData() {

        // TODO: 2018/6/17  
        Map<String, Object> mStringStringMap = new HashMap<>();
        mStringStringMap.put("id", UserManager.getInsatance().getUser().id);
        mStringStringMap.put("no", mTextViewSearch.getText().toString());
        mStringStringMap.put("pageNo",pageNo);

        Http.getDefault().seacrchUser(mStringStringMap)
                .as(RxHelper.<WrapperModel<VipUser>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<VipUser>>() {
                    @Override
                    protected void onSucess(WrapperModel<VipUser> mNearbyVipWrapperModel) {

                        loadComplete(mNearbyVipWrapperModel.list);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });

    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

}
