package com.example.administrator.js.base.model;

import java.io.Serializable;
import java.util.List;

public class WrapperModel<T> implements Serializable {

    public int pageSize;
    public int pageNo;
    public int count;
    public List<T> list;
}
