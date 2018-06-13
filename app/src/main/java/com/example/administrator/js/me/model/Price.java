package com.example.administrator.js.me.model;

import java.io.Serializable;

/**
 * Created by tangming on 2018/6/12.
 */

public class Price implements Serializable {

    public Userprice userprice;
    public PriceRange range;

    public static class PriceRange {
        public int amin;
        public int amax;
        public int bmin;
        public int bmax;
        public int cmin;
        public int cmax;
    }
}
