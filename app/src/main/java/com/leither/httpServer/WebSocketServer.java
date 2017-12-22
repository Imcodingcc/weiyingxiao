package com.leither.httpServer;

import android.util.Log;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class WebSocketServer implements Server{
    private BlockingQueue<byte[]> dataList;
    private Map<String, BlockingQueue<Boolean>> sendMap = new HashMap<>();

    WebSocketServer(BlockingQueue<byte[]> dataList){
        this.dataList = dataList;
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        server.websocket("/live", new AsyncHttpServer.WebSocketRequestCallback() {
            @Override
            public void onConnected(final WebSocket webSocket, AsyncHttpServerRequest request) {
                BlockingQueue<Boolean> sendList = new LinkedBlockingDeque<>(3);
                sendList.offer(true);
                sendList.offer(true);
                sendMap.put(webSocket.toString(), sendList);
                new SendJpgThread(webSocket, sendList, dataList).start();

                webSocket.setClosedCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                            if (ex != null) Log.e("WebSocket", "Error");
                    }
                });

                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String s) {
                        sendMap.get(webSocket.toString()).offer(true);
                    }
                });
            }
        });
    }
}
