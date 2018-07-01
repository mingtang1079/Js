package com.example.administrator.js.activity.locaiton;

import com.example.administrator.js.exercise.model.SmallCourseType;
import com.example.administrator.js.me.model.NewNeed;

import java.io.Serializable;
import java.util.List;

public class NewNeedAndType implements Serializable {

  public   NewNeed mNewNeed;
 public    List<SmallCourseType> mSmallCourseTypes;

    public NewNeedAndType(NewNeed mNewNeed, List<SmallCourseType> mSmallCourseTypes) {
        this.mNewNeed = mNewNeed;
        this.mSmallCourseTypes = mSmallCourseTypes;
    }
}
