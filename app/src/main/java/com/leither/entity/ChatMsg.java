package com.leither.entity;

/**
 * Created by lvqiang on 18-1-3.
 */

public class ChatMsg {
    private String name;
    private String msg;

    public String getName() {
        return name;
    }

    public ChatMsg(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    @Override
    public String toString() {

        return "ChatMsg{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
