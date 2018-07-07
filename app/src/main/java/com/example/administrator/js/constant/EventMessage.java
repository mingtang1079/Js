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

    public static  class  ListStatusChange extends EventMessage{

    }
}
