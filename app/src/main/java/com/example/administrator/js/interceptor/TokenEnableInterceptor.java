package com.example.administrator.js.interceptor;

import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;

import com.appbaselib.app.AppManager;
import com.appbaselib.utils.JsonUtil;
import com.appbaselib.utils.LogUtils;
import com.appbaselib.utils.ToastUtils;
import com.example.administrator.js.App;
import com.example.administrator.js.UserManager;
import com.example.administrator.js.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class TokenEnableInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        if (!bodyEncoded(response.headers())) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    return response;
                }
            }

            if (!isPlaintext(buffer)) {
                return response;
            }

            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                LogUtils.d(" response.url():" + response.request().url());
                LogUtils.d(" response.body():" + result);
                //得到所需的string，开始判断是否异常
                //***********************do something*****************************
                try {
                    int code = new JSONObject(result).getInt("code");
                    String mS=new JSONObject(result).getString("msg");
                    if (code == 401) {

                        UserManager.getInsatance().logout();
                        Intent intent = new Intent(AppManager.getInstance().getCurrentActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        AppManager.getInstance().startActivity(intent);
                        Looper.prepare();
                        ToastUtils.showShort(App.mInstance,mS);
                        Looper.loop();

                    }
                } catch (JSONException mE) {
                    mE.printStackTrace();
                }
            }

        }


        return response;
    }


    private static final Charset UTF8 = Charset.forName("UTF-8");

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }


}
