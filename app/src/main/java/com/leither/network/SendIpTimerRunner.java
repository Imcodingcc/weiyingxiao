package com.leither.network;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.leither.common.Tools;
import com.leither.common.ShotApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SendIpTimerRunner {
    SendIpTimerRunner() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sendIp();
            }
        }, 2 * 60 * 1000);
    }


    private List<String> discoverIp() throws Exception {
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

    private String discoverReachablePortIp(List<String> list) {
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

    private void sendIp() {
        try {
            AsyncHttpClient.getDefaultInstance()
                    .execute("http://" + discoverReachablePortIp(discoverIp()) + ":5758"
                            + "/ipaddr?ip=" + Tools.getLocalHostLANAddress().getHostName()
                            + "&mac=" + Tools.getWifiMac(ShotApplication.getContext())
                            + "&model=" + Tools.getDeviceName(), null).get().message();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}