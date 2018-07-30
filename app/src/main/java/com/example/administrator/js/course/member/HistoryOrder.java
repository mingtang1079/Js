package com.example.administrator.js.course.member;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangming on 2018/6/25.
 */

public class HistoryOrder implements Serializable {
    /**
     * "no":"001",//教练ID
     * "img":"http://39.104.67.37/rImage.png",//头像
     * "sex":"2",//性别1时是男性，值为2时是女性
     * "nickname":"教练昵称",//教练昵称
     * "updateDate":"2018-06-14 15:39:48",
     * "coursetypeids":"b01",
     * "coursetypenames":"塑形",
     * "csum":7,//总课程数目
     * "cuse":5,//已经上的课程数
     * "isNewRecord":false,
     * "tid":"3",//教练id
     * "uid":"1",//用户id
     * "tryflag":"0",//体验课0否1是
     * "ctype":"b",//课程大类
     * "ctypename":"专业课",//大类名称
     * "id":"2",
     * "createDate":"2018-06-14 15:39:44",
     * "status":"0",
     */

    public String no;

    public String img;

    public Integer age; // 年龄
    public String sex; // 值为1时是男性，值为2时是女性，值为0时是未知

    public String nickname;

    public String updateDate;

    public String coursetypeids;

    public String coursetypenames;

    public int csum;

    public int cuse;

    public boolean isNewRecord;

    public String tid;

    public String uid;

    public String tryflag;

    public String ctype;

    public String ctypename;

    public String id;

    public String createDate;

    public String status;

    public List<OrderList> orderlist;


    public static class OrderList implements Serializable {

        /**
         * "updateDate":"2018-06-14 11:06:31",
         * "cprice":18700,
         * "tid":"3cc8414eb37040f3854b9456b72aa17c",
         * "uid":"1",
         * "tryflag":"0",
         * "cardid":"2",
         * "ctypename":"常规课",
         * "id":"ec7809a2409b989c3399dda890d1",
         * "createDate":"2018-06-14 11:06:31",
         * "rebatedesc":"无优惠",
         * "coursetypeids":"a01,a02",
         * "coursetypenames":"增肌,减脂",
         * "csum":2,//总课程数目
         * "cuse":0,//已经上的课程数
         * "isNewRecord":false,
         * "ctype":"a",
         * "crealprice":37400,
         * "ctotalprice":37400,
         * "status":"b55"
         */

        public String updateDate;
        public int cprice;
        public String tid;
        public String uid;
        public String tryflag;
        public String cardid;
        public String ctypename;
        public String id;
        public String createDate;
        public String rebatedesc;
        public String coursetypeids;
        public String coursetypenames;
        public int csum;
        public int cuse;
        public boolean isNewRecord;
        public String ctype;
        public int crealprice;
        public int ctotalprice;
        public String status;


    }


}
