package com.leither.network;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

public class SendIpWebSocket {
    private static SendIpWebSocket sendIpWebSocket;
    public WebSocket webSocket;

    private SendIpWebSocket() {

    }

    public static SendIpWebSocket getDefault() {
        if (sendIpWebSocket == null) {
            sendIpWebSocket = new SendIpWebSocket();
        }
        return sendIpWebSocket;
    }

    public void send(String str){
        if(webSocket != null){
            webSocket.send(str);
        }
    }

    void onLanBoxIp(String ip) {
        AsyncHttpClient.getDefaultInstance().websocket(ip,
                null, (ex, webSocket) -> {
                    if (ex != null) {
                        ex.printStackTrace();
                        return;
                    }
                    this.webSocket = webSocket;
                });
    }
}
