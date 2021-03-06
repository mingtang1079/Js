package com.example.administrator.js.activity.fragment;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.utils.PreferenceUtils;
import com.example.administrator.js.R;
import com.example.administrator.js.constant.Constans;

import butterknife.BindView;
import butterknife.OnClick;


public class PayFailFragment extends BaseFragment {

    String orderId;

    @BindView(R.id.tv_back_home)
    TextView mTvBackHome;
    @BindView(R.id.tv_back_top)
    TextView mTvBackTop;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_pay_fail;
    }

    @Override
    protected void initView() {
        orderId = PreferenceUtils.getPrefString(mContext, Constans.ORDERID, "");
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @OnClick({R.id.tv_back_home, R.id.tv_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_home:
                getActivity().finish();
                break;
            case R.id.tv_back_top:

                ARouter.getInstance().build("/me/member/OrderDetailActivity")
                        .withString("id", orderId)
                        .navigation(mContext);
                getActivity().finish();
                break;
        }
    }


}
