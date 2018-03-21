package com.leither.network;

import android.net.Uri;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.body.JSONObjectBody;

import org.json.JSONObject;

public class SendInfoToServer {
    private static SendInfoToServer sendInfoToServer;
    private String ip = "0.0.0.0";

    private SendInfoToServer() {

    }

    public static SendInfoToServer getDefault() {
        if (sendInfoToServer == null) {
            sendInfoToServer = new SendInfoToServer();
        }
        return sendInfoToServer;
    }

    public void execute(String opt, JSONObject jsonObject) {
        AsyncHttpRequest request =
                new AsyncHttpRequest(Uri.parse("http://" + ip + ":5758/" + opt), "POST");
        request.setBody(new JSONObjectBody(jsonObject));
        AsyncHttpClient.getDefaultInstance()
                .execute(request, (ex, response) -> {
                    if (ex != null) ex.printStackTrace();
                });
    }

    void onLanBoxIp(String ip) {
        this.ip = ip;
    }

}