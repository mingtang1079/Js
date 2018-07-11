package com.example.administrator.js.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.appbaselib.view.datepicker.view.DateTimeView;
import com.appbaselib.view.datepicker.view.OnDateSelectedListener;
import com.example.administrator.js.R;
import com.example.administrator.js.view.ZeroThirtyTimeView;

/**
 * Created by tangming on 2018/7/11.
 */

public class MyDateUtils {


    /**
     * 获取时间选择dialog （时分秒）
     * @param mContext
     * @param mOnDateSelectedListener
     * @return
     */
    public static AlertDialog.Builder getDefaultDatePickerDialog2(final Context mContext, final OnDateSelectedListener mOnDateSelectedListener) {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        View mView = LayoutInflater.from(mContext).inflate(R.layout.view_zero_thirty_time, null);

        final ZeroThirtyTimeView mDateTimeView = (ZeroThirtyTimeView) mView.findViewById(com.pangu.appbaselibrary.R.id.calendar_time_view);
        mBuilder.setView(mView);
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialogInterface, int mI) {
                mOnDateSelectedListener.onDateSelected(mDateTimeView.getCalendarData());
            }
        });
        mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface mDialogInterface, int mI) {
                mDialogInterface.dismiss();
            }
        });
        return mBuilder;

    }
}
