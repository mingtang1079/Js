package com.example.core;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.appbaselib.app.BaseApplication;
import com.appbaselib.utils.SystemUtils;
import com.example.core.model.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by tangming on 2018/1/18.
 */

public class App extends BaseApplication {

    private OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttp();
        initRouter();
    }


    protected String buglyAppId() {
        return "";
    }


    private void initOkHttp() {
        OkHttpClient.Builder clientBuilder;

        clientBuilder = new OkHttpClient.Builder();
        List<Interceptor> interceptors = getInterceptors();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                clientBuilder.addInterceptor(interceptor);
            }
        }
        List<Interceptor> interceptors1 = getNetworkInterceptors();
        if (interceptors1 != null) {
            for (Interceptor interceptor : interceptors1) {
                clientBuilder.addInterceptor(interceptor);
            }
        }

        clientBuilder.connectTimeout(10 * 1000L, TimeUnit.MILLISECONDS);
        clientBuilder.readTimeout(10 * 1000L, TimeUnit.MILLISECONDS);
        clientBuilder.retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("HttpReponse:", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
        }
        clientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        client = clientBuilder.build();
    }


    private void initRouter() {

       // if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
     //   }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
    public User getUser() {

        return null;
    }

    private List<Interceptor> getNetworkInterceptors() {
        return null;
    }

    private List<Interceptor> getInterceptors() {
        return null;
    }

}
