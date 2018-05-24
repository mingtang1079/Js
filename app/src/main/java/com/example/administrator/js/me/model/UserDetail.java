package com.example.administrator.js.me.model;

import android.app.Service;

import java.io.Serializable;

/**
 * Created by tangming on 2018/5/23.
 */

public class UserDetail implements Serializable {

    public Need need;//用户需求 ,可能null,注意判断
    public boolean isOrderd;//是否预约课了
    public User userinfo;
    public UserBody bodydata;
    public Relation relation;

    public static class Need implements Serializable {
        public String id;

        public boolean isNewRecord;

        public String remarks;

        public String createDate;

        public String updateDate;

        public String userid;

        public String ctype;

        public String ctypename;

        public String coursetypeids;

        public String coursetypenames;

        public int csum;

        public String areaname;

        public String areacode;

        public String detail;

        public String detailimg;

        public String status;


    }

    public static class UserBody {

        public String id;

        public boolean isNewRecord;

        public String remarks;

        public String createDate;

        public String updateDate;

        public String userid;

        public String height;

        public String weight;

        public String fat;

        public String bmr;

        public String bmi;

        public String visceralfat;

        public String muscle;

        public String bodywater;

        public String wdxiong;

        public String wdyao;

        public String wdxiaotui;

        public String wddatui;

        public String wdxiaobi;

        public String wddabi;

        public String wdtun;

        public String wdjian;

        public String writedate;


    }

    public static class Relation {
        //是否接单tradestatus:0否1是, status:0没有关系,1已关注,2已拉黑
        public String tradestatus;
        public String status;
    }
}
