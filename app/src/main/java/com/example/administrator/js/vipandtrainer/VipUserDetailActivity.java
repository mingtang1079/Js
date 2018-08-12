package com.example.administrator.js.vipandtrainer;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.BaseModel;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.network.ResponceSubscriber2;
import com.appbaselib.rx.RxHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.model.UserDetail;
import com.example.administrator.js.vipandtrainer.adapter.UserdetailImageAdapter;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;

@Route(path = "/vip/VipUserDetailActivity")
public class VipUserDetailActivity extends BaseActivity {

    @Autowired
    String id;  //获取会员id


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
    @BindView(R.id.tv_detail)
    TextView mTextViewDetail;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.view_line)
    View mViewLine;

    @BindView(R.id.ll_body_detail)
    LinearLayout mLinearLayoutDetailAndBody;
    @BindView(R.id.ll_button)
    LinearLayout mLinearLayoutButton;
    @BindView(R.id.ll_yuyue)
    LinearLayout mLinearLayoutYuyue;

    @BindView(R.id.tv_message)
    TextView mTextViewMeassage;


    MenuItem mMenuItem;

    UserDetail mUserDetail;

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
        mToolbar.inflateMenu(R.menu.user_detail);
        mMenuItem = mToolbar.getMenu().findItem(R.id.add_hei);
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem mMenuItem) {
                if (mMenuItem.getTitle().equals("加入黑名单")) {
                    handleUser("2");
                } else {
                    handleUser("0");

                }
                return false;
            }
        });
        toggleShowLoading(true);

        requestData();
    }

    @Override
    protected void requestData() {
        super.requestData();

        Http.getDefault().userDetail(UserManager.getInsatance().getUser().id, id)
                .as(RxHelper.<UserDetail>handleResult(mContext))
                .subscribe(new ResponceSubscriber2<UserDetail>() {
                    @Override
                    protected void onSucess(BaseModel<UserDetail> t) {
                        if (t != null && t.data != null) {
                            mUserDetail = t.data;
                            mTextViewMeassage.setText(t.msg);
                            setData(t.data);
                        }
                        toggleShowLoading(false);
                    }

                    @Override
                    protected void onFail(String message) {
                        loadError();
                    }
                });

    }

    private void setData(UserDetail mUserDetail) {

        //黑名单或者其他原因不能展示
        if (mUserDetail.code != 0) {
            mLinearLayoutDetailAndBody.setVisibility(View.GONE);
            mLinearLayoutButton.setVisibility(View.GONE);
            mLinearLayoutYuyue.setVisibility(View.GONE);
            mTextViewMeassage.setVisibility(View.VISIBLE);
        } else {
            mLinearLayoutDetailAndBody.setVisibility(View.VISIBLE);
            mLinearLayoutButton.setVisibility(View.VISIBLE);
            mLinearLayoutYuyue.setVisibility(View.VISIBLE);
            mTextViewMeassage.setVisibility(View.GONE);

        }


        User mUser = mUserDetail.userinfo;
        mTvName.setText(mUser.nickname);
        if (mUserDetail.isOrderd) {
            mIvYuyue.setImageResource(R.drawable.icon_xingxing_selsected);
        } else {
            mIvYuyue.setImageResource(R.drawable.icon_xingxing);

        }
        mTvId.setText("ID："+mUser.no + "");
        ImageLoader.load(mContext, mUser.img, mIvHead);
        //年龄
        if (mUser.sex.equals("0")) {

            if (mUser.age != null) {
                mTvAge.setText(mUser.age + "");
            }
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
        if (mUserDetail.need != null) {
            mTvSkill.setText(mUserDetail.need == null ? "" : mUserDetail.need.coursetypenames);
            mTvKeshi.setText(mUserDetail.need.csum + "课时");
            mTvArea.setText(mUserDetail.need.areaname);
            mTextViewDetail.setText(mUserDetail.need.detail);

            if (!TextUtils.isEmpty(mUserDetail.need.detailimg)) {
                String[] mStrings = mUserDetail.need.detailimg.split(",");
                final ArrayList<String> mList = new ArrayList<>(mStrings.length);
                for (String m:mStrings)
                {
                    mList.add(m);
                }
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
                mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                UserdetailImageAdapter mUserdetailImageAdapter=new UserdetailImageAdapter(R.layout.item_user_detail_image, mList);
                mRecyclerView.setAdapter(mUserdetailImageAdapter);
                mUserdetailImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        PhotoPreviewIntent intent = new PhotoPreviewIntent(mContext);
                        intent.setCurrentItem(position);
                        intent.setPhotoPaths(mList);
                        intent.setIsShowDelete(false);
                        startActivity(intent);
                    }
                });
            }

        }
