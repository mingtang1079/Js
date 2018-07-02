package com.example.administrator.js.vipandtrainer.trainer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangming on 2018/7/2.
 */

public  class Workdate implements Serializable{


    /**
     * date : 2018-06-10
     * weekindex : 日
     * timelist : ["09:00-12:00"]
     * month : 06
     * year : 2018
     * day : 10
     */

    public String date;
    public String weekindex;
    public String month;
    public String year;
    public String day;
    public List<String> timelist;
}
