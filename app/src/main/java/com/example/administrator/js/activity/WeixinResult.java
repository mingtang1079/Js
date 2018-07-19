package com.example.administrator.js.activity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WeixinResult implements Serializable{


    /**
     * package : Sign=WXPay
     * appid : wx2421b1c4370ec43b
     * sign : 859F637F0981050AEE3BD334878E0D51
     * partnerid : 10000100
     * prepayid : WX1217752501201407033233368018
     * noncestr : 4c4194cd8c964bc3ae9c005d754575c5
     * timestamp : 1531582592
     */

    public String packageX;
    public String appid;
    public String sign;
    public String partnerid;
    public String prepayid;
    public String noncestr;
    public String timestamp;
}
