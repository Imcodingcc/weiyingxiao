package com.leither.share;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;

public class Global {
    @SuppressLint("StaticFieldLeak")
    private static Global global = null;

    public AccessibilityService getAccessibilityService() {
        return accessibilityService;
    }

    public void setAccessibilityService(AccessibilityService accessibilityService) {
        this.accessibilityService = accessibilityService;
    }

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
