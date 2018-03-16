package com.leither.service;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.leither.network.ServerCreator;
import com.leither.common.Global;
import com.leither.common.ShotApplication;
import com.leither.weChatVersion.WeChatResourceId;

public class AccessService extends AccessibilityService{

    private static final String TAG = AccessService.class.getName();

    void initScripts() throws Exception{
        ((ShotApplication)getApplication()).setAccessibilityService(this);
        Global.getDefault().setAccessibilityService(this);
        Global.getDefault().setWeChatResourceId(new WeChatResourceId("6.5.23"));
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        Global.getDefault().setClipboardManager(clipboardManager);
        ServerCreator.getDefault().setHttpListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        try {
            initScripts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //Log.d("AccessibilityEvent", String.valueOf(event.getEventType()));
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
