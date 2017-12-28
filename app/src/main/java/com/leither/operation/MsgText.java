package com.leither.operation;

public class MsgText {
    private String type;
    private String data;

    public MsgText(String type, String data, int y) {
        this.type = type;
        this.data = data;
        this.y = y;
    }

    public int getY() {
        return y;

    }

    public void setY(int y) {
        this.y = y;
    }

    private int y;

    public String getType() {
        return type;

    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
