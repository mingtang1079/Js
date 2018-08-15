package com.example.administrator.js.constant;

import java.util.List;

public class EventMessage {

    public Object val;

    public int message;

    public EventMessage() {
    }

    public EventMessage(int message) {
        this.message = message;
    }

    public EventMessage(int message, Object val) {
        this.message = message;
        this.val = val;
    }


    /**
     * int 值  0：已确认
     */
    public static class UsertypeMessage extends EventMessage {
        public int i;

        public UsertypeMessage(int mI) {
            i = mI;
        }
    }

    public static class VerifyChangedMessage extends EventMessage {
    }

    //支付成功
    public static class ListStatusChange extends EventMessage {

    }

    public static class BodyDataListChange extends EventMessage {

    }

    public static class CourseListStatusChange extends EventMessage {
    }

    public static class weixinLogin extends EventMessage {
        public weixinLogin(String mCode) {
            code = mCode;
        }

        public String code;
    }

    public static class closePayActivity {
    }

    public static class shareSuceesState extends EventMessage {

        public shareSuceesState(int mI, Object mO) {
            super(mI, mO);
        }
    }

    // message  0新消息 1 被读取
    public static class NewMessageReceived extends EventMessage {
      public   NewMessageReceived(int mI) {
            message = mI;
        }
    }

    public static class ZizhiStatus extends EventMessage{

    }
}
