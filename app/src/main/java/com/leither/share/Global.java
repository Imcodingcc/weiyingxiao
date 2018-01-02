package com.leither.share;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;

import com.leither.operation.RootedAction;
import com.leither.weChatVersion.WeChatResourceId;

public class Global {
    @SuppressLint("StaticFieldLeak")
    private static Global global = null;

    private String weChatId;

    private String ConversationList = "";

    public String getConversationList() {
        return ConversationList;
    }

    public void setConversationList(String conversationList) {
        ConversationList = conversationList;
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
