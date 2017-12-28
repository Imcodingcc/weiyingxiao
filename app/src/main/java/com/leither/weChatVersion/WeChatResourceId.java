package com.leither.weChatVersion;

import android.util.Log;

public class WeChatResourceId {

    public String version;
    private String weChat = "com.tencent.mm:id/";
    public String weChat_conversation_title;
    public String weChat_conversation_lastTime;
    public String weChat_conversation_lastMsg;
    public String weChat_conversation_msg_count;
    public String weChat_conversation_content;
    public String weChat_conversation_time;
    public String weChat_conversation_text;
    public WeChatResourceId(String version){
       this.version = version;
       version();
    }

    private void version(){
        switch (version){
            case "6.5.23":
                weChat_conversation_title = weChat + "aoj";
                weChat_conversation_lastTime = weChat + "aok";
                weChat_conversation_lastMsg = weChat + "aol";
                weChat_conversation_msg_count = weChat + "io";
                weChat_conversation_content = weChat + "j1";
                weChat_conversation_time = weChat + "a2";
                weChat_conversation_text= weChat + "af6";
                break;
            default:
                Log.d("WeChatResourceId", "unknown weChat version");
        }

    }
}
