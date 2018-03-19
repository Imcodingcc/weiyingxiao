package com.leither.common;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;

import com.koushikdutta.async.http.WebSocket;
import com.leither.entity.ChatMsg;
import com.leither.entity.MsgContent;
import com.leither.entity.MsgSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.leither.touchlibiary.Nexus5;

@SuppressLint("StaticFieldLeak")
public class Global {
    private static Global global = null;

    private String lanBoxIp;

    private Nexus5 nexus5 = new Nexus5();

    private WeChatVersion weChatVersion;

    private ClipboardManager clipboardManager;

    private AccessibilityService accessibilityService;

    private Map<String, Integer> xy = new HashMap<>();

    private Map<String, ChatMsg> chatMsgMap =new HashMap<>();

    private Map<String, MsgContent> allConversation = new HashMap<>();

    private Map<String, MsgSummary> ConversationList = new HashMap<>();

    private Map<String, MsgContent> recentConversation = new HashMap<>();

    public String getLanBoxIp() {
        return lanBoxIp;
    }

    public void setLanBoxIp(String lanBoxIp) {
        this.lanBoxIp = lanBoxIp;
    }

    public Map<String, Integer> getXy() {
        return xy;
    }

    public Nexus5 getNexus5() {
        return nexus5;
    }

    private List<WebSocket> inputWebSocket = new ArrayList<>();

    public List<WebSocket> getInputWebSocket() {
        return inputWebSocket;
    }

    private Map<String, String> addOneStatus = new HashMap<>();

    public Map<String, String> getAddOneStatus() {
        return addOneStatus;
    }

    public Map<String, ChatMsg> getChatMsgMap() {
        return chatMsgMap;
    }

    public void setChatMsgMap(Map<String, ChatMsg> chatMsgMap) {
        this.chatMsgMap = chatMsgMap;
    }


    public Map<String, MsgSummary> getConversationList() {
        return ConversationList;
    }

    public void setConversationList(Map<String, MsgSummary> conversationList) {
        ConversationList = conversationList;
    }

    public WeChatVersion getWeChatVersion() {
        return weChatVersion;
    }

    public void setWeChatVersion(WeChatVersion weChatVersion) {
        this.weChatVersion = weChatVersion;
    }


    public Map<String, MsgContent> getRecentConversation() {
        return recentConversation;
    }

    public void setRecentConversation(Map<String, MsgContent> recentConversation) {
        this.recentConversation = recentConversation;
    }

    public Map<String, MsgContent> getAllConversation() {
        return allConversation;
    }

    public AccessibilityService getAccessibilityService() {
        return accessibilityService;
    }

    public void setAccessibilityService(AccessibilityService accessibilityService) {
        this.accessibilityService = accessibilityService;
    }

    public ClipboardManager getClipboardManager() {
        return clipboardManager;
    }

    public void setClipboardManager(ClipboardManager clipboardManager) {
        this.clipboardManager = clipboardManager;
    }

    public static Global getDefault(){
        if(global == null){
            global = new Global();
        }
        return global;
    }
}
