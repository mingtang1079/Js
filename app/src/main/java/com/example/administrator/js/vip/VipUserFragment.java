package com.example.administrator.js.vip;

import android.text.TextUtils;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.exercise.adapter.NearbyVipAdapter;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

@Route(path = "/vip/VipUserFragment")
public class VipUserFragment extends BaseRefreshFragment<User> {

    @Autowired
    String status;

    @BindView(R.id.et_search)
    EditText mEditTextSearch;

    @Override
    protected void initView() {
        super.initView();
        RxTextView.textChangeEvents(mEditTextSearch).skip(1)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<TextViewTextChangeEvent>() {
                    @Override
                    public void accept(TextViewTextChangeEvent mTextViewTextChangeEvent) throws Exception {

                        requestData();
                    }
                });
    }

    @Override
    public void initAdapter() {
        mAdapter = new NearbyVipAdapter(R.layout.item_nearby_vip, mList);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_vip_user;
    }

    @Override
    public void requestData() {
        Map<String, String> mMap = new HashMap<>();
        mMap.put("id", UserManager.getInsatance().getUser().id);
        mMap.put("status", status);
        if (!TextUtils.isEmpty(mEditTextSearch.getText().toString())) {
            mMap.put("condition", mEditTextSearch.getText().toString());
        }

        Http.getDefault().getUser(mMap)
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
