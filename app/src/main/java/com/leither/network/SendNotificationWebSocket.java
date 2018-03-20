package com.leither.network;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

public class SendNotificationWebSocket {
    private static SendNotificationWebSocket sendIpWebSocket;
    private WebSocket webSocket;

    private SendNotificationWebSocket() {

    }

    public static SendNotificationWebSocket getDefault() {
        if (sendIpWebSocket == null) {
            sendIpWebSocket = new SendNotificationWebSocket();
        }
        return sendIpWebSocket;
    }

    public void send(String str) {
        if (webSocket != null) {
            webSocket.send(str);
        }
    }

    void onLanBoxIp(String ip) {
        AsyncHttpClient.getDefaultInstance().websocket("ws://" + ip + ":8010/Message",
                null, (ex, webSocket) -> {
                    if (ex != null) {
                        ex.printStackTrace();
                        return;
                    }
                    this.webSocket = webSocket;
                });
    }
}
