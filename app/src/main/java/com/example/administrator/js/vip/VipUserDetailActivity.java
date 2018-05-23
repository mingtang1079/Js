package com.example.administrator.js.vip;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.model.UserDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = "/vip/VipUserDetailActivity")
public class VipUserDetailActivity extends BaseActivity {

    @Autowired
    User mUser;  //获取会员id


    @BindView(R.id.content)
    LinearLayout mLinearLayoutContent;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_age)
    TextView mTvAge;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_skill)
    TextView mTvSkill;
    @BindView(R.id.tv_keshi)
    TextView mTvKeshi;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.iv_yuyue)
    ImageView mIvYuyue;
    @BindView(R.id.tv_tizhong)
    TextView mTvTizhong;
    @BindView(R.id.tv_daixie)
    TextView mTvDaixie;
    @BindView(R.id.tv_shengao)
    TextView mTvShengao;
    @BindView(R.id.tv_zhibiao)
    TextView mTvZhibiao;
    @BindView(R.id.tv_tizhi)
    TextView mTvTizhi;
    @BindView(R.id.tv_neizang)
    TextView mTvNeizang;
    @BindView(R.id.tv_weidu)
    TextView mTvWeidu;
    @BindView(R.id.tv_dazhaohu)
    TextView mTvDazhaohu;
    @BindView(R.id.tv_guanzhu)
    TextView mTvGuanzhu;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_vip_user_detail;
    }

    @Override
    protected View getLoadingTargetView() {
        return mLinearLayoutContent;
    }

    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void initView() {

        mToolbar.setTitle("用户详情");
    }

    @Override
    protected void requestData() {
        super.requestData();

        Http.getDefault().userDetail(UserManager.getInsatance().getUser().id, mUser.id)
                .as(RxHelper.<UserDetail>handleResult(mContext))
                .subscribe(new ResponceSubscriber<UserDetail>() {
                    @Override
                    protected void onSucess(UserDetail mUserDetail) {

                        if (mUserDetail != null)
                            setData(mUserDetail);

                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });

    }

    private void setData(UserDetail mUserDetail) {

        User mUser=mUserDetail.userinfo;
        mTvName.setText(mUser.nickname);
        //年龄
        if (mUser.age != null || mUser.sex.equals("0")) {

            mTvAge.setText(mUser.age + "");
            if (mUser.sex.equals("1")) {
                //男性
                mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_men));

                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_men);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvAge.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_women);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                mTvAge.setCompoundDrawables(drawable, null, null, null);

                mTvAge.setBackground(mContext.getResources().getDrawable(R.drawable.com_round_corner_solid_women));

            }
        } else {

        }
       // mTvSkill.setText(mUserDetail.s);

    }


    @OnClick({R.id.tv_dazhaohu, R.id.tv_guanzhu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dazhaohu:
                break;
            case R.id.tv_guanzhu:
                break;
        }
    }
}
