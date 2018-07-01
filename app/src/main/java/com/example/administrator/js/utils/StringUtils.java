package com.example.administrator.js.utils;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static List<String> stringToList(String mS) {
        if (TextUtils.isEmpty(mS)) {
            return null;
        } else {
            return Arrays.asList(mS.split(","));
        }
    }

    public static  String listToString(List<String> mStrings)
    {
        StringBuilder mStringBuilder = new StringBuilder();
        for (int i = 0, nsize = mStrings.size(); i < nsize; i++) {
            String value = mStrings.get(i);
            if (i == 0) {
                mStringBuilder.append(value);
            } else {
                mStringBuilder.append("," + value);
            }
        }
        return  mStringBuilder.toString();
    }
}
