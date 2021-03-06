package com.example.administrator.js.me;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.constant.Constants;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.AdressHelper;
import com.appbaselib.utils.DatePickerDialogUtils;
import com.appbaselib.utils.DateUtils;
import com.appbaselib.utils.DialogUtils;
import com.appbaselib.utils.PreferenceUtils;
import com.appbaselib.view.datepicker.view.GregorianLunarCalendarView;
import com.appbaselib.view.datepicker.view.OnDateSelectedListener;
import com.example.administrator.js.App;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.login.LoginActivity;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.presenter.UserPresenter;
import com.example.administrator.js.utils.TimeUtils;
import com.mic.adressselectorlib.City;
import com.mic.adressselectorlib.OnItemClickListener;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

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
            } else {
                mTextViewZhifubao.setText("未绑定");

            }
            if (!TextUtils.isEmpty(mUser.birthdate))
                mTextViewBirthday.setText(TimeUtils.getTime(mUser.birthdate));
            if (!TextUtils.isEmpty(mUser.openid)) {
                mTvWeixin.setText("已绑定");
                mTvWeixin.setText(mUser.wxnickname);
            } else {
                mTvWeixin.setText("未绑定");

            }
        }
    }

    @OnClick({R.id.ll_head, R.id.ll_nick, R.id.ll_sex, R.id.ll_area,
            R.id.ll_barcode, R.id.ll_weixin, R.id.ll_phone, R.id.ll_password, R.id.tv_exit, R.id.ll_zhifubao,
            R.id.iv_head,
            R.id.ll_birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:

                ARouter.getInstance().build("/me/ChangeUserHeadActivity")
                        .withString("name", mTvUserName.getText().toString())
                        .navigation();
                break;

            case R.id.ll_head:

                ARouter.getInstance().build("/me/ChangeUserHeadActivity")
                        .withString("name", mTvUserName.getText().toString())
                        .withBoolean("isStart", true)
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

                if (!"未绑定".equals(mTvWeixin.getText().toString())) {
                    DialogUtils.getDefaultDialog(mContext, "提示", "确定解除绑定吗？", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface mDialogInterface, int mI) {

                            Http.getDefault().unbindwechat(UserManager.getInsatance().getUser().id)
                                    .as(RxHelper.<String>handleResult(mContext))
                                    .subscribe(new ResponceSubscriber<String>(mContext) {
                                        @Override
                                        protected void onSucess(String mS) {
                                            mTvWeixin.setText("未绑定");
                                            User mUser = UserManager.getInsatance().getUser();
                                            mUser.openid=null;
                                            PreferenceUtils.saveObjectAsGson(mContext, Constants.PRE_USER, mUser);

                                        }

                                        @Override
                                        protected void onFail(String message) {
                                            showToast(message);
                                        }
                                    });
                        }
                    }).show();

                } else {

                    if (!App.mInstance.api.isWXAppInstalled()) {
                        showToast("请安装微信");
                    } else {
                        final SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_demo_test";
                        App.mInstance.api.sendReq(req);
                    }

                }

                break;
            case R.id.ll_phone:
                start(ChangeMobileActivity.class);

                break;
            case R.id.ll_password:
                start(ForgetPasswordActivity.class);
                break;
            case R.id.ll_zhifubao:

                ARouter.getInstance().build("/me/AddAlipayActivity")
                        .withString("name", UserManager.getInsatance().getUser().alipay)
                        .navigation();
                break;
            case R.id.tv_exit:

                DialogUtils.getDefaultDialog(mContext, "提示", "确定退出吗？", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface mDialogInterface, int mI) {

                        UserManager.getInsatance().logout();
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

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatusChange(EventMessage.weixinLogin mListStatusChange) {
        requestUserInfo(mListStatusChange.code);

    }

    private void requestUserInfo(final String mCode) {
        Map<String, String> mStringStringMap = new HashMap<>();
        mStringStringMap.put("code", mCode);
        mStringStringMap.put("type", "2");
        mStringStringMap.put("userid", UserManager.getInsatance().getUser().id);

        Http.getDefault().getUserInfoByWxCode(mStringStringMap)
                .as(RxHelper.<User>handleResult(mContext))
                .subscribe(new ResponceSubscriber<User>() {
                    @Override
                    protected void onSucess(User mUser) {
                        mTvWeixin.setText("已绑定");
                        mTvWeixin.setText(mUser.wxnickname);
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

}
