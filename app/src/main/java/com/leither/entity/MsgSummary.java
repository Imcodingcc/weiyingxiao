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
                ", lastTime='" + lastTime + '\'' +
                ", lastMsg='" + lastMsg + '\'' +
                '}';
    }

    public MsgSummary(String title, String lastTime, String lastMsg) {
        this.title = title;
        this.lastTime = lastTime;
        this.lastMsg = lastMsg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    private String lastTime;
    private String lastMsg;
}
