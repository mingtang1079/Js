package com.example.administrator.js.interceptor;

import com.appbaselib.utils.PackageUtil;
import com.appbaselib.utils.PreferenceUtils;
import com.appbaselib.utils.SystemUtils;
import com.example.administrator.js.App;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.constant.Constans;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        if (UserManager.getInsatance().getUser() != null) {
            Request request = original.newBuilder()
                    .header("apitoken", PreferenceUtils.getPrefString(App.mInstance, Constans.TOKEN,""))
                    .header("osname", "0")
                    .header("versionno", PackageUtil.getAppVersionName(App.mInstance))
                    .header("deviceid", SystemUtils.getDeviceBrand())
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);

        } else {
            return chain.proceed(original);
        }
    }
}
