package com.example.administrator.js.me.member;

import android.graphics.drawable.Drawable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.utils.BottomDialogUtils;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.MessageActivity;
import com.example.administrator.js.activity.NewNeedActivity;
import com.example.administrator.js.activity.ShareActivity;
import com.example.administrator.js.me.AboutUsActivity;
import com.example.administrator.js.me.BarcodeActivity;
import com.example.administrator.js.me.CollectionActivity;
import com.example.administrator.js.me.FankuiActivity;
import com.example.administrator.js.me.SettingActivity;
import com.example.administrator.js.me.TongjiActivity;
import com.example.administrator.js.me.model.RealUserInfo;
import com.example.administrator.js.me.model.User;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tangming on 2018/6/25.
 */

public class MeMemberFragment extends BaseFragment {

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
    @BindView(R.id.tv_age)
    TextView mTextViewAge;


    User mUser;
    RealUserInfo mRealUserInfo;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me_member;
    }

    @Override
    protected void initView() {
        mUser = UserManager.getInsatance().getUser();
        requestData();

    }

    @Override
    public void onResume() {
        super.onResume();
        setUser();
    }

    private void setUser() {
        mUser = UserManager.getInsatance().getUser();
        if (mUser != null) {
            ImageLoader.load(mContext, mUser.img, mImageViewHead);
            mTvName.setText(mUser.nickname);
            mTvId.setText("ID：" + mUser.no);

            //年龄
            //年龄
            if (mUser.sex != null) {
                mTextViewAge.setVisibility(View.VISIBLE);

                if (mUser.age != null ) {
                    mTextViewAge.setText(" "+mUser.age);
                }
                if (mUser.sex.equals("1")) {
                    //男性
                    mTextViewAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));

                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    mTextViewAge.setCompoundDrawables(drawable, null, null, null);
                } else {
                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    mTextViewAge.setCompoundDrawables(drawable, null, null, null);
                    mTextViewAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));
                }
            } else {
                mTextViewAge.setVisibility(View.GONE);

            }
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    BottomSheetDialog mBottomSheetDialog;

    @OnClick({R.id.ll_barcode, R.id.iv_add, R.id.iv_mes, R.id.iv_setting, R.id.iv_head, R.id.tv_id,
            R.id.my_order, R.id.ll_share, R.id.ll_my_collection,
            R.id.shenti_shuju, R.id.ll_xuqiu, R.id.ll_about, R.id.ll_fankui ,R.id.ll_personal,})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_personal:
                ARouter.getInstance().build("/me/UserInfoActivity")
                        .navigation();
                break;

            case R.id.ll_barcode:
                start(BarcodeActivity.class);

                break;

            case R.id.iv_add:


                break;
            case R.id.iv_mes:

                start(MessageActivity.class);

                break;
            case R.id.iv_setting:


                start(SettingActivity.class);
                break;
            case R.id.iv_head:

                ARouter.getInstance().build("/activity/LookBigImageActivity")
                        .withString("url",mUser.img)
                        .navigation();

                break;
            case R.id.tv_id:

                break;

            case R.id.my_order:

                start(MyOrderActivity.class);
                break;

            case R.id.shenti_shuju:

                start(BodyDataActivity.class);
                break;
            case R.id.ll_xuqiu:

                // TODO: 2018/6/26 需求
                start(NewNeedActivity.class);
                break;
            case R.id.ll_tongji:

                start(TongjiActivity.class);
                break;

            case R.id.ll_share:

                start(ShareActivity.class);
                getActivity().overridePendingTransition(R.anim.alpha_enter,0);

                break;
            case R.id.ll_about:
                start(AboutUsActivity.class);
                break;
            case R.id.ll_fankui:

                start(FankuiActivity.class);
                break;
            case R.id.ll_my_collection:

                start(CollectionActivity.class);
                break;

        }
    }

}
