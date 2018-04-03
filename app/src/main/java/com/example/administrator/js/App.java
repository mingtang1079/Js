package com.example.administrator.js;

import android.content.Context;
import android.util.Log;

import com.appbaselib.app.BaseApplication;
import com.appbaselib.utils.SystemUtils;
import com.pangu.appbaselibrary.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

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

        initBugly();

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
        boolean isDebug = com.pangu.appbaselibrary.BuildConfig.DEBUG;
        /**
         * 配置Bugly,第三个参数为SDK调试模式开关，调试模式的行为特性如下：
         * 输出详细的Bugly SDK的Log；
         * 每一条Crash都会被立即上报；
         * 自定义日志将会在Logcat中输出。
         * 建议在测试阶段建议设置成true，发布时设置为false */
        CrashReport.initCrashReport(context, buglyAppId(), isDebug, strategy);
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

    private List<Interceptor> getNetworkInterceptors() {
        return null;
    }

    private List<Interceptor> getInterceptors() {
        return null;
    }

}
