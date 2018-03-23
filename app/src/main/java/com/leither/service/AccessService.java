package com.leither.service;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.leither.common.WeChatVersion;
import com.leither.network.ServerCreator;
import com.leither.common.Global;

public class AccessService extends AccessibilityService{

    private static final String TAG = AccessService.class.getName();

    void initScripts(){
        Global.getDefault().setAccessibilityService(this);
        Global.getDefault().setWeChatVersion(WeChatVersion.getWeChatResourceIds(WeChatVersion.VERSION_6235));
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        Global.getDefault().setClipboardManager(clipboardManager);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
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
