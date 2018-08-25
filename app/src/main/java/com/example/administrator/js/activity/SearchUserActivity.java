package com.example.administrator.js.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appbaselib.adapter.ObjectAdapter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseRefreshActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.BottomDialogUtils;
import com.appbaselib.utils.JsonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.LocationManager;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.NearbyVipAdapter;
import com.example.administrator.js.exercise.model.Skill;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchUserActivity extends BaseRefreshActivity<User> {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search_user;
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
        mAdapter = new NearbyVipAdapter(R.layout.item_nearby_vip, mList);
        setLoadMoreListener();
    }

    @Override
    public void requestData() {

        Map<String, Object> mStringStringMap = new HashMap<>();
        mStringStringMap.put("id", UserManager.getInsatance().getUser().id);
        mStringStringMap.put("no", mTextViewSearch.getText().toString());
        mStringStringMap.put("pageNo",pageNo);
        if (!TextUtils.isEmpty(LocationManager.getInsatance().longitude)) {
            mStringStringMap.put("longitude", LocationManager.getInsatance().longitude);
        }
        if (!TextUtils.isEmpty(LocationManager.getInsatance().latitude)) {
            mStringStringMap.put("latitude", LocationManager.getInsatance().latitude);
        }
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
