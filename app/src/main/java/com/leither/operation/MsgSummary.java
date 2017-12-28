package com.leither.operation;

/**
 * Created by lvqiang on 17-12-26.
 */

public class MsgSummary {
    private String title;

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "MsgSummary{" +
                "title='" + title + '\'' +
                ", lasTime='" + lasTime + '\'' +
                ", lastMsg='" + lastMsg + '\'' +
                ", newMsgCount='" + newMsgCount + '\'' +
                '}';
    }

    public MsgSummary(String title, String lasTime, String lastMsg, String newMsgCount) {
        this.title = title;
        this.lasTime = lasTime;
        this.lastMsg = lastMsg;
        this.newMsgCount = newMsgCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLasTime() {
        return lasTime;
    }

    public void setLasTime(String lasTime) {
        this.lasTime = lasTime;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getNewMsgCount() {
        return newMsgCount;
    }

    public void setNewMsgCount(String newMsgCount) {
        this.newMsgCount = newMsgCount;
    }

    private String lasTime;
    private String lastMsg;
    private String newMsgCount;
}
