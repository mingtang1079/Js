package com.example.administrator.js.me;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/me/UserInfoActivity")
public class UserInfoActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_head)
    LinearLayout mLlHead;
    @BindView(R.id.ll_nick)
    LinearLayout mLlNick;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.ll_area)
    LinearLayout mLlArea;
    @BindView(R.id.ll_barcode)
    LinearLayout mLlBarcode;
    @BindView(R.id.ll_weixin)
    LinearLayout mLlWeixin;
    @BindView(R.id.ll_phone)
    LinearLayout mLlPhone;
    @BindView(R.id.ll_password)
    LinearLayout mLlPassword;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_weixin)
    TextView mTvWeixin;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_password)
    TextView mTvPassword;
    @BindView(R.id.tv_exit)
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
        User mUser = UserManager.getInsatance().getUser();
        mTvUserName.setText(mUser.nickname);
        if ("1".equals(UserManager.getInsatance().getUser().sex))
            mTvSex.setText("男");
        else if ("2".equals(UserManager.getInsatance().getUser().sex))
            mTvSex.setText("女");
        mTvAddress.setText(mUser.address);
        mTvPhone.setText(mUser.mobile);

    }

    @OnClick({R.id.ll_head, R.id.ll_nick, R.id.ll_sex, R.id.ll_area, R.id.ll_barcode, R.id.ll_weixin, R.id.ll_phone, R.id.ll_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_head:

                ARouter.getInstance().build("/me/ChangeUserHeadActivity")
                        .withString("name", mTvUserName.getText().toString())
                        .navigation();

                break;
            case R.id.ll_nick:
                ARouter.getInstance().build("/me/ChangeNameActivity")
                        .withString("name", mTvUserName.getText().toString())
                        .navigation();
                break;
            case R.id.ll_sex:
                String sex;
                if (TextUtils.isEmpty(mTvUserName.getText().toString()))
                    sex = "";
                else if (mTvUserName.getText().toString().equals("男"))
                    sex = "1";
                else sex = "2";

                ARouter.getInstance().build("/me/ChangeSexActivity")
                        .withString("sex", sex)
                        .navigation();
                break;
            case R.id.ll_area:
                break;
            case R.id.ll_barcode:
                break;
            case R.id.ll_weixin:
                break;
            case R.id.ll_phone:
                break;
            case R.id.ll_password:
                break;
        }
    }
}
