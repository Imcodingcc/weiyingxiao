package com.leither.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.dylan_wang.capturescreen.R;
import com.leither.share.ShotApplication;
import com.leither.service.AccessService;
import com.leither.service.ScreenshotService;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    private Intent screenIntent = null;
    private Intent accessIntent= null;
    private int REQUEST_MEDIA_PROJECTION = 1;
    private MediaProjectionManager mMediaProjectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MEDIA_PROJECTION && data != null) {
            ((ShotApplication)getApplication()).setResultCode(resultCode);
            ((ShotApplication)getApplication()).setIntent(data);
            ((ShotApplication)getApplication()).setMediaProjectionManager(mMediaProjectionManager);
            screenIntent = new Intent(getApplicationContext(), ScreenshotService.class);
            startService(screenIntent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        stopService();
        super.onDestroy();
    }

    private void init(){
        getMediaProject();
        startService();
    }

    private void startService(){
        accessIntent = new Intent(getApplicationContext(), AccessService.class);
        startService(accessIntent);
        starScreenshot();
    }

    private void stopService(){
        if(accessIntent != null){
            stopService(accessIntent);
        }
        if(screenIntent != null){
            stopService(screenIntent);
        }
    }

    private void getMediaProject(){
        mMediaProjectionManager =
                (MediaProjectionManager)getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    private void starScreenshot(){
        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
    }
}
