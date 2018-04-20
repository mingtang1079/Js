package com.example.mine.me;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.example.mine.R;
import com.example.mine.R2;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/me/MeFragment")
public class MeFragment extends BaseFragment {
    @BindView(R2.id.iv_add)
    ImageView mIvAdd;
    @BindView(R2.id.iv_mes)
    ImageView mIvMes;
    @BindView(R2.id.iv_setting)
    ImageView mIvSetting;
    @BindView(R2.id.tv_name)
    TextView mTvName;
    @BindView(R2.id.tv_id)
    TextView mTvId;
    @BindView(R2.id.iv_barcode)
    ImageView mIvBarcode;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @OnClick({R2.id.iv_head,R2.id.iv_add, R2.id.iv_mes, R2.id.iv_setting, R2.id.tv_name, R2.id.tv_id, R2.id.iv_barcode})
    public void onViewClicked(View view) {
        if (R.id.iv_mes == view.getId()) {

            ARouter.getInstance().build("/me/RealNameVerifyActivity")
                    .navigation();
        } else if (R.id.iv_head==view.getId())
        {
            ARouter.getInstance().build("/me/UserInfoActivity")
                    .navigation();
        }



    }
}
