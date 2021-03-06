package com.example.administrator.js.course.member;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangming on 2018/7/4.
 */

public  class WeekTimeList implements Serializable{


    /**
     //此处timelist为某一天的时间块数据
     "zone": "0",
     "time": "09:00-09:30",  //本时间块时间,可作为id
     "brothertime": "09:30-10:00",//联动的时间块
     "date": "2018-06-25",
     "year": "2018",
     "month": "06",
     "day": "25",
     "starttime": "09:00",
     "endtime": "09:30",
     "weekindex": "一",//星期
     "status": "0",//可选状态,0表示可选,1表示禁止选择
     "remark": "可选"
     */

    public String date;
    public String weekindex;
    public List<DayTimeList> timelist;
    public String month;
    public String year;
    public String day;
}
