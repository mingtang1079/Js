package com.example.administrator.js.vipandtrainer.adapter;

import java.io.Serializable;
import java.util.List;

public class BigCourse implements Serializable, CourseType {


    /**
     * {
     * "updateDate":"2018-06-11 18:33:38",
     * "isNewRecord":false,
     * "list":[//课程小类列表
     * {
     * "updateDate":"2018-05-02 23:01:00",
     * "name":"竞技健美",//课程小类名称
     * "id":"c01",//课程小类id
     * "isNewRecord":false,
     * "type":"c",
     * "createDate":"2018-05-02 23:00:36",
     * "status":"1"
     * }
     * ],
     * "onsalelabel":"满100减10,满200减30", //优惠说明
     * "onsaletype":"2", //优惠类型 0无优惠,1打折优惠,2满减优惠
     * "onsaledata":"200-30,100-10",//计算规则
     * "onsaledataforapp":[//app计算规则
     * {
     * "total":200,
     * "money":30
     * },
     * {
     * "total":100,
     * "money":10
     * }
     * ],
     * "price":"300",//课程价格(元)
     * "name":"特色课"//课程大类名称
     * "id":"c",//课程大类id
     * "createDate":"2018-06-11 18:33:30",
     * "status":"1"
     * }
     */

    public String updateDate;
    public boolean isNewRecord;
    public String onsalelabel;
    public String onsaletype;
    public String onsaledata;
    public String price;
    public String name;
    public String id;
    public String createDate;
    public String status;
    public List<OnsaledataforappBean> onsaledataforapp;
    public List<SmallCourse> list;

    @Override
    public String getName() {
        return name;
    }

    public static class OnsaledataforappBean implements Serializable{
        /**
         * total : 200
         * money : 30
         */

        public int total;
        public int money;
    }

    public static class SmallCourse implements Serializable, CourseType {


        /**
         * updateDate : 2018-05-02 23:01:00
         * name : 竞技健美
         * id : c01
         * isNewRecord : false
         * type : c
         * createDate : 2018-05-02 23:00:36
         * status : 1
         */

        public String updateDate;
        public String name;
        public String id;
        public boolean isNewRecord;
        public String type;
        public String createDate;
        public String status;

        @Override
        public String getName() {
            return name;
        }
    }
}
