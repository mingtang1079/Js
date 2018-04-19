package com.example.core.network;

import com.appbaselib.base.BaseModel;
import com.example.core.model.User;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
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
     * 编辑
     * <p>
     * private String nickname; // 昵称
     * private String openid; // 微信openid
     * private String img; // 头像
     * private String sex; // 值为1男性，值为2时是女性，值为0时是未知
     * private String areacode; // areacode地区id
     * private String deviceid; // 设备id
     * private String longitude; // 经度
     * private String latitude; // 纬度
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/edit")
    Observable<BaseModel<User>> userEdit(@FieldMap Map<String, String> mStringStringMap);

    /**
     * 登录
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/sms/get")
    Observable<BaseModel<String>> getCode(@Field("type") String type,
                                          @Field("mobile") String phone
    );

    @FormUrlEncoded
    @POST(AUTHORIRY + "/upload")
    Observable<BaseModel<String>> upload(@Field("url") String url);

}