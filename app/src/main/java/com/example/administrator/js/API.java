package com.example.administrator.js;

import com.appbaselib.base.BaseModel;
import com.example.administrator.js.activity.SystemMessage;
import com.example.administrator.js.activity.WeixinResult;
import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.course.CourseModel;
import com.example.administrator.js.course.member.HistoryOrder;
import com.example.administrator.js.course.member.Pingjia;
import com.example.administrator.js.course.member.YuyueInfo;
import com.example.administrator.js.course.model.CourseDetail;
import com.example.administrator.js.course.model.MyDate;
import com.example.administrator.js.exercise.model.Main;
import com.example.administrator.js.exercise.model.SmallCourseType;
import com.example.administrator.js.exercise.model.VipUser;
import com.example.administrator.js.me.member.BodyData;
import com.example.administrator.js.me.member.MyOrder;
import com.example.administrator.js.me.model.NewNeed;
import com.example.administrator.js.me.model.Price;
import com.example.administrator.js.me.model.RealUserInfo;
import com.example.administrator.js.me.model.ServiceTime;
import com.example.administrator.js.me.model.ShenheInfo;
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


    //  String AUTHORIRY = BuildConfig.BASE_URL;

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseModel<User>> login(@Field("mobile") String phone, @Field("password") String password);

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("user/reg")
    Observable<BaseModel<User>> register(@Field("zhucetype") String type,
                                         @Field("openid") String openid,
                                         @Field("mobile") String phone,
                                         @Field("code") String code,
                                         @Field("role") String role,
                                         @Field("password") String password,
                                         @Field("nickname") String nickname,
                                         @Field("img") String img,
                                         @Field("sex") String sex

    );

    /**
     * 忘记密码
     *
     * @param phone
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("user/changemobile")
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
    @POST("user/changepsw")
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
    @POST("user/edit")
    Observable<BaseModel<User>> userEdit(@FieldMap Map<String, String> mStringStringMap);

    /**
     * 登录
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("sms/get")
    Observable<BaseModel<String>> getCode(@Field("type") String type,
                                          @Field("mobile") String phone
    );

    /**
     * @return
     */
    @Multipart
    @POST("upload")
    Observable<BaseModel<String>> uploadImage(@Part() MultipartBody.Part mPart);


    /**
     * @return
     */
    @FormUrlEncoded
    @POST("user/saveauth")
    Observable<BaseModel<String>> verifyzizhi(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("user/getauth")
    Observable<BaseModel<VerifyUser>> getAuth(@Field("userid") String id);

    @FormUrlEncoded
    @POST("user/getRealNameauth")
    Observable<BaseModel<RealUserInfo>> getRealNameInfo(@Field("userid") String id);

    @FormUrlEncoded
    @POST("user/get")
    Observable<BaseModel<User>> getUser(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/teachingQualificationSave")
    Observable<BaseModel<Zizhi>> editZizhi(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("news/list")
    Observable<BaseModel<WrapperModel<Main>>> getMain(@Field("userid") String userId, @Field("type") int type, @Field("pageNo") int pageNo, @Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("contact/search")
    Observable<BaseModel<WrapperModel<VipUser>>> seacrchUser(@FieldMap Map<String, Object> mStringStringMap);

    @FormUrlEncoded
    @POST("contact/list")
    Observable<BaseModel<List<VipUser>>> getUser(@FieldMap Map<String, String> mStringStringMap);

    @FormUrlEncoded
    @POST("user/feedback")
    Observable<BaseModel<String>> feed(@FieldMap Map<String, String> mMap);

    //融云api
    @POST
    @FormUrlEncoded
    Observable<JsonObject> getUserRongYunToken(@Url String url, @Field("userid") String id, @Field("name") String name, @Field("portraitUri") String portraitUri);

    @POST("media/useraction")
    @FormUrlEncoded
    Observable<BaseModel<String>> collection(@Field("userid") String userId, @Field("id") String id, @Field("action") String isCollection);

    @POST("contact/studentDetail")
    @FormUrlEncoded
    Observable<BaseModel<UserDetail>> userDetail(@Field("tid") String jiaolianId, @Field("uid") String useId);

    /**
     * 教练端 课程详细
     *
     * @param jiaolianId
     * @param useId
     * @return
     */
    @POST("course/get")
    @FormUrlEncoded
    Observable<BaseModel<CourseDetail>> courseDetail(@Field("tid") String jiaolianId, @Field("id") String useId);

    /**
     * 会员端 课程详细
     *
     * @param userId
     * @return
     */
    @POST("course/get")
    @FormUrlEncoded
    Observable<BaseModel<CourseDetail>> courseDetail2(@Field("uid") String userId, @Field("id") String courseID);

    /**
     * J教练端
     */
    @FormUrlEncoded
    @POST("course/list")
    Observable<BaseModel<WrapperModel<CourseModel>>> getCourse(@FieldMap Map<String, Object> mMap);

    @FormUrlEncoded
    @POST("course/list")
    Observable<BaseModel<WrapperModel<CourseModel>>> getHasCourseList(@Field("uid") String jiaolianId, @Field("status") String status,
                                                                      @Field("pageNo") int pageNo, @Field("orderid") String orderid);

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
    @POST("course/list")
    Observable<BaseModel<WrapperModel<CourseModel>>> getCourse2(@Field("uid") String jiaolianId, @Field("status") String status,
                                                                @Field("pageNo") int pageNo, @Field("starttime") String starttime);

    /**
     * @param id
     * @param fid
     * @param status 操作 0取消关注,取消拉黑,1关注,2拉黑
     * @return
     */
    @FormUrlEncoded
    @POST("contact/handle")
    Observable<BaseModel<String>> handleUser(@Field("id") String id, @Field("fid") String fid, @Field("status") String status);

    @FormUrlEncoded
    @POST("order/list")
    Observable<BaseModel<WrapperModel<VipSupply>>> vipSupply(@FieldMap Map<String, Object> mStringObjectMap);

    @FormUrlEncoded
    @POST("media/favoritepage")
    Observable<BaseModel<WrapperModel<Main>>> getCollection(@Field("userid") String id, @Field("pageNo") int pageNo);

    @FormUrlEncoded
    @POST("order/save")
    Observable<BaseModel<String>> passOrRefuse(@Field("id") String id, @Field("tid") String tid, @Field("status") String status);

    @FormUrlEncoded
    @POST("course/cancel")
    Observable<BaseModel<String>> cancelCourse(@Field("id") String id, @Field("tid") String tid, @Field("cancelreason") String cancelreason);

    //会员端用
    @FormUrlEncoded
    @POST("course/cancel")
    Observable<BaseModel<String>> cancelCourse2(@Field("id") String id, @Field("uid") String tid, @Field("cancelreason") String cancelreason);

    @FormUrlEncoded
    @POST("user/invitelist")
    Observable<BaseModel<Tuijian>> getTuijian(@Field("id") String userid);

    @FormUrlEncoded
    @POST("user/count")
    Observable<BaseModel<List<Tongji>>> tongji(@Field("tid") String tid);


    @FormUrlEncoded
    @POST("user/servicetimeList")
    Observable<BaseModel<List<ServiceTime>>> servicetimeList(@Field("tid") String tid);

    @FormUrlEncoded
    @POST("user/servicetimeSave")
    Observable<BaseModel<String>> servicetimeSaveAndAlter(@FieldMap Map<String, String> mStringObjectMap);


    @FormUrlEncoded
    @POST("user/servicetimeDelete")
    Observable<BaseModel<String>> servicetimeDelete(@Field("id") String tid);

    @FormUrlEncoded
    @POST("user/getprice")
    Observable<BaseModel<Price>> getprice(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/saveprice")
    Observable<BaseModel<String>> saveprice(@FieldMap Map<String, Object> mMap);


    @FormUrlEncoded
    @POST("course/cardlist")
    Observable<BaseModel<WrapperModel<HistoryOrder>>> cardlist(@Field("uid") String uid, @Field("pageNo") String pageNo);


    @FormUrlEncoded
    @POST("user/getNeed")
    Observable<BaseModel<NewNeed>> getNeed(@Field("userid") String uid);

    @POST("course/getcoursetypestags")
    Observable<BaseModel<List<SmallCourseType>>> getSmallcourseType();


    @FormUrlEncoded
    @POST("user/publishNeed")
    Observable<BaseModel<NewNeed>> publishNeed(@FieldMap Map<String, Object> mStringObjectMap);


    @POST("course/gettags")
    Observable<BaseModel<List<String>>> getPinjiatags();

    @FormUrlEncoded
    @POST("contact/teacherDetail")
    Observable<BaseModel<TrainerDetail>> teacherDetail(@Field("tid") String tid, @Field("uid") String uid);

    @FormUrlEncoded
    @POST("order/studentsave")
    Observable<BaseModel<String>> applyYuyueke(@FieldMap Map<String, Object> mMap);

    @FormUrlEncoded
    @POST("order/getcourseinfo")
    Observable<BaseModel<CourseInfo>> getcourseinfo(@Field("tid") String tid, @Field("cardid") String cardid);

    @FormUrlEncoded
    @POST("order/studentsave")
    Observable<BaseModel<String>> studentsave(@Field("tid") String tid, @Field("cardid") String cardid, @Field("uid") String uid, @Field("ctype") String ctype,
                                              @Field("coursetypeids") String coursetypeids, @Field("csum") String csum
            , @Field("address") String address, @Field("clongtitude") String clongtitude, @Field("clatitude") String clatitude);

    @FormUrlEncoded
    @POST("course/subscribepage")
    Observable<BaseModel<YuyueInfo>> getYuyuTime(@Field("uid") String uid, @Field("cardid") String cardid);


    @FormUrlEncoded
    @POST("course/subscribesave")
    Observable<BaseModel<String>> saveYuekeCourse(@Field("uid") String uid, @Field("cardid") String cardid,
                                                  @Field("startdate") String startdate, @Field("starttime") String starttime
            , @Field("address") String address, @Field("latitude") String latitude, @Field("longtitude") String longtitude);

    @FormUrlEncoded
    @POST("order/list")
    Observable<BaseModel<WrapperModel<MyOrder>>> getOrderlist(@Field("uid") String uid, @Field("status") String status, @Field("pageNo") String pageNo);

    @FormUrlEncoded
    @POST("order/detail")
    Observable<BaseModel<MyOrder>> getOrderDetail(@Field("uid") String uid, @Field("id") String id);


    @FormUrlEncoded
    @POST("order/gettuikereasons")
    Observable<BaseModel<List<String>>> gettuikereasons(@Field("uid") String uid);

    @FormUrlEncoded
    @POST("user/bodydataList")
    Observable<BaseModel<WrapperModel<BodyData>>> getBodyList(@Field("userid") String userid, @Field("pageNo") int pageNo);

    @FormUrlEncoded
    @POST("user/bodydataSave")
    Observable<BaseModel<String>> bodydataSave(@FieldMap Map<String, String> mStringStringMap);

    @FormUrlEncoded
    @POST("course/handle")
    Observable<BaseModel<String>> xiake(@Field("uid") String uid, @Field("id") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST("course/appraiseSave")
    Observable<BaseModel<Pingjia>> savePingjia(@FieldMap Map<String, Object> mMap);

    @FormUrlEncoded
    @POST("course/appraise")
    Observable<BaseModel<Pingjia>> getPingjia(@Field("courseid") String courseid, @Field("userid") String userid);

    /**
     * ordertype	是	string	支付用途 0买课,1押金
     * paytype	是	string	支付方式0支付宝,1微信
     *
     * @param uid
     * @param orderid
     * @param paytype
     * @param ordertype
     * @return
     */
    @FormUrlEncoded
    @POST("pay/submit")
    Observable<BaseModel<String>> pay(@Field("userid") String uid, @Field("orderid") String orderid, @Field("paytype") String paytype, @Field("ordertype") String ordertype);

    @FormUrlEncoded
    @POST("pay/submit")
    Observable<BaseModel<WeixinResult>> payWeixin(@Field("userid") String uid, @Field("orderid") String orderid, @Field("paytype") String paytype, @Field("ordertype") String ordertype);


    @FormUrlEncoded
    @POST("user/getUserInfoByWxCode")
    Observable<BaseModel<User>> getUserInfoByWxCode(@FieldMap Map<String, String> mStringStringMap);

    @FormUrlEncoded
    @POST("pay/getDepositMoney")
    Observable<BaseModel<String>> getDepositMoney(@Field("userid") String code);

    @FormUrlEncoded
    @POST("pay/returnDepositMoney")
    Observable<BaseModel<String>> returnDepositMoney(@Field("userid") String code);

    @FormUrlEncoded
    @POST("user/msgList")
    Observable<BaseModel<WrapperModel<SystemMessage>>> msgList(@Field("userid") String code, @Field("pageNo") int pageNo);


    @FormUrlEncoded
    @POST("user/readmsg")
    Observable<BaseModel<String>> readmsg(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/promotionSave")
    Observable<BaseModel<ShenheInfo>> promotionSave(@Field("userid") String id);

    @FormUrlEncoded
    @POST("user/getPromotion")
    Observable<BaseModel<ShenheInfo>> getPromotion(@Field("userid") String id);

    @FormUrlEncoded
    @POST("course/mydate")
    Observable<BaseModel<MyDate>> getMydate(@FieldMap Map<String, String> mStringStringMap);

    @FormUrlEncoded
    @POST("user/get")
    Observable<BaseModel<User>> getUserInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/unbindwechat")
    Observable<BaseModel<String>> unbindwechat(@Field("id") String id);


    @FormUrlEncoded
    @POST("contact/canskip")
    Observable<BaseModel<String>> canskip(@Field("uid") String uid,@Field("tid") String tid);
}
