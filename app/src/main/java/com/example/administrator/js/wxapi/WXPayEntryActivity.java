package com.example.administrator.js.wxapi;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.appbaselib.base.BaseActivity;
import com.appbaselib.base.Navigator;
import com.example.administrator.js.R;
import com.example.administrator.js.activity.fragment.PayFailFragment;
import com.example.administrator.js.activity.fragment.PaySuccessFragment;
import com.example.administrator.js.constant.Constans;
import com.example.administrator.js.constant.EventMessage;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * 微信支付
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private IWXAPI api;

    Navigator mNavigator;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wxpay_entry;
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
        mToolbar.setTitle("支付结果");
        mNavigator = new Navigator(getSupportFragmentManager(), R.id.content);
        api = WXAPIFactory.createWXAPI(this, Constans.weixin);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {


    }


    //名称	描述	解决方案
//0	成功	展示成功页面
//-1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
//-2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
    @Override
    public void onResp(BaseResp resp) {
        Log.d("tag----->", "onPayFinish, errCode = " + resp.errCode);
        int code = resp.errCode;

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (code) {
                case 0:
                    //更新订单列表状态
                    EventBus.getDefault().post(new EventMessage.PaySucessResult());
                    mNavigator.showFragment(new PaySuccessFragment());
                    break;
                case -1:

                    mNavigator.showFragment(new PayFailFragment());
                    break;
                case -2:
                    showToast("取消支付");
                    finish();
                    break;
                default:
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }

    }
}
