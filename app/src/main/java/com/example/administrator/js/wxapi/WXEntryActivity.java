package com.example.administrator.js.wxapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.appbaselib.base.BaseActivity;
import com.example.administrator.js.R;
import com.example.administrator.js.constant.Constans;
import com.example.administrator.js.constant.EventMessage;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * 微信分享相
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wxentry;
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
        api = WXAPIFactory.createWXAPI(this, Constans.weixin);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq mBaseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {

        int code = resp.errCode;

        if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH)//登录
        {
            switch (code) {

                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //用户拒绝授权
                    showToast("拒绝授权微信登录");
                    finish();

                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //用户取消
                    showToast("取消了微信登录");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_OK:
                    //用户同意
                    EventBus.getDefault().post(new EventMessage.weixinLogin(((SendAuth.Resp) resp).code));
                    break;
            }
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX)//分享
        {

            switch (code) {
                case BaseResp.ErrCode.ERR_OK:
                    EventBus.getDefault().post(new EventMessage.shareSuceesState(0, null));
                    finish();

                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    EventBus.getDefault().post(new EventMessage.shareSuceesState(-1, null));
                    finish();

                    break;

            }

        }
    }
}
