package com.example.administrator.js;

import com.appbaselib.base.BaseModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by tangming on 2017/5/11.
 */

public interface API {

    String AUTHEN = "spd/rest/authen/";

    String INDEX = "spd/rest/index/";

    String NO_AUTHEN = "spd/rest/";
    String AUTHORIRY="http://39.107.84.49:8080";

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/sport/api/user/login")
    Observable<BaseModel<User>> login(@Field("mobile") String phone, @Field("password") String password);


}
