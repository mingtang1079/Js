package com.example.administrator.js.interceptor;

import com.appbaselib.utils.DateUtils;
import com.appbaselib.utils.Md5AndSha1CaculateUtils;
import com.example.administrator.js.constant.Constans;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.administrator.js.constant.Constans.rongAppKey;
import static com.example.administrator.js.constant.Constans.rongAppScrect;

/**
 * Created by tangming on 2018/5/15.
 */

public class RongInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.url().toString().equals(Constans.rongyunUrl)) {

            String signatureValue = Md5AndSha1CaculateUtils.sha1(rongAppScrect + "1" + DateUtils.getCurrentTimeInString());

            Request authorised = originalRequest.newBuilder()
                    .addHeader("App-Key", rongAppKey)
                    .addHeader("Nonce", "1")
                    .addHeader("Timestamp", DateUtils.getCurrentTimeInString())
                    .addHeader("Signature", signatureValue)
                    .build();
            return chain.proceed(authorised);
        }
        return chain.proceed(originalRequest);
    }
}
