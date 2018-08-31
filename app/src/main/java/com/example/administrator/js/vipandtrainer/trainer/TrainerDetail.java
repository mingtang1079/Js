package com.example.administrator.js.vipandtrainer.trainer;

import com.example.administrator.js.me.model.User;
import com.example.administrator.js.me.model.UserDetail;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangming on 2018/7/2.
 */

public class TrainerDetail implements Serializable {

    public User userinfo;
    public UserDetail.Relation relation;
    public List<Workdate> workdatelist;
    public int code;
    public Moreuserinfo moreuserinfo;

    public class Moreuserinfo {

        public String workdate;
        public  String intro;
    }
}
