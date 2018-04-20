package com.example.mine.me;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.example.core.UserManager;
import com.example.core.model.User;
import com.example.mine.R;
import com.example.mine.R2;
import com.example.mine.presenter.UserPresenter;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/me/UserInfoActivity")
public class UserInfoActivity extends BaseActivity implements UserPresenter.UserResponse {


    @BindView(R2.id.toolbar)
    Toolbar mToolbar;
    @BindView(R2.id.ll_head)
    LinearLayout mLlHead;
    @BindView(R2.id.ll_nick)
    LinearLayout mLlNick;
    @BindView(R2.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R2.id.ll_area)
    LinearLayout mLlArea;
    @BindView(R2.id.ll_barcode)
    LinearLayout mLlBarcode;
    @BindView(R2.id.ll_weixin)
    LinearLayout mLlWeixin;
    @BindView(R2.id.ll_phone)
    LinearLayout mLlPhone;
    @BindView(R2.id.ll_password)
    LinearLayout mLlPassword;
//    @BindView(R2.id.tv_user_name)
//    TextView mTvUserName;
//    @BindView(R2.id.tv_sex)
//    TextView mTvSex;
//    @BindView(R2.id.tv_address)
//    TextView mTvAddress;
//    @BindView(R2.id.tv_weixin)
//    TextView mTvWeixin;
//    @BindView(R2.id.tv_phone)
//    TextView mTvPhone;
//    @BindView(R2.id.tv_password)
//    TextView mTvPassword;
    @BindView(R2.id.tv_exit)
    TextView mTextViewExit;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user_info;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("个人资料");
        updateUser();
    }

    @OnClick({R2.id.ll_head, R2.id.ll_nick, R2.id.ll_sex, R2.id.ll_area, R2.id.ll_barcode, R2.id.ll_weixin, R2.id.ll_phone, R2.id.ll_password,R2.id.tv_exit})
    public void onViewClicked(View view) {

        if (R.id.ll_head == view.getId()) {

            ARouter.getInstance().build("/me/ChangeUserHeadActivity")
                    .navigation();
        } else if (R.id.ll_nick==view.getId())
        {
            ARouter.getInstance().build("/me/ChangeNameActivity")
                    .navigation();
        }
        else if (R.id.ll_sex==view.getId())
        {
            ARouter.getInstance().build("/me/ChangeSexActivity")
                    .navigation();
        }
    }

    @Override
    public void onSuccess() {
        updateUser();
    }

    private void updateUser() {
        User mUser = UserManager.getInsatance().getUser();
        if (mUser!=null) {
//            mTvUserName.setText(mUser.nickname);
//            mTvSex.setText(mUser.sex);
//            mTvAddress.setText(mUser.address);
//            mTvWeixin.setText(mUser.openid + "");
//            mTvPhone.setText(mUser.mobile + "");
        }

    }

    @Override
    public void onFail(String mes) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            updateUser();
        }
    }

}
