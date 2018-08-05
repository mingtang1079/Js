package com.example.administrator.js.broadcastReceiver;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.js.activity.MainActivity;
import com.example.administrator.js.activity.MessageActivity;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class MyPushMessageReceiver extends PushMessageReceiver {

    @Override
    public boolean onNotificationMessageArrived(Context mContext, PushNotificationMessage mPushNotificationMessage) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context mContext, PushNotificationMessage mPushNotificationMessage) {

        Intent mIntent1=new Intent(mContext, MainActivity.class);
        mIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntent1);

        Intent mIntent=new Intent(mContext, MessageActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntent);
        return true;
    }
}
