package com.example.administrator.js.activity.locaiton;

import java.io.Serializable;

/**
 * Created by tangming on 2018/7/16.
 */

public class PayResult implements Serializable {

    public  String resultStatus;
    public PayResultEntity alipay_trade_app_pay_response;


    private class PayResultEntity implements Serializable {

        public String code;
        public String msg;
        public String total_amount;
    }
}
