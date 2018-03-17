package com.leither.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.leither.R;
import com.leither.common.Tools;
import com.leither.common.ShotApplication;
import com.leither.service.AccessService;
import com.leither.service.ScreenshotService;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    private Intent screenIntent = null;
    private Intent accessIntent= null;
    private int REQUEST_MEDIA_PROJECTION = 1;
    private MediaProjectionManager mMediaProjectionManager;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        if(!isPermission()) {
            finish();
            return;
        }
        Tools.openAccessibility();
        getMediaProject();
        startService();
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
        Log.d(TAG, "onDestroy: ");
        stopService();
        super.onDestroy();
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

    boolean isPermission(){
        if(!Tools.isWeChatInstalled(this)){
            Toast.makeText(this, "请先安装微信APP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return Tools.isRoot();
    }
}