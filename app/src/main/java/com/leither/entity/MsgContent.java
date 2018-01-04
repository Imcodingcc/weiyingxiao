package com.leither.entity;

import java.util.List;

public class MsgContent {
    private List<ChatMsg> msg;

    public String getLastTime() {
        return lastTime;
    }

    public List<ChatMsg> getMsg() {
        return msg;
    }

    public void setMsg(List<ChatMsg> msg) {
        this.msg = msg;
    }

    @Override

    public String toString() {
        return "MsgContent{" +
                "msg=" + msg +
                ", lastTime='" + lastTime + '\'' +
                '}';
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    private String lastTime;
}
