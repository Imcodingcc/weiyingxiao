package com.leither.network;

import android.net.Uri;
import android.util.Log;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.leither.common.Tools;
import com.leither.common.ShotApplication;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

class SendIpTimerRunner {

    SendIpTimerRunner() {
        Log.d("sendIpTimer", "SendIpTimerRunner: ");
        String ip = discoverLanBoxIp();
        sendIpToNotification(ip);
        if (ip != null) createSendRunner(ip);
    }

    private void createSendRunner(final String lanBoxIp) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    sendPhoneInfo(lanBoxIp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }


    private String discoverLanBoxIp() {
        String[] hostName;
        hostName = Tools
                .getLocalHostLANAddress()
                .getHostName()
                .split("\\.");
        for (int i = 0; i < 255; i++) {
            String isReachableIp = hostName[0]
                    + "." +
                    hostName[1]
                    + "." +
                    hostName[2] + "." + i;
            if (Tools.isReachableByTcp(isReachableIp, 5758, 10)) {
                return isReachableIp;
            }
        }
        return null;
    }

    private void sendPhoneInfo(final String lanBoxIp) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip", Tools.getLocalHostLANAddress().getHostName())
                .put("mac", Tools.getWifiMac())
                .put("model", Tools.getDeviceName());
        SendInfoToServer.getDefault().execute("ipaddr", jsonObject);
    }

    private void sendIpToNotification(String ip) {
        if (ip != null) {
            SendInfoToServer.getDefault().onLanBoxIp(ip);
        }
    }
}