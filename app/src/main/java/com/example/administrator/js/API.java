package com.example.administrator.js;

import com.appbaselib.base.BaseModel;
import com.example.administrator.js.activity.WeixinResult;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.course.CourseModel;
import com.example.administrator.js.course.member.HistoryOrder;
import com.example.administrator.js.course.member.Pingjia;
import com.example.administrator.js.course.member.YuyueInfo;
import com.example.administrator.js.course.model.CourseDetail;
import com.example.administrator.js.exercise.model.Main;
import com.example.administrator.js.exercise.model.SmallCourseType;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.member.BodyData;
import com.example.administrator.js.me.member.MyOrder;
import com.example.administrator.js.me.model.NewNeed;
import com.example.administrator.js.me.model.Price;
import com.example.administrator.js.me.model.RealUserInfo;
import com.example.administrator.js.me.model.ServiceTime;
import com.example.administrator.js.me.model.Tongji;
import com.example.administrator.js.me.model.Tuijian;
import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.model.UserDetail;
import com.example.administrator.js.me.model.VerifyUser;
import com.example.administrator.js.me.model.VipSupply;
import com.example.administrator.js.me.model.Zizhi;
import com.example.administrator.js.vipandtrainer.adapter.BigCourse;
import com.example.administrator.js.vipandtrainer.adapter.CourseInfo;
import com.example.administrator.js.vipandtrainer.adapter.CourseType;
import com.example.administrator.js.vipandtrainer.trainer.TrainerDetail;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by tangming on 2017/5/11.
 */

public interface API {


    String AUTHORIRY = BuildConfig.BASE_URL;

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
     * 忘记密码
     *
     * @param phone
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/changemobile")
    Observable<BaseModel<String>> changemobile(
            @Field("mobile") String phone,
            @Field("code") String code,
            @Field("id") String userid);

    /**
     * 修改手机号奥
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/changepsw")
    Observable<BaseModel<String>> alterPassword(
            @Field("mobile") String phone,
            @Field("code") String code,
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

    /**
     * @return
     */
    @Multipart
    @POST(AUTHORIRY + "/upload")
    Observable<BaseModel<String>> uploadImage(@Part() MultipartBody.Part mPart);


    /**
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/saveauth")
    Observable<BaseModel<String>> verifyzizhi(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/getauth")
    Observable<BaseModel<VerifyUser>> getAuth(@Field("userid") String id);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/getRealNameauth")
    Observable<BaseModel<RealUserInfo>> getRealNameInfo(@Field("userid") String id);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/get")
    Observable<BaseModel<User>> getUser(@Field("id") String id);


    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/teachingQualificationSave")
    Observable<BaseModel<Zizhi>> editZizhi(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/news/list")
    Observable<BaseModel<WrapperModel<Main>>> getMain(@Field("userid") String userId, @Field("type") int type, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/contact/search")
    Observable<BaseModel<WrapperModel<VipUser>>> seacrchUser(@FieldMap Map<String, Object> mStringStringMap);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/contact/list")
    Observable<BaseModel<List<VipUser>>> getUser(@FieldMap Map<String, String> mStringStringMap);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/feedback")
    Observable<BaseModel<String>> feed(@FieldMap Map<String, String> mMap);

    //融云api
    @POST
    @FormUrlEncoded
    Observable<JsonObject> getUserRongYunToken(@Url String url, @Field("userid") String id, @Field("name") String name, @Field("portraitUri") String portraitUri);

    @POST(AUTHORIRY + "/media/useraction")
    @FormUrlEncoded
    Observable<BaseModel<String>> collection(@Field("userid") String userId, @Field("id") String id, @Field("action") String isCollection);

    @POST(AUTHORIRY + "/contact/studentDetail")
    @FormUrlEncoded
    Observable<BaseModel<UserDetail>> userDetail(@Field("tid") String jiaolianId, @Field("uid") String useId);

    /**
     * 教练端 课程详细
     *
     * @param jiaolianId
     * @param useId
     * @return
     */
    @POST(AUTHORIRY + "/course/get")
    @FormUrlEncoded
    Observable<BaseModel<CourseDetail>> courseDetail(@Field("tid") String jiaolianId, @Field("id") String useId);

    /**
     * 会员端 课程详细
     *
     * @param userId
     * @param useId
     * @return
     */
    @POST(AUTHORIRY + "/course/get")
    @FormUrlEncoded
    Observable<BaseModel<CourseDetail>> courseDetail2(@Field("uid") String userId, @Field("id") String courseID);

