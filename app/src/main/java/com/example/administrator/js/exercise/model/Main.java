package com.example.administrator.js.exercise.model;

import java.io.Serializable;

public class Main implements Serializable {


    /**
     * id : 1
     * isNewRecord : false
     * remarks :
     * createDate : 2018-04-02 14:36:33
     * updateDate : 2018-05-01 06:27:00
     * title : 小新闻1
     * summary : 小新闻1
     * status : 0
     * type : 1
     * copyfrom :
     * image :
     * linkurl : http://www.baidu.com
     * detail : null
     * sort : 1
     */

    public String id;
    public boolean isNewRecord;
    public String remarks;
    public String createDate;
    public String updateDate;
    public String title;
    public String summary;
    public String status;
    public String type;
    public String copyfrom;
    public String image;
    public String linkurl;
    public Object detail;
    public int sort;

    public String userid; // 用户id
    public String nickname; // 用户昵称
    public String userimg; // 用户图片
}
