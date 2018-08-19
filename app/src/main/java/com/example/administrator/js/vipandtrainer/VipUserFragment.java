package com.example.administrator.js.vipandtrainer;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.SearchUserActivity;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.exercise.adapter.NearbyVipAdapter;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.model.User;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

@Route(path = "/vip/VipUserFragment")
public class VipUserFragment extends BaseRefreshFragment<User> {

    @Autowired
    String status;

    @BindView(R.id.et_search)
    TextView mEditTextSearch;

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
        mEditTextSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                start(SearchUserActivity.class);

            }
        });


        toggleShowLoading(true, "加载中……");
        requestData();
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
                .as(RxHelper.<List<VipUser>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<List<VipUser>>() {
                    @Override
                    protected void onSucess(List<VipUser> list) {

                        loadComplete(list);
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
    public void onStatusChange(EventMessage.RelationStatusChangge mZizhiStatus) {
        refreshData(false);
    }
}
