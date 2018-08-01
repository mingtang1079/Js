package com.example.administrator.js.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.utils.AdressHelper;
import com.appbaselib.utils.DatePickerDialogUtils;
import com.appbaselib.utils.DateUtils;
import com.appbaselib.utils.DialogUtils;
import com.appbaselib.utils.PreferenceUtils;
import com.appbaselib.view.datepicker.view.GregorianLunarCalendarView;
import com.appbaselib.view.datepicker.view.OnDateSelectedListener;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.login.LoginActivity;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.presenter.UserPresenter;
import com.example.administrator.js.utils.TimeUtils;
import com.mic.adressselectorlib.City;
import com.mic.adressselectorlib.OnItemClickListener;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;

@Route(path = "/me/UserInfoActivity")
public class UserInfoActivity extends BaseActivity implements UserPresenter.UserResponse {


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
    @BindView(R.id.iv_head)
    CircleImageView head;
    @BindView(R.id.ll_zhifubao)
    LinearLayout mLinearLayoutZhifubao;
    @BindView(R.id.tv_zhifubao)
    TextView mTextViewZhifubao;
    @BindView(R.id.tv_birthday)
    TextView mTextViewBirthday;

    UserPresenter mUserPresenter;

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

        mUserPresenter = new UserPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUser();
    }

    private void updateUser() {
        User mUser = UserManager.getInsatance().getUser();
        if (mUser != null) {
            mTvUserName.setText(mUser.nickname);
            ImageLoader.load(mContext, mUser.img, head);
            if ("1".equals(UserManager.getInsatance().getUser().sex))
                mTvSex.setText("男");
            else if ("2".equals(UserManager.getInsatance().getUser().sex))
                mTvSex.setText("女");
            mTvAddress.setText(mUser.address);
            mTvPhone.setText(mUser.mobile);
            if (!TextUtils.isEmpty(mUser.alipay)) {
                mTextViewZhifubao.setText(mUser.alipay + "");
            }
            if (!TextUtils.isEmpty(mUser.birthdate))
                mTextViewBirthday.setText(TimeUtils.getTime(mUser.birthdate));
            if (!TextUtils.isEmpty(mUser.openid)) {
                mTvWeixin.setText("已绑定");
            } else {
                mTvWeixin.setText("未绑定");

            }
        }
    }

    @OnClick({R.id.ll_head, R.id.ll_nick, R.id.ll_sex, R.id.ll_area, R.id.ll_barcode, R.id.ll_weixin, R.id.ll_phone, R.id.ll_password, R.id.tv_exit, R.id.ll_zhifubao, R.id.ll_birthday})
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
                AdressHelper.showAddressSelector(mContext, new OnItemClickListener() {
                    @Override
                    public void itemClick(City mProvice, City mCity, City mCounty) {
                        mTvAddress.setText(mProvice.name + " " + mCity.name + " " + mCounty.name);

                        mUserPresenter.updateUser("areacode", mCounty.id);

                    }
                });

                break;
            case R.id.ll_barcode:
                start(BarcodeActivity.class);
                break;
            case R.id.ll_weixin:


                break;
            case R.id.ll_phone:
                start(ChangeMobileActivity.class);

                break;
            case R.id.ll_password:
                start(ForgetPasswordActivity.class);
                break;
            case R.id.ll_zhifubao:

                start(AddAlipayActivity.class);
                break;
            case R.id.tv_exit:

                DialogUtils.getDefaultDialog(mContext, "提示", "确定退出吗？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialogInterface, int mI) {

                        RongIM.getInstance().logout();
                        PreferenceUtils.clearDefaultPreference(mContext);
                        UserManager.getInsatance().setUser(null);
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }).show();

                break;
            case R.id.ll_birthday:

                DatePickerDialogUtils.getDefaultDatePickerDialog(mContext, new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(GregorianLunarCalendarView.CalendarData mCalendarData) {
                        //       time = DateUtils.getTimeLongYmd(mCalendarData.getTime());
                        mTextViewBirthday.setText(mCalendarData.getTime());
                        mUserPresenter.updateUser("birthdate", mCalendarData.getTime());

                    }
                }).show();
                break;
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail(String mes) {
        showToast(mes);
    }
}
