package com.leither.share;

import android.accessibilityservice.AccessibilityService;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;

public class ShotApplication extends Application {
    private int resultCode;
    private Intent intent;
    private MediaProjectionManager mMediaProjectionManager;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ShotApplication.context = getApplicationContext();
    }

    public AccessibilityService getAccessibilityService() {
        return accessibilityService;
    }

    public void setAccessibilityService(AccessibilityService accessibilityService) {
        this.accessibilityService = accessibilityService;
    }

    private AccessibilityService accessibilityService;

    public int getResultCode(){
        return resultCode;
    }

    public Intent getIntent(){
        return intent;
    }

    public MediaProjectionManager getMediaProjectionManager(){
        return mMediaProjectionManager;
    }

    public void setResultCode(int resultCode){
        this.resultCode = resultCode;
    }

    public void setIntent(Intent intent1){
        this.intent = intent1;
    }

    public void setMediaProjectionManager(MediaProjectionManager mMediaProjectionManager){
        this.mMediaProjectionManager = mMediaProjectionManager;
    }

    public static Context getContext(){
        return context;
    }
}