// 与我的关系          //是否接单tradestatus:0否1是, status:0没有关系,1已关注,2已拉黑

        if (mUserDetail.relation != null) {
            if (mUserDetail.relation.status.equals("2")) {
                mTvGuanzhu.setVisibility(View.GONE);
                mTvDazhaohu.setVisibility(View.GONE);
                mMenuItem.setTitle("取消黑名单");
//黑名单不显示
                mTvDazhaohu.setVisibility(View.GONE);
                mViewLine.setVisibility(View.GONE);
            } else {
                mTvGuanzhu.setVisibility(View.VISIBLE);
                mMenuItem.setTitle("加入黑名单");
                mTvDazhaohu.setVisibility(View.VISIBLE);
            }
            //已接单不能拉黑
            if (!TextUtils.isEmpty(mUserDetail.relation.tradestatus) && mUserDetail.relation.tradestatus.equals("1")) {

                mMenuItem.setVisible(false);

                if (mUserDetail.relation.status.equals("1")) {
                    mTvGuanzhu.setText("取消关注");
                } else {
                    mTvGuanzhu.setText("关注");
                }

            }

        }
        //身体数据

        if (mUserDetail.bodydata != null) {
            mTvTizhong.setText(mUserDetail.bodydata.weight + "");
            mTvShengao.setText(mUserDetail.bodydata.height + "");
            mTvDaixie.setText(mUserDetail.bodydata.bmr + "");
            mTvZhibiao.setText(mUserDetail.bodydata.bmi + "");
            mTvTizhi.setText(mUserDetail.bodydata.fat + "");
            mTvNeizang.setText(mUserDetail.bodydata.visceralfat);
//            StringBuilder mStringBuilder = new StringBuilder();
//            if (!TextUtils.isEmpty(mUserDetail.bodydata.wdxiong))
//                mStringBuilder.append("胸：" + mUserDetail.bodydata.wdxiong);
//            if (!TextUtils.isEmpty(mUserDetail.bodydata.wdyao))
//                mStringBuilder.append("腰：" + mUserDetail.bodydata.wdyao);
//            if (!TextUtils.isEmpty(mUserDetail.bodydata.wdxiong))
//                mStringBuilder.append("小腿：" + mUserDetail.bodydata.wdxiaotui);
//            if (!TextUtils.isEmpty(mUserDetail.bodydata.wdxiong))
//                mStringBuilder.append("大腿：" + mUserDetail.bodydata.wddatui);
//
//            if (!TextUtils.isEmpty(mUserDetail.bodydata.wddabi))
//                mStringBuilder.append("大臂：" + mUserDetail.bodydata.wddabi);
//            if (!TextUtils.isEmpty(mUserDetail.bodydata.wdxiaobi))
//                mStringBuilder.append("小臂：" + mUserDetail.bodydata.wdxiaobi);
//            if (!TextUtils.isEmpty(mUserDetail.bodydata.wdtun))
//                mStringBuilder.append("臀：" + mUserDetail.bodydata.wdtun);
//            if (!TextUtils.isEmpty(mUserDetail.bodydata.wdjian))
//                mStringBuilder.append("肩：" + mUserDetail.bodydata.wdjian);
//
//            mTvWeidu.setText(mStringBuilder);
            mTvWeidu.setText(mUserDetail.bodydata.wddetail);
        }

    }


    @OnClick({R.id.tv_dazhaohu, R.id.tv_guanzhu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dazhaohu:
                RongIM.getInstance().startPrivateChat(this, id, mUserDetail.userinfo.nickname);

                break;
            case R.id.tv_guanzhu:

//操作 0取消关注,取消拉黑,1关注,2拉黑
                if (mTvGuanzhu.getText().equals("关注")) {

                    handleUser("1");

                } else if (mTvGuanzhu.getText().equals("取消关注")) {
                    handleUser("0");

                } else if (mTvGuanzhu.getText().equals("从黑名单中删除")) {
                    handleUser("1");

                }
                break;
        }
    }

    private void handleUser(String mS) {
        Http.getDefault().handleUser(UserManager.getInsatance().getUser().id, id, mS)
                .as(RxHelper.<String>handleResult(mContext))
                .subscribe(new ResponceSubscriber<String>(mContext) {
                    @Override
                    protected void onSucess(String mS) {

                        if (mUserDetail.relation == null) {
                            mUserDetail.relation = new UserDetail.Relation();
                        }
                        mUserDetail.relation.status = mS;
                        setData(mUserDetail);

                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });


    }
}
