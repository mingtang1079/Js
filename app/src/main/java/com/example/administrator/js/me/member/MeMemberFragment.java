package com.example.administrator.js.me.member;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseFragment;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.BuildConfig;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.activity.MessageActivity;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.course.CourseCanlenderActivity;
import com.example.administrator.js.me.AboutUsActivity;
import com.example.administrator.js.me.BarcodeActivity;
import com.example.administrator.js.me.CollectionActivity;
import com.example.administrator.js.me.FankuiActivity;
import com.example.administrator.js.me.PriceListActivity;
import com.example.administrator.js.me.ServiceTimeListActivity;
import com.example.administrator.js.me.SettingActivity;
import com.example.administrator.js.me.TeacherBiDuActivity;
import com.example.administrator.js.me.TongjiActivity;
import com.example.administrator.js.me.TuijianActivity;
import com.example.administrator.js.me.VipSupplyActivity;
import com.example.administrator.js.me.ZizhiActivity;
import com.example.administrator.js.me.model.RealUserInfo;
import com.example.administrator.js.me.model.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    protected boolean registerEventBus() {
        return true;
    }



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
    protected void requestData() {
        super.requestData();

        Http.getDefault().getRealNameInfo(mUser.id)
                .as(RxHelper.<RealUserInfo>handleResult(mContext))
                .subscribe(new ResponceSubscriber<RealUserInfo>() {
                    @Override
                    protected void onSucess(RealUserInfo mUser) {

                        if (mUser != null) {
                            mRealUserInfo = mUser;
                            if (!TextUtils.isEmpty(mUser.status)) {
                                if ("1".equals(mUser.status)) {
                                    mTextViewVerify.setText("已认证");
                                } else if ("2".equals(mUser.status)) {
                                    mTextViewVerify.setText("认证不通过");

                                } else {
                                    mTextViewVerify.setText("审核中");

                                }
                            }
                        } else {
                            mTextViewVerify.setText("未认证");

                        }
                    }

                    @Override
                    protected void onFail(String message) {

                    }
                });
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

            if (!TextUtils.isEmpty(mUser.depositstatus)) {

                if ("0".equals(mUser.depositstatus)) {
                    mTextViewYajingStatus.setText("未交");
                } else if ("1".equals(mUser.depositstatus)) {
                    mTextViewYajingStatus.setText("已交");

                } else if ("2".equals(mUser.depositstatus)) {
                    mTextViewYajingStatus.setText("免押金");


                }
            }

            //年龄
            //年龄
            if (mUser.age != null && mUser.sex != null) {
                mTextViewAge.setText(mUser.age + "");
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


    @OnClick({R.id.ll_barcode, R.id.iv_add, R.id.iv_mes, R.id.iv_setting, R.id.tv_name, R.id.tv_id, R.id.iv_barcode, R.id.ll_zizhi, R.id.ll_share, R.id.ll_my_collection,
            R.id.ll_shenqing, R.id.ll_richeng, R.id.ll_dingjia, R.id.ll_tongji, R.id.ll_yajing, R.id.ll_bidu, R.id.ll_about, R.id.ll_fankui, R.id.ll_wufu_time, R.id.ll_tuijian, R.id.tv_verify})
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


                start(SettingActivity.class);
                break;
            case R.id.tv_name:

                ARouter.getInstance().build("/me/UserInfoActivity")
                        .navigation();
                break;
            case R.id.tv_id:

                break;
            case R.id.iv_barcode:
                break;

            case R.id.ll_zizhi:

                if (mRealUserInfo != null) {
                    if (mRealUserInfo.status.equals("1")) {
                        start(ZizhiActivity.class);
                    } else {
                        showToast("未通过认证不能进行教学资质编辑");
                    }
                }
                break;

            case R.id.ll_shenqing:

                start(VipSupplyActivity.class);
                break;
            case R.id.ll_richeng:

                start(CourseCanlenderActivity.class);
                break;
            case R.id.ll_tongji:

                start(TongjiActivity.class);
                break;
            case R.id.ll_yajing:

                yajing();

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

                start(CollectionActivity.class);
                break;

            case R.id.ll_wufu_time:

                start(ServiceTimeListActivity.class);

                break;
            case R.id.ll_tuijian:
                start(TuijianActivity.class);
                break;

            case R.id.tv_verify:

                if (mRealUserInfo != null) {
                    if (!TextUtils.isEmpty(mRealUserInfo.status)) {
                        if ("1".equals(mRealUserInfo.status)) {

                            if (BuildConfig.DEBUG) {
                                ARouter.getInstance().build("/me/RealNameVerifyActivity")
                                        .navigation();
                            }

                        } else if ("2".equals(mRealUserInfo.status)) {
                            ARouter.getInstance().build("/me/RealNameVerifyActivity")
                                    .navigation();

                        } else {
                            if (BuildConfig.DEBUG) {
                                ARouter.getInstance().build("/me/RealNameVerifyActivity")
                                        .navigation();
                            }

                        }
                    }

                } else {
                    ARouter.getInstance().build("/me/RealNameVerifyActivity")
                            .navigation();
                }

                break;

            case R.id.ll_dingjia:

                start(PriceListActivity.class);
                break;
        }
    }

}
