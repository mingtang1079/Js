package com.example.administrator.js.vipandtrainer;

import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.exercise.member.NearbyTrainerAdapter;
import com.example.administrator.js.me.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by tangming on 2018/6/22.
 */
@Route(path = "/trainer/TrainerFragment")
public class TrainerFragment extends BaseRefreshFragment<User> {

    @Autowired
    String status;
    @BindView(R.id.et_search)
    EditText mEditTextSearch;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_trainer;
    }

    @Override
    protected void initView() {
        super.initView();
        toggleShowLoading(true, "加载中……");
        requestData();

    }
    @Override
    public void initAdapter() {
        mAdapter = new NearbyTrainerAdapter(R.layout.item_nearby_trainer, mList);
        setLoadMoreListener();

    }

    @Override
    public void requestData() {
        Map<String, String> mMap = new HashMap<>();
        mMap.put("id", UserManager.getInsatance().getUser().id);
        mMap.put("status", status);
        // TODO: 2018/6/22
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
}
