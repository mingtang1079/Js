package com.example.administrator.js.me.member;

import java.io.Serializable;

/**
 * Created by tangming on 2018/6/26.
 */

public class MyOrder implements Serializable {
    /**
     * {
     * "id": "1",
     * "isNewRecord": false,
     * "remarks": null,
     * "createDate": null,
     * "updateDate": null,
     * "tid": "1",
     * "uid": "2", //会员id
     * "ctype": "d",
     * "ctypename": "体验课", //课程名称
     * "coursetypeids": null,
     * "coursetypenames": null,
     * "tryflag": "1", //0私教课1体验课
     * "csum": 10, //课程数量
     * "cuse": 0,
     * "cprice": 100,
     * "ctotalprice": 1000,
     * "crealprice": null,
     * "rebate": null,
     * "rebatedesc": null,
     * "status": "a1",//a1已提交b2通过b3完成付款,b4不通过,b55退款中,b56退款成功
     * "repeatstatus": null,
     * "paytype": null,
     * "payno": null,
     * "nickname": "学生", //会员昵称
     * "longitude": "104.201165",
     * "latitude": "30.738908",
     * "img": "headimgurl", //头像地址
     * "age": 22, //年龄
     * "sex": "0", //性别值为1时是男性，值为2时是女性，值为0时是未知
     * "distance": "18km" //距离
     * }
     */
    public String id;        // id
    public String tid;        // 教练id
    public String uid;        // 学员id
    public String ctype;        // 课程大类
    public String ctypename;        // 课程大类名称
    public String coursetypeids;        // 课程小类id集合
    public String coursetypenames;        // 课程小类名称集合
    public String tryflag;        // 是否为体验课0否1是
    public Integer csum;        // 数量
    public Integer cuse;        // 已上课时
    public Integer cprice;        // 课程单价
    public Integer ctotalprice;        // 总价
    public Integer crealprice;        // 实际付款
    public String rebate;        // 折扣(小于1大于0)
    public String rebatedesc;        // 折扣描述
    public String status;        // 订单状态a1提交b2通过b3完成付款,b4不通过
    public String repeatstatus;// 续单状态
    public String paytype;        // 支付方式0支付宝1微信
    public String payno;        // 支付单号
    public String nickname;//会员昵称
    public String longitude; // 经度
    public String latitude; // 纬度
    public String img;//头像
    public Integer age;//年龄
    public String sex;//值为1时是男性，值为2时是女性，值为0时是未知
    public String distance;//距离

    public String no;
    public String address;
    public String createDate;
    public String paydate;

    public String tuikePrice;//b本地的
    public String refundtype;
    public String refunddetail;
    public String tuikeTime;
}
