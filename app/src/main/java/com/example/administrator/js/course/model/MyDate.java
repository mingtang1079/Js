package com.example.administrator.js.course.model;

import com.example.administrator.js.base.model.WrapperModel;
import com.example.administrator.js.course.CourseModel;

import java.io.Serializable;
import java.util.List;

public class MyDate implements Serializable{

    public int count;
    public List<String> worktimelist;
    public WrapperModel<CourseModel> page;

}
