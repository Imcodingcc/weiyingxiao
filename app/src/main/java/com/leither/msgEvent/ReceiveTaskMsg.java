package com.leither.msgEvent;

/**
 * Created by lvqiang on 17-12-19.
 */

public class ReceiveTaskMsg {
    public ReceiveTaskMsg(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public String type;
    public String data;
}
