package com.leither.network;

import android.net.Uri;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.body.JSONObjectBody;

import org.json.JSONObject;

public class SendInfoToServer {
    private static SendInfoToServer sendInfoToServer;
    private WebSocket webSocket;
    private String ip;

    private SendInfoToServer() {

    }

    public static SendInfoToServer getDefault() {
        if (sendInfoToServer == null) {
            sendInfoToServer = new SendInfoToServer();
        }
        return sendInfoToServer;
    }

    public void send(String content) {
        if (webSocket != null) {
            webSocket.send(content);
        }
    }

    public void execute(String opt, JSONObject jsonObject){
        if(ip != null){
            AsyncHttpRequest request =
                    new AsyncHttpRequest(Uri.parse("http://" + ip+ ":5758/" + opt), "POST");
            request.setBody(new JSONObjectBody(jsonObject));
            AsyncHttpClient.getDefaultInstance()
                    .execute(request, (ex, response) -> {
                        if (ex != null) ex.printStackTrace();
                    });
        }
    }

    void onLanBoxIp(String ip) {
        this.ip = ip;
        AsyncHttpClient.getDefaultInstance().websocket("ws://" + ip + ":8010/message",
                null, (ex, webSocket) -> {
                    if (ex != null) {
                        ex.printStackTrace();
                        return;
                    }
                    webSocket.setClosedCallback(ex1 -> this.webSocket = null);
                    this.webSocket = webSocket;
                });
    }
}