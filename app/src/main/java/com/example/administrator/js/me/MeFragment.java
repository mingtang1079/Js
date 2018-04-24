package com.example.administrator.js.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.common.ImageLoader;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class MeFragment extends BaseFragment {
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.iv_mes)
    ImageView mIvMes;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.iv_barcode)
    ImageView mIvBarcode;
    @BindView(R.id.iv_head)
    CircleImageView mImageViewHead;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {

        User mUser= UserManager.getInsatance().getUser();
        if (mUser!=null)
        {
            ImageLoader.load(mContext,mUser.img,mImageViewHead);
            mTvName.setText(mUser.nickname);
            mTvId.setText(mUser.no);

        }

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @OnClick({R.id.iv_add, R.id.iv_mes, R.id.iv_setting, R.id.tv_name, R.id.tv_id, R.id.iv_barcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add:


                break;
            case R.id.iv_mes:
                break;
            case R.id.iv_setting:
                break;
            case R.id.tv_name:

                ARouter.getInstance().build("/me/UserInfoActivity")
                        .navigation();
                break;
            case R.id.tv_id:
                ARouter.getInstance().build("/me/RealNameVerifyActivity")
                        .navigation();
                break;
            case R.id.iv_barcode:
                break;
        }
    }
}
