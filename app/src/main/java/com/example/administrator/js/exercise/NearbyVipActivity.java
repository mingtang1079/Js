package com.example.administrator.js.exercise;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.appbaselib.base.BaseRefreshActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.NearbyVipAdapter;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NearbyVipActivity extends BaseRefreshActivity<User> {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.zonghe)
    RadioButton mZonghe;
    @BindView(R.id.juli)
    RadioButton mJuli;
    @BindView(R.id.shaixuan)
    RadioButton mShaixuan;
    @BindView(R.id.group)
    RadioGroup mGroup;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_nearby_vip;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        mToolbar.setTitle("附近会员");

    }

    @Override
    public void initAdapter() {
        mAdapter = new NearbyVipAdapter(R.layout.item_nearby_vip, mList);
    }

    @Override
    public void requestData() {

        Map<String, String> mStringStringMap = new HashMap<>();
        mStringStringMap.put("id", UserManager.getInsatance().getUser().id);
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


    @OnClick({R.id.zonghe, R.id.juli, R.id.shaixuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zonghe:
                break;
            case R.id.juli:
                break;
            case R.id.shaixuan:
                break;
        }
    }
}
