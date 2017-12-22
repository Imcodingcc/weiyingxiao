package com.leither.common;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.os.Build;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Tools {

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos;
        byte[] bitmapBytes;
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        bitmapBytes = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmapBytes;
    }
}
