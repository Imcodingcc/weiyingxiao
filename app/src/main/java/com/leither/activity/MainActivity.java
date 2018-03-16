package com.leither.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.dylan_wang.capturescreen.R;
import com.leither.common.Tools;
import com.leither.operation.RootedAction;
import com.leither.common.Global;
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
        if(!isPermission()) {
            finish();
            return;
        }
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
        Log.d(TAG, "onDestroy: ");
        stopService();
        super.onDestroy();
    }

    private void init(){
        Toast.makeText(this, "请不要操作,直到等待初始化完成", Toast.LENGTH_SHORT).show();
        Global.getDefault().setRootedAction(new RootedAction());
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

    boolean isPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            Toast.makeText(this, "请在android5.0以上的手机运行", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!Tools.isAccessibilitySettingsOn(this)){
            Toast.makeText(this, "请先打开辅助功能, 再重启app", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!Tools.isAppInstalled(this)){
            Toast.makeText(this, "请先安装微信APP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return Tools.isRoot();
    }
}