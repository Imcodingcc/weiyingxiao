package com.leither.entity;

public class WeChatIds {
    private String weChatId;

    @Override
    public String toString() {
        return "WeChatIds{" +
                "weChatId='" + weChatId + '\'' +
                ", qq='" + qq + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public WeChatIds(String weChatId, String qq, String phoneNum, String email) {
        this.weChatId = weChatId;
        this.qq = qq;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    private String qq;

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String phoneNum;
    private String email;
}
