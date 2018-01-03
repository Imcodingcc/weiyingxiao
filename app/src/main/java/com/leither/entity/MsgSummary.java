package com.leither.entity;

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
                '}';
    }

    public MsgSummary(String title, String lasTime, String lastMsg) {
        this.title = title;
        this.lasTime = lasTime;
        this.lastMsg = lastMsg;
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

    private String lasTime;
    private String lastMsg;
}
