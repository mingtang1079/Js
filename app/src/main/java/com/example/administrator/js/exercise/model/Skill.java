package com.example.administrator.js.exercise.model;

import com.appbaselib.adapter.ObjectAdapter;

import java.io.Serializable;

public class Skill implements Serializable,ObjectAdapter.Item {

 public    String id;
   public String name;

    public Skill(String mId, String mName) {
        id = mId;
        name = mName;
    }

    @Override
    public String getValue() {
        return name;
    }
}
