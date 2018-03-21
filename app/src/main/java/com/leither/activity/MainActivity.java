package com.leither.activity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.leither.R;
import com.leither.common.Global;
import com.leither.common.Tools;
import com.leither.common.ShotApplication;
import com.leither.service.AccessService;
import com.leither.service.NotificationCaptureService;
import com.leither.service.ScreenshotService;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    private Intent screenIntent = null;
    private Intent accessIntent = null;
    private int REQUEST_MEDIA_PROJECTION = 1;
    private MediaProjectionManager mMediaProjectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tools.openAccessibility();
        Tools.openNotificationListener();
        toggleNotificationListenerService();
        if (!isPermission()) return;
        createMediaProjection();
        toggleActivityResult();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MEDIA_PROJECTION && data != null) {
            saveMediaToShotApplication(resultCode, data);
            startService();
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

    private void startService() {
        accessIntent = new Intent(getApplicationContext(), AccessService.class);
        startService(accessIntent);
        screenIntent = new Intent(getApplicationContext(), ScreenshotService.class);
        startService(screenIntent);
    }

    private void saveMediaToShotApplication(int resultCode, Intent data) {
        ((ShotApplication) getApplication()).setResultCode(resultCode);
        ((ShotApplication) getApplication()).setIntent(data);
        ((ShotApplication) getApplication())
                .setMediaProjectionManager(mMediaProjectionManager);
    }

    private void toggleActivityResult() {
        startActivityForResult(mMediaProjectionManager
                        .createScreenCaptureIntent()
                , REQUEST_MEDIA_PROJECTION);
    }

    private void stopService() {
        if (accessIntent != null) {
            stopService(accessIntent);
        }
        if (screenIntent != null) {
            stopService(screenIntent);
        }
    }

    private void createMediaProjection() {
        mMediaProjectionManager = (MediaProjectionManager)
                getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    boolean isPermission() {
        if (!Tools.isWeChatInstalled(this)) {
            Toast.makeText(this, "请先安装微信APP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return Tools.isRoot();
    }

    private void toggleNotificationListenerService() {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(this, NotificationCaptureService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(this, NotificationCaptureService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
}