package com.example.administrator.js.exercise.model;

import java.io.Serializable;

public class SmallCourseType implements Serializable {

    public String name;
    public String id;

    public SmallCourseType(String mName, String mId) {
        name = mName;
        id = mId;
    }
}
