package com.example.administrator.js;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.NearbyVipAdapter;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by tangming on 2018/5/22.
 */

@Route(path = "/commen/NearByVipFragment")
public class NearByVipFragment extends BaseRefreshFragment<User> {
    @BindView(R.id.et_search)
    EditText mEditTextSearch;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_vip_user;
    }

    @Override
    protected void initView() {
        super.initView();
        RxTextView.textChanges(mEditTextSearch).skip(1)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence mCharSequence) throws Exception {
                        refreshData(true);
                    }
                });
        toggleShowLoading(true, "加载中……");
        requestData();

    }
    @Override
    public void initAdapter() {
        mAdapter = new NearbyVipAdapter(R.layout.item_nearby_vip, mList);
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ARouter.getInstance().build("/vip/VipUserDetailActivity")
//                        .withObject("mUser", mList.get(position))
//                        .navigation(mContext);
//            }
//        });
        setLoadMoreListener();

    }

    @Override
    public void requestData() {
        Map<String, Object> mStringStringMap = new HashMap<>();
        mStringStringMap.put("id", UserManager.getInsatance().getUser().id);
        if (!TextUtils.isEmpty(LocationManager.getInsatance().longitude)) {
            mStringStringMap.put("longitude", LocationManager.getInsatance().longitude);
        }
        if (!TextUtils.isEmpty(LocationManager.getInsatance().latitude)) {
            mStringStringMap.put("latitude", LocationManager.getInsatance().latitude);
        }
        if (!TextUtils.isEmpty(mEditTextSearch.getText().toString())) {
            mStringStringMap.put("no", mEditTextSearch.getText().toString());
        }
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
}
