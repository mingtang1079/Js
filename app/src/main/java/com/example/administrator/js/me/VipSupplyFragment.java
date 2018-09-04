package com.example.administrator.js.me;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.me.adapter.VipSupplyAdapter;
import com.example.administrator.js.me.model.VipSupply;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.HashMap;
import java.util.Map;

@Route(path = "/me/VipSupplyFragment")
public class VipSupplyFragment extends BaseRefreshFragment<VipSupply> {

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
        mAdapter = new VipSupplyAdapter(R.layout.item_vip_supply, mList);
        setLoadMoreListener();
    }

    @Override
    public void requestData() {


        Map<String, Object> mMap = new HashMap<>();
        mMap.put("tid", UserManager.getInsatance().getUser().id);
        if ("1".equals(status)) {
            mMap.put("status", "3");
            mMap.put("tryflag", "1");
        } else if ("2".equals(status)) {
            mMap.put("status", "3");
            mMap.put("tryflag", "0");
        } else if ("3".equals(status)) {
            mMap.put("status", "b55");
            mMap.put("tryflag", "0");
        } else {
            mMap.put("status", "b");
        }
        mMap.put("pageNo", pageNo);

        Http.getDefault().vipSupply(mMap)
                .as(RxHelper.<WrapperModel<VipSupply>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<VipSupply>>() {
                    @Override
                    protected void onSucess(WrapperModel<VipSupply> mVipSupplyWrapperModel) {

                        if (mVipSupplyWrapperModel.list != null)
                            loadComplete(mVipSupplyWrapperModel.list);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError(message);
                    }
                });

    }

}
