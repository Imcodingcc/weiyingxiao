package com.leither.entity;

import java.util.List;

public class MsgContent {
    private List<String> msg;
    private int isRead;

    public String getLastTime() {
        return lastTime;
    }

    @Override
    public String toString() {
        return "MsgContent{" +
                "msg=" + msg +
                ", isRead=" + isRead +
                ", lastTime='" + lastTime + '\'' +
                '}';
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    private String lastTime;

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public List<String> getMsg() {
        return msg;
    }

    public void setMsg(List<String> msg) {
        this.msg = msg;
    }
}
