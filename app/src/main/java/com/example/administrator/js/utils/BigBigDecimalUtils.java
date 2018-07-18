package com.example.administrator.js.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by tangming on 2018/7/18.
 */

public class BigBigDecimalUtils {

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return a.divide(b, 2, RoundingMode.HALF_UP);

    }


}
