package com.leither.network;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.leither.common.Global;
import com.leither.common.Tools;
import com.leither.common.ShotApplication;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class SendIpTimerRunner {

    SendIpTimerRunner() {
        String ip = findLanBoxIp();
        sendIpToNotification(ip);
        if(ip != null) createSendRunner(ip);
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
        }, 2 * 60 * 1000);
    }


    private List<String> discoverLanIps() throws Exception {
        String[] hostName = Tools
                .getLocalHostLANAddress()
                .getHostName()
                .split("\\.");
        List<String> reachableIp = new ArrayList<>();
        for (int i = 0; i < 255; i++) {
            String isReachableIp = hostName[0]
                    + "." +
                    hostName[1]
                    + "." +
                    hostName[2] + "." + i;
            if (Tools.isHostReachable(isReachableIp, 20)) {
                reachableIp.add(isReachableIp);
            }
        }
        return reachableIp;
    }

    private String discoverLanBoxIp(List<String> list) {
        if (list == null) {
            return null;
        }
        for (String s : list) {
            if (Tools.isHostConnectAble(s, 5758)) {
                return s;
            }
        }
        return null;
    }

    private void sendPhoneInfo(final String lanBoxIp) throws Exception {
        AsyncHttpClient.getDefaultInstance()
                .execute("http://" + lanBoxIp + ":5758"
                        + "/ipaddr?ip=" + Tools.getLocalHostLANAddress().getHostName()
                        + "&mac=" + Tools.getWifiMac(ShotApplication.getContext())
                        + "&model=" + Tools.getDeviceName(), null).get().message();
    }

    private String findLanBoxIp() {
        try {
            return discoverLanBoxIp(discoverLanIps());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendIpToNotification(String ip) {
        if (ip != null) {
            SendIpWebSocket.getDefault().onLanBoxIp(ip);
        }
    }
}