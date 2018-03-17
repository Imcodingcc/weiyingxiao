package com.leither.network;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.leither.common.Global;
import com.leither.common.Tools;
import com.leither.common.ShotApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class SendIpTimerRunner {
    SendIpTimerRunner() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    upIpToGlobal();
                    sendPhoneInfo();
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

    private void sendPhoneInfo() throws Exception {
        AsyncHttpClient.getDefaultInstance()
                .execute("http://" + Global.getDefault().getLanBoxIp() + ":5758"
                        + "/ipaddr?ip=" + Tools.getLocalHostLANAddress().getHostName()
                        + "&mac=" + Tools.getWifiMac(ShotApplication.getContext())
                        + "&model=" + Tools.getDeviceName(), null).get().message();
    }

    private void upIpToGlobal() throws Exception {
        Global.getDefault().setLanBoxIp(discoverLanBoxIp(discoverLanIps()));
    }
}