package com.example.administrator.js.me.model;

import java.io.Serializable;

/**
 * Created by tangming on 2018/6/4.
 */

public class RealUserInfo implements Serializable {

    public String userid;        // 会员id
    public String realname;        // 真实姓名
    public String sex;        // 性别
    public String idnumber;        // 身份证号码
    public String idimgpath;        // 身份证图片
    public String areaid;        // 地区id
    public String areaname;        // 地区名称
    public String reason;        // 状态描述，不通过原因
    public String status;        // 审核状态0未审核1审核通过2不通过3审核中
    public String teachstatus;  // 审核状态0未审核1审核通过2不通过3审核中

}
