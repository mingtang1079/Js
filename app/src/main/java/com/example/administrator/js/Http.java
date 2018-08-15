package com.example.administrator.js;

import com.appbaselib.network.AbstractRetrofitHelper;
import com.example.administrator.js.interceptor.RongInterceptor;
import com.example.administrator.js.interceptor.TokenEnableInterceptor;
import com.example.administrator.js.interceptor.TokenInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * Created by tangming on 2018/3/15.
 */

public class Http extends AbstractRetrofitHelper<API> {
    @Override
    protected String getHost() {
        return "http://192.168.0.211/";
    }

    @Override
    protected Class getAPI() {
        return API.class;

    }

    public static Http getInstance() {
        if (mInstance == null) {
            mInstance = new Http();
        }
        return (Http) mInstance;
    }

    public static API getDefault() {
        return getInstance().getApi();
    }

    @Override
    protected List<Interceptor> getCustomNetworkInterceptors() {
        List<Interceptor> mInterceptors = new ArrayList<>();
        mInterceptors.add(new TokenEnableInterceptor());
        return mInterceptors;
    }

    @Override
    protected List<Interceptor> getCustomInterceptors() {
        List<Interceptor> mInterceptors = new ArrayList<>();
        mInterceptors.add(new RongInterceptor());
        mInterceptors.add(new TokenInterceptor());
        return mInterceptors;
    }

}
