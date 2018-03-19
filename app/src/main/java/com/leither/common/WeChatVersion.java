package com.leither.common;

public abstract class WeChatVersion {
    public static final String VERSION_6235 = "6.5.23";//微信版本号
    String weChat = "com.tencent.mm:id/";//公共字符串[不变]
    public String weChat_conversation_title;//最近联系人列表名字[微信首页]
    public String weChat_conversation_lastTime;//最近联系人列表时间[微信首页]
    public String weChat_conversation_lastMsg;//最近联系人列表消息[微信首页]
    public String weChat_conversation_msg_count;//最近联系人列表新消息数目[微信首页]
    public String weChat_conversation_content;//聊天框内消息[微信->与xx聊天]
    public String weChat_conversation_time;//聊天框内消息时间[微信->与xx聊天]
    public String weChat_conversation_text;//聊天框内双击消息放大[微信->与xx聊天->双击某条消息]
    public String weChat_account_id;//微信号[我->设置->帐号与安全]
    public String weChat_main_tab;//底栏tab按钮[微信首页]
    public String weChat_search_text_view;//右上角[+]号->添加朋友->输入框
    public String weChat_main_list;//最近联系人列表
    public String weChat_main_list_view;//最近联系人列表
    public String weChat_main_tab_msg_count;//底栏tab按钮上的红点[微信首页]
    public String weChat_title;//微信主页的[微信]title

    public static WeChatVersion getWeChatResourceIds(String version){
        switch (version){
            case VERSION_6235:
                return new Version6523();
        }
        return null;
    }
}