package com.example.administrator.js;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.app.BaseApplication;
import com.appbaselib.base.BaseModel;
import com.appbaselib.network.ResponceSubscriber;
import com.appbaselib.rx.RxHelper;
import com.appbaselib.utils.CommonUtils;
import com.appbaselib.utils.JsonUtil;
import com.appbaselib.utils.LogUtils;
import com.appbaselib.utils.SystemUtils;
import com.example.administrator.js.constant.Constans;
import com.example.administrator.js.interceptor.RongInterceptor;
import com.example.administrator.js.login.RongYunToken;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.service.LocationService;
import com.google.gson.JsonObject;
import com.mic.adressselectorlib.CityHelper;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by tangming on 2018/1/18.
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initIM();
        initBugly();
        initRouter();
        startService();
        //参数3:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        CityHelper.getInsatance().getCities(this);//初始化
    }

    private void startService() {

        //启动位置Service
        Intent intentFive = new Intent(this, LocationService.class);
        startService(intentFive);

    }

    private void initIM() {
        if (getApplicationInfo().packageName.equals(CommonUtils.getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);
            //自定义点击事件
            RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {

                @Override
                public boolean onConversationPortraitClick(Context mContext, Conversation.ConversationType mConversationType, String mS) {
                    return false;
                }

                @Override
                public boolean onConversationPortraitLongClick(Context mContext, Conversation.ConversationType mConversationType, String mS) {
                    return false;
                }

                @Override
                public boolean onConversationLongClick(Context mContext, View mView, UIConversation mUIConversation) {
                    return false;
                }

                @Override
                public boolean onConversationClick(Context mContext, View mView, UIConversation uiConversation) {
                    uiConversation.setUnReadMessageCount(0);
                    RongIM.getInstance().clearMessagesUnreadStatus(uiConversation.getConversationType(),uiConversation.getConversationTargetId(),null);

                    if (uiConversation.getConversationType().toString().equals(Conversation.ConversationType.SYSTEM.toString())) {
                        ARouter.getInstance().build("/activity/SystemMessageActivity")
                                .navigation(mContext);
                    }
                    else {
                        RongIM.getInstance().startConversation(mContext, Conversation.ConversationType.PRIVATE, uiConversation.getConversationTargetId(), uiConversation.getUIConversationTitle());
                    }

                    return true;
                }
            });
            RongIM.setConversationClickListener(new RongIM.ConversationClickListener() {
                @Override
                public boolean onUserPortraitClick(Context mContext, Conversation.ConversationType mConversationType, UserInfo mUserInfo, String mS) {

                    ARouter.getInstance().build("/vip/VipUserDetailActivity")
                            .withString("id", mUserInfo.getUserId())
                            .navigation(mContext);
                    return true;
                }

                @Override
                public boolean onUserPortraitLongClick(Context mContext, Conversation.ConversationType mConversationType, UserInfo mUserInfo, String mS) {
                    return false;
                }

                @Override
                public boolean onMessageClick(Context mContext, View mView, Message mMessage) {
                    return false;
                }

                @Override
                public boolean onMessageLinkClick(Context mContext, String mS, Message mMessage) {
                    return false;
                }

                @Override
                public boolean onMessageLongClick(Context mContext, View mView, Message mMessage) {
                    return false;
                }
            });
            connectRongYun();
        }
    }

    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        String processName = SystemUtils.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        boolean isDebug = BuildConfig.DEBUG;
        /**
         * 配置Bugly,第三个参数为SDK调试模式开关，调试模式的行为特性如下：
         * 输出详细的Bugly SDK的Log；
         * 每一条Crash都会被立即上报；
         * 自定义日志将会在Logcat中输出。
         * 建议在测试阶段建议设置成true，发布时设置为false */
        Bugly.init(getApplicationContext(), "b10f100f0d", false);
    }

    private void initRouter() {

        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public void connectRongYun() {

        User mUser = UserManager.getInsatance().getUser();
        if (mUser == null) {
            return;
        }

//        Http.getDefault().getUserRongYunToken(Constans.rongyunUrl, mUser.id, mUser.nickname, mUser.img)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultObserver<JsonObject>() {
//                    @Override
//                    public void onNext(JsonObject mS) {
//
//                        RongYunToken mRongYunToken = JsonUtil.fromJson(mS.toString(), RongYunToken.class);
//                        connect(mRongYunToken.token);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
        ;

        connect(mUser.token);
    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {#init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(CommonUtils.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    LogUtils.d("IM token错误");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {

                    LogUtils.d("IM连接成功");

                    requestUserInfo();


                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.d("IM连接失败！ ---->" + errorCode);

                }
            });
        }
    }

    private void requestUserInfo() {
        RongIM.setUserInfoProvider(mUserInfoProvider, true);


    }

    RongIM.UserInfoProvider mUserInfoProvider = new RongIM.UserInfoProvider() {
        @Override
        public UserInfo getUserInfo(String mS) {
            io.reactivex.Observable<BaseModel<User>> mObservable = Http.getDefault().getUser(mS);
            BaseModel<User> mUserBaseModel = mObservable.blockingFirst();
            if (mUserBaseModel.status) {

                return new UserInfo(mUserBaseModel.data.id, mUserBaseModel.data.nickname, Uri.parse(mUserBaseModel.data.img));
            } else {
                return null;
            }
        }
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 安装tinker
        Beta.installTinker();
    }
}
