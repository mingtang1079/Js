package com.example.administrator.js.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.constant.Constans;
import com.example.administrator.js.constant.EventMessage;
import com.example.administrator.js.utils.ShareUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShareActivity extends BaseActivity implements WbShareCallback {

    WbShareHandler shareHandler = new WbShareHandler(this);

    ImageView mImageViewWeixin;
    ImageView mImageViewWeibo;
    ImageView mImageViewqq;
    View mSpace;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_share;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void initView() {

        WbSdk.install(mContext, new AuthInfo(mContext, Constans.APP_KEY, Constans.REDIRECT_URL, Constans.SCOPE));

//注册你的ShareHandler：


        if (shareHandler == null) {
            shareHandler = new WbShareHandler((Activity) mContext);
        }

        shareHandler.registerApp();

        final ShareUtils shareUtil = new ShareUtils();
        shareUtil.regToWX(mContext);//向微信终端注册appID
        shareUtil.regToQQ(mContext);//向QQ终端注册appID

        mImageViewWeixin = findViewById(R.id.iv_weixin);
        mImageViewqq = findViewById(R.id.iv_qq);
        mImageViewWeibo = findViewById(R.id.iv_weibo);

        mImageViewWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                shareUtil.shareToWXSceneSession("http://www.cdmuscle.com", Constans.shareTitle, Constans.shareContent);
            }
        });
        mImageViewqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                shareUtil.shareToQQ((Activity) mContext, "http://www.cdmuscle.com", Constans.shareTitle,  Constans.shareContent, new IUiListener() {
                    @Override
                    public void onComplete(Object mO) {
                        onWbShareSuccess();
                    }

                    @Override
                    public void onError(UiError mUiError) {
                        onWbShareFail();
                    }

                    @Override
                    public void onCancel() {
                        onWbShareCancel();
                    }
                });

            }
        });
        mImageViewWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                shareUtil.sendMultiMessageToWeibo(mContext, "不错的健身软件，来看看吧", shareHandler);

            }
        });

        mSpace = findViewById(R.id.space);
        mSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                finish();
            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent, this);
    }

    @Override
    public void onWbShareSuccess() {
        showToast("分享成功");
        finish();
    }

    @Override
    public void onWbShareCancel() {
        showToast("取消分享");
        finish();

    }

    @Override
    public void onWbShareFail() {
        showToast("分享失败");
        finish();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.alpha_out);

    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStatus(EventMessage.shareSuceesState mShareSuceesState) {

        if (mShareSuceesState.message == 0) {
            onWbShareSuccess();

        } else if (mShareSuceesState.message == -1) {
            onWbShareCancel();

        }

    }


}
