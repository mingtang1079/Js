package com.example.administrator.js.me.model;

import java.io.Serializable;

/**
 * Created by tangming on 2018/5/29.
 */

public class BarcodeModel implements Serializable {
    public String type;
    public String id;

    public BarcodeModel(String mType, String mId) {
        type = mType;
        id = mId;
    }
}
