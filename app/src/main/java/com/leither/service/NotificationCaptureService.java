package com.leither.service;

import android.annotation.SuppressLint;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.leither.common.Global;
import com.leither.common.ShotApplication;
import com.leither.common.Tools;

import java.util.Objects;

@SuppressLint({"OverrideAbstract", "Registered"})
public class NotificationCaptureService extends NotificationListenerService {
    private static WebSocket ws;

    static {
        AsyncHttpClient.getDefaultInstance().websocket(
                Global.getDefault().getLanBoxIp(),
                null, (ex, webSocket) -> {
                    if (ex != null) {
                        ex.printStackTrace();
                        return;
                    }
                    ws = webSocket;
                });
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if (Objects.equals(sbn.getPackageName(), "com.tencent.mm") && ws != null) {
            ws.send("{ip: "
                    + Tools.getWIFILocalIpAdress(ShotApplication.getContext()) + ","
                    + "mac: " + Tools.getWifiMac(ShotApplication.getContext()) + ","
                    + "model" + Tools.getDeviceName() + "}");
        }
    }
}