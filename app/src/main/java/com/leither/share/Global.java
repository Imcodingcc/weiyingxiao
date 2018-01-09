package com.leither.share;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;

import com.leither.Task.syncTask.RefreshListRunner;
import com.leither.entity.ChatMsg;
import com.leither.entity.MsgContent;
import com.leither.entity.MsgSummary;
import com.leither.operation.RootedAction;
import com.leither.weChatVersion.WeChatResourceId;

import java.util.HashMap;
import java.util.Map;

public class Global {
    @SuppressLint("StaticFieldLeak")
    private static Global global = null;

    private String weChatId = "";
    private Map<String, String> addOneStatus = new HashMap<>();

    public Map<String, String> getAddOneStatus() {
        return addOneStatus;
    }

    public void setAddOneStatus(Map<String, String> addOneStatus) {
        this.addOneStatus = addOneStatus;
    }

    public Map<String, ChatMsg> getChatMsgMap() {
        return chatMsgMap;
    }

    public void setChatMsgMap(Map<String, ChatMsg> chatMsgMap) {
        this.chatMsgMap = chatMsgMap;
    }

    private Map<String, ChatMsg> chatMsgMap =new HashMap<>();

    public Map<String, MsgSummary> getConversationList() {
        return ConversationList;
    }

    public void setConversationList(Map<String, MsgSummary> conversationList) {
        ConversationList = conversationList;
    }

    private Map<String, MsgSummary> ConversationList = new HashMap<>();

    public Map<String, MsgContent> getRecentConversation() {
        return recentConversation;
    }

    public void setRecentConversation(Map<String, MsgContent> recentConversation) {
        this.recentConversation = recentConversation;
    }

    private Map<String, MsgContent> recentConversation = new HashMap<>();

    private Map<String, MsgContent> allConversation = new HashMap<>();

    public Map<String, MsgContent> getAllConversation() {
        return allConversation;
    }

    public void setAllConversation(Map<String, MsgContent> allConversation) {
        this.allConversation = allConversation;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public RootedAction getRootedAction() {
        return rootedAction;
    }

    public void setRootedAction(RootedAction rootedAction) {
        this.rootedAction = rootedAction;
    }

    private RootedAction rootedAction;

    public AccessibilityService getAccessibilityService() {
        return accessibilityService;
    }

    public void setAccessibilityService(AccessibilityService accessibilityService) {
        this.accessibilityService = accessibilityService;
    }

    public WeChatResourceId getWeChatResourceId() {
        return weChatResourceId;
    }

    public void setWeChatResourceId(WeChatResourceId weChatResourceId) {
        this.weChatResourceId = weChatResourceId;
    }

    private WeChatResourceId weChatResourceId;

    private AccessibilityService accessibilityService;

    private ClipboardManager clipboardManager;

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
