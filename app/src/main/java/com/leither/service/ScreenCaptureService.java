package com.leither.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.leither.common.DeEncodecCommon;
import com.leither.httpServer.SocketCreator;
import com.leither.msgEvent.ScreenMsg;
import com.leither.share.ShotApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScreenCaptureService extends Service
{

    private int windowWidth = 0;
    private int windowHeight = 0;
    private int mScreenDensity = 60;
    public static int mResultCode = 0;
    private Thread captureThread = null;
    private MediaCodec encoder = null;
    public volatile boolean haveConnected = false;
    public static Intent mIntent = null;
    private BufferedOutputStream outputStream;
    private Surface encoderInputSurface = null;
    private VirtualDisplay mVirtualDisplay = null;
    private MediaProjection mMediaProjection = null;
    SocketCreator socketCreator = SocketCreator.getDefault(null);
    public static MediaProjectionManager mediaProjectionManager = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
        createSurface();
        startOnUIThread();
    }

    private void createSurface(){
        EventBus.getDefault().register(this);
        WindowManager windowManager = (WindowManager) getApplication().getSystemService(WINDOW_SERVICE);
        assert windowManager != null;
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        windowWidth = size.x;
        windowHeight = size.y;
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        encoder = DeEncodecCommon.getMediaCodec();
        encoderInputSurface = encoder.createInputSurface();
    }

    public void createMediaProject(){
        mediaProjectionManager = ((ShotApplication)getApplication()).getMediaProjectionManager();
        mIntent = ((ShotApplication)getApplication()).getIntent();
        mResultCode = ((ShotApplication)getApplication()).getResultCode();
        mediaProjectionManager = ((ShotApplication)getApplication()).getMediaProjectionManager();
        mMediaProjection = mediaProjectionManager.getMediaProjection(mResultCode, mIntent);
    }

    private void createVirtualDisplay(){
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
                windowWidth, windowHeight, mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                encoderInputSurface, null, null);
    }

    public void startVirtualDisplay(){
        createMediaProject();
        createVirtualDisplay();
        encoder.start();
        File f = new File(Environment.getExternalStorageDirectory(), "Download/video_encoded.264");
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startOnUIThread(){
        startVirtualDisplay();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void capture(){
        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
        int status = encoder.dequeueOutputBuffer(info, 11000);
        if(status >= 0){
            ByteBuffer encodedData = encoder.getOutputBuffer(status);
            byte[] outData = new byte[info.size];
            encodedData.get(outData);
            try {
                outputStream.write(outData, 0, outData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            encoder.releaseOutputBuffer(status, false);
        }
    }

    @Subscribe
    public void onSocketChange(ScreenMsg msg){
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void stopAndClear() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        mVirtualDisplay = null;
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy()
    {
        stopAndClear();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}