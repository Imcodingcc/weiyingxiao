package com.leither.service;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.leither.share.Global;
import com.leither.share.ShotApplication;

public class AccessService extends AccessibilityService{

    void initScripts(){
        ((ShotApplication)getApplication()).setAccessibilityService(this);
        Global.getDefault().setAccessibilityService(this);
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        Global.getDefault().setClipboardManager(clipboardManager);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initScripts();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
