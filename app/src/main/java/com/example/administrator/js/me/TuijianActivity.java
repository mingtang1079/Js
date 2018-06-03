package com.example.administrator.js.me;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.common.ImageLoader;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.example.administrator.js.Http;
import com.example.administrator.js.R;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.me.adapter.TuijianAdapter;
import com.example.administrator.js.me.model.Tuijian;
import com.example.administrator.js.me.model.User;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TuijianActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_my_code)
    TextView mTvMyCode;
    @BindView(R.id.tv_copy)
    Button mTvCopy;
    @BindView(R.id.tv_my_money)
    TextView mTextViewMyMoney;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.iv_head)
    CircleImageView mIvHead;

    public LinearLayoutManager mLinearLayoutManager;
    TuijianAdapter mTuijianAdapter;
    List<User> mlist = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tuijian;
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
        mToolbar.setTitle("好友邀请");
        mlist = new ArrayList<>();
        mTuijianAdapter = new TuijianAdapter(R.layout.item_tuijian, mlist);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setAdapter(mTuijianAdapter);
        requestData();
        ImageLoader.load(mContext, UserManager.getInsatance().getUser().img, mIvHead);
    }

    @Override
    protected void requestData() {
        super.requestData();
        Http.getDefault().getTuijian(UserManager.getInsatance().getUser().id)
                .as(RxHelper.<Tuijian>handleResult(mContext))
                .subscribe(new ResponceSubscriber<Tuijian>() {
                    @Override
                    protected void onSucess(Tuijian mUsers) {

                        if (mUsers != null) {
                            mTvMyCode.setText(mUsers.invitecode + "");
                            mTextViewMyMoney.setText("我的奖励" + mUsers.invitemoney + "元");
                            if (mUsers.list != null) {
                                for (User m :
                                        mUsers.list) {
                                    if (m != null) {
                                        mlist.add(m);
                                    }
                                }
                                ;
                                mTuijianAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    protected void onFail(String message) {
                        showToast(message);
                    }
                });

    }

    private void save() {


    }


    @OnClick(R.id.tv_copy)
    public void onViewClicked() {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", mTvMyCode.getText().toString());
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        showToast("已复制到剪贴板");

    }
}
