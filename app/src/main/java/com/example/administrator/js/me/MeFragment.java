package com.example.administrator.js.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.common.ImageLoader;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.MessageActivity;
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
    @BindView(R.id.ll_zizhi)
    LinearLayout mLinearLayoutZizhi;
    @BindView(R.id.tv_verify)
    TextView mTextViewVerify;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {

        User mUser = UserManager.getInsatance().getUser();
        if (mUser != null) {
            ImageLoader.load(mContext, mUser.img, mImageViewHead);
            mTvName.setText(mUser.nickname);
            mTvId.setText("ID：" + mUser.no);

        }

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @OnClick({R.id.ll_barcode, R.id.iv_add, R.id.iv_mes, R.id.iv_setting, R.id.tv_name, R.id.tv_id, R.id.iv_barcode, R.id.ll_zizhi, R.id.ll_share, R.id.ll_my_collection,
            R.id.ll_shenqing, R.id.ll_richeng, R.id.ll_tongji, R.id.ll_yajing, R.id.ll_bidu, R.id.ll_about, R.id.ll_fankui})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_barcode:
                start(BarcodeActivity.class);

                break;

            case R.id.iv_add:


                break;
            case R.id.iv_mes:

                start(MessageActivity.class);

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

            case R.id.ll_zizhi:

                start(ZizhiActivity.class);
                break;

            case R.id.ll_shenqing:

                break;
            case R.id.ll_richeng:

                break;
            case R.id.ll_tongji:

                break;
            case R.id.ll_yajing:

                break;
            case R.id.ll_share:

                break;
            case R.id.ll_about:
                start(AboutUsActivity.class);
                break;
            case R.id.ll_fankui:

                start(FankuiActivity.class);
                break;
            case R.id.ll_bidu:

                start(TeacherBiDuActivity.class);
                break;
            case R.id.ll_my_collection:

                //    start(FankuiActivity.class);
                break;
        }
    }

}
