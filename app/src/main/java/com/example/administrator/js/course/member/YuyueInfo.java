package com.example.administrator.js.course.member;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangming on 2018/7/4.
 */

public class YuyueInfo implements Serializable {


    /**
     * "tid": "1",
     * "uid": "1",
     * "status": null,
     * "ctype": "a",
     * "ctypename": "常规课",
     * "coursetypeids": "a01,a02",
     * "coursetypenames": "增肌,减脂",
     * "starttime": null,
     * "endtime": null,
     * "clickstarttime": null,
     * "clickendtime": null,
     * "gymname": null,
     * "address": "中和镇",//上课地址
     * "longtitude": "120.3123",//经度
     * "latitude": "30.1234",//纬度
     * "cancelreason": null,
     * "tryflag": null,
     * "beginStarttime": null,
     * "endStarttime": null,
     * "beginEndtime": null,
     * "endEndtime": null,
     * "nickname": null,//昵称
     * "sex": null,//性别 1男2女
     * "no": null,//教练ID
     * "img": null,//头像
     */
    public String id;

    public boolean isNewRecord;

    public String remarks;

    public String createDate;

    public String updateDate;

    public String cardid;

    public String orderid;

    public String tid;

    public String uid;

    public String status;

    public String ctype;

    public String ctypename;

    public String coursetypeids;

    public String coursetypenames;

    public String starttime;

    public String endtime;

    public String clickstarttime;

    public String clickendtime;

    public String gymname;

    public String address;

    public String longtitude;

    public String latitude;

    public String cancelreason;

    public String tryflag;

    public String beginStarttime;

    public String endStarttime;

    public String beginEndtime;

    public String endEndtime;

    public String nickname;

    public String sex;

    public String no;

    public String img;

    public String mobile;

    public List<WeekTimeList> timelist;

    public String age;
}
