package com.example.administrator.js.me.model;

import java.io.Serializable;

public class ShenheInfo implements Serializable {


    /**
     * id : 70eaaa82021d4538beab8dd6aeb92789
     * isNewRecord : false
     * remarks : null
     * createDate : 2018-05-03 12:44:09
     * updateDate : 2018-05-03 12:44:09
     * userid : 3cc8414eb37040f3854b9456b72aa17c
     * promotionstatus : 0
     * promotiontype : null
     */

    public String id;
    public boolean isNewRecord;
    public String remarks;
    public String createDate;
    public String updateDate;
    public String userid;
    public String promotionstatus;  //晋升状态0.已提交1,审核通过,2,审核不通过
    public String promotiontype;
}
