package com.example.administrator.js;

import com.appbaselib.base.BaseModel;
import com.example.administrator.js.me.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by tangming on 2017/5/11.
 */

public interface API {

    String AUTHORIRY = "https://www.bjwork.xyz/api";

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/login")
    Observable<BaseModel<User>> login(@Field("mobile") String phone, @Field("password") String password);

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/reg")
    Observable<BaseModel<User>> register(@Field("regtype") String type,
                                         @Field("mobile") String phone,
                                         @Field("code") String code,
                                         @Field("role") String role,
                                         @Field("password") String password);

    /**
     * 登录
     *
     * @param phone
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/sms/get")
    Observable<BaseModel<String>> getCode(@Field("type") String type,
                                        @Field("mobile") String phone
    );


}