    /**
     * J教练端
     *
     * @param jiaolianId
     * @param status     状态 1进行中,2已结束
     * @param pageNo     页码,默认1,用于已结束的下拉刷新
     * @param starttime  某一天,用于我的日程 格式 2018-02-03
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/course/list")
    Observable<BaseModel<WrapperModel<CourseModel>>> getCourse(@Field("tid") String jiaolianId, @Field("status") String status,
                                                               @Field("pageNo") int pageNo, @Field("starttime") String starttime);


    /**
     * J会员端
     *
     * @param jiaolianId
     * @param status     状态 1进行中,2已结束
     * @param pageNo     页码,默认1,用于已结束的下拉刷新
     * @param starttime  某一天,用于我的日程 格式 2018-02-03
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/course/list")
    Observable<BaseModel<WrapperModel<CourseModel>>> getCourse2(@Field("uid") String jiaolianId, @Field("status") String status,
                                                                @Field("pageNo") int pageNo, @Field("starttime") String starttime);

    /**
     * @param id
     * @param fid
     * @param status 操作 0取消关注,取消拉黑,1关注,2拉黑
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/contact/handle")
    Observable<BaseModel<String>> handleUser(@Field("id") String id, @Field("fid") String fid, @Field("status") String status);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/order/list")
    Observable<BaseModel<WrapperModel<VipSupply>>> vipSupply(@FieldMap Map<String, Object> mStringObjectMap);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/media/favoritepage")
    Observable<BaseModel<WrapperModel<Main>>> getCollection(@Field("userid") String id, @Field("pageNo") int pageNo);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/order/save")
    Observable<BaseModel<String>> passOrRefuse(@Field("id") String id, @Field("tid") String tid, @Field("status") String status);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/course/cancel")
    Observable<BaseModel<String>> cancelCourse(@Field("id") String id, @Field("tid") String tid, @Field("cancelreason") String cancelreason);


    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/invitelist")
    Observable<BaseModel<Tuijian>> getTuijian(@Field("id") String userid);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/count")
    Observable<BaseModel<Tongji>> tongji(@Field("tid") String tid);


    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/servicetimeList")
    Observable<BaseModel<List<ServiceTime>>> servicetimeList(@Field("tid") String tid);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/servicetimeSave")
    Observable<BaseModel<String>> servicetimeSaveAndAlter(@FieldMap Map<String, String> mStringObjectMap);


    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/servicetimeDelete")
    Observable<BaseModel<String>> servicetimeDelete(@Field("id") String tid);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/getprice")
    Observable<BaseModel<Price>> getprice(@Field("id") String id);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/saveprice")
    Observable<BaseModel<String>> saveprice(@FieldMap Map<String, Object> mMap);


    @FormUrlEncoded
    @POST(AUTHORIRY + "/course/cardlist")
    Observable<BaseModel<WrapperModel<HistoryOrder>>> cardlist(@Field("uid") String uid);


    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/getNeed")
    Observable<BaseModel<NewNeed>> getNeed(@Field("userid") String uid);

    @POST(AUTHORIRY + "/course/getcoursetypestags")
    Observable<BaseModel<List<SmallCourseType>>> getSmallcourseType();


    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/publishNeed")
    Observable<BaseModel<NewNeed>> publishNeed(@FieldMap Map<String, Object> mStringObjectMap);


    @POST(AUTHORIRY + "/course/gettags")
    Observable<BaseModel<List<String>>> getPinjiatags();

    @FormUrlEncoded
    @POST(AUTHORIRY + "/contact/teacherDetail")
    Observable<BaseModel<TrainerDetail>> teacherDetail(@Field("tid") String tid, @Field("uid") String uid);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/order/studentsave")
    Observable<BaseModel<String>> applyYuyueke(@FieldMap Map<String, Object> mMap);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/order/getcourseinfo")
    Observable<BaseModel<CourseInfo>> getcourseinfo(@Field("tid") String tid, @Field("cardid") String cardid);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/order/studentsave")
    Observable<BaseModel<String>> studentsave(@Field("tid") String tid, @Field("cardid") String cardid, @Field("uid") String uid, @Field("ctype") String ctype,
                                              @Field("coursetypeids") String coursetypeids, @Field("csum") String csum
            , @Field("address") String address, @Field("clongtitude") String clongtitude, @Field("clatitude") String clatitude);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/course/subscribepage")
    Observable<BaseModel<YuyueInfo>> getYuyuTime(@Field("uid") String uid, @Field("cardid") String cardid);


    @FormUrlEncoded
    @POST(AUTHORIRY + "/course/subscribesave")
    Observable<BaseModel<String>> saveYuekeCourse(@Field("uid") String uid, @Field("cardid") String cardid,
                                                  @Field("startdate") String startdate, @Field("starttime") String starttime
            , @Field("address") String address, @Field("latitude") String latitude, @Field("longtitude") String longtitude);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/order/list")
    Observable<BaseModel<WrapperModel<MyOrder>>> getOrderlist(@Field("uid") String uid, @Field("status") String status, @Field("pageNo") String pageNo);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/order/detail")
    Observable<BaseModel<MyOrder>> getOrderDetail(@Field("uid") String uid, @Field("id") String id);


    @FormUrlEncoded
    @POST(AUTHORIRY + "/order/gettuikereasons")
    Observable<BaseModel<List<String>>> gettuikereasons(@Field("uid") String uid);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/bodydataList")
    Observable<BaseModel<WrapperModel<BodyData>>> getBodyList(@Field("userid") String userid, @Field("pageNo") int pageNo);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/bodydataSave")
    Observable<BaseModel<String>> bodydataSave(@FieldMap Map<String, String> mStringStringMap);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/course/handle")
    Observable<BaseModel<String>> xiake(@Field("uid") String uid, @Field("id") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/course/appraiseSave")
    Observable<BaseModel<Pingjia>> savePingjia(@FieldMap Map<String, Object> mMap);

    /**
     * ordertype	是	string	支付用途 0买课,1押金
     paytype	是	string	支付方式0支付宝,1微信
     * @param uid
     * @param orderid
     * @param paytype
     * @param ordertype
     * @return
     */
    @FormUrlEncoded
    @POST(AUTHORIRY + "/pay/submit")
    Observable<BaseModel<String>> pay(@Field("uid") String uid, @Field("orderid") String orderid, @Field("paytype") String paytype, @Field("ordertype") String ordertype);

    @FormUrlEncoded
    @POST(AUTHORIRY + "/pay/submit")
    Observable<BaseModel<WeixinResult>> payWeixin(@Field("uid") String uid, @Field("orderid") String orderid, @Field("paytype") String paytype, @Field("ordertype") String ordertype);


    @FormUrlEncoded
    @POST(AUTHORIRY + "/user/getUserInfoByWxCode")
    Observable<BaseModel<User>> getUserInfoByWxCode(@Field("user") String code);
}
