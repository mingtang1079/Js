package com.example.administrator.js.me.member;

import android.content.DialogInterface;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseRefreshFragment;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.DialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.constant.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangming on 2018/6/26.
 */
@Route(path = "/me/member/OrderFragment")
public class OrderFragment extends BaseRefreshFragment<MyOrder> {


    @Autowired
    String status;//状态a1提交b2已通过未付款b3完成付款,b4不通过,b55退款中,b56退款成功

    @Override
    protected void initView() {
        super.initView();
        toggleShowLoading(true);
        requestData();
        setLoadMoreListener();
    }

    @Override
    public void initAdapter() {
        mAdapter = new MyOrderAdapter(R.layout.item_my_order, mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                ARouter.getInstance().build("/me/member/OrderDetailActivity")
                        .withString("id", mList.get(position).id)
                        .navigation(mContext);

            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view.getId() == R.id.tv_cancel) {
                    DialogUtils.getDefaultDialog(mContext, "提示", "确定取消该订单吗？", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface mDialogInterface, int mI) {

                            cancel(position, "c70");


                        }
                    }).show();
                } else if (view.getId() == R.id.tv_quxiao_tuikuan) {
                    DialogUtils.getDefaultDialog(mContext, "提示", "确定取消退款吗？", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface mDialogInterface, int mI) {

                            cancel(position, "b3");
                        }
                    }).show();


                } else if (view.getId() == R.id.tv_pay) {

                    ARouter.getInstance().build("/activity/PayActivity")
                            .withString("orderId", mList.get(position).id)
                            .withString("orderType", "0")
                            .navigation(mContext);


                } else if (view.getId() == R.id.tv_tuikuan) {
                    ARouter.getInstance().build("/member/TuikeActivity")
                            .withObject("mMyOrder", mList.get(position))
                            .navigation(mContext);

                }
            }
        });
    }

    private void cancel(final int mPosition, String mStatus) {

        Map<String, Object> mMap = new HashMap<>();
        mMap.put("uid", UserManager.getInsatance().getUser().id);
        mMap.put("id", mList.get(mPosition).id);
        mMap.put("status", mStatus);

        Http.getDefault().applyYuyueke(mMap)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {
                        mAdapter.remove(mPosition);
                        EventBus.getDefault().post(new EventMessage.ListStatusChange());
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

    @Override
    public void requestData() {

        Http.getDefault().getOrderlist(UserManager.getInsatance().getUser().id, status, String.valueOf(pageNo))
                .as(RxHelper.<WrapperModel<MyOrder>>handleResult(mContext))
                .subscribe(new ResponceSubscriber<WrapperModel<MyOrder>>() {
                    @Override
                    protected void onSucess(WrapperModel<MyOrder> mMyOrders) {
                        loadComplete(mMyOrders.list);
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
    public void onStatusChange(EventMessage.ListStatusChange mListStatusChange) {
        refreshData(false);
    }
}
