package com.example.administrator.js.me.model;

public class User {

    public String alipay;
    public String id; //id
    public String realname; // 真实名称
    public String nickname; // 昵称
    public String no; // ID号用于前台显示
    public String openid; // 微信openid
    public Integer editnum; // 昵称修改剩余次数
    public String password; // 密码
    public String comfirmpsd; // 课程确认密码
    public String img; // 头像
    public Integer age; // 年龄
    public String birthdate; // 出生日期
    public String sex; // 值为1时是男性，值为2时是女性，值为0时是未知
    public String email; // email
    public String address; // 地址
    public String areacode; // areacode
    public String areaname; // 地区名称
    public String score; // 评分
    public String degree; // 等级
    public String intro; // 自我描述
    public String role; // 角色0教练1学员
    public String deviceid; // 设备id
    public long beginBirthdate; // 开始 出生日期
    public long endBirthdate; // 结束 出生日期
    public String mobile; // 电话
    public String longitude; // 经度
    public String latitude; // 纬度
    public Double distance;//经纬度刻度//用于查询
    public String token;//token
    public String skillids;    // 专长ids
    public String skillname;    // 专长名称
    public String courseprice;    // 课程价格
    public String reorder;        // 续课率
    public String distancefmt;
    public String coursetypenames;
    public String coursetypeids;
    public String starttime;
    public String privatemode;//隐私0关  1开
    public String remindme;//提醒 0 不提醒1 一小时提醒 2 两小时提薪

    public String provestatus;        // 审核状态0未审核1审核通过2不通过3审核中
    public String teachstatus;  // 审核状态0未审核1审核通过2不通过3审核中
    public String depositstatus; //押金状态 0 未交押金 1 已交押金 2 免押金,3退款中

    public String apitoken;
    public int progress;
    public  String wxnickname;
}
