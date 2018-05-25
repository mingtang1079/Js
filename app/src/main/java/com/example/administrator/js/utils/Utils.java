package com.example.administrator.js.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import java.util.List;

/**
 * Created by tangming on 2018/5/23.
 */

public class Utils {

    public static void callPhone(final Context activity, final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(phone)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //拨打电话
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + phone);
                intent.setData(data);
                activity.startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static String list2String(List<String> mStrings) {
        String SEPARATOR = ",";
        StringBuilder csvBuilder = new StringBuilder();
        for (String city : mStrings) {
            csvBuilder.append(city);
            csvBuilder.append(SEPARATOR);
        }
        String csv = csvBuilder.toString();
        csv = csv.substring(0, csv.length() - SEPARATOR.length());
        return csv;
    }

}
