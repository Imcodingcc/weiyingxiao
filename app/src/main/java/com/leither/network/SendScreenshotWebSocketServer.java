package com.leither.network;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class SendScreenshotWebSocketServer implements Server{
    private BlockingQueue<byte[]> dataList;
    private Map<String, BlockingQueue<Boolean>> sendMap = new HashMap<>();

    SendScreenshotWebSocketServer(BlockingQueue<byte[]> dataList, AsyncHttpServer asyncHttpServer){
        this.dataList = dataList;
        setListener(asyncHttpServer);
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        server.websocket("/live", (webSocket, request) -> {
            BlockingQueue<Boolean> sendList = new LinkedBlockingDeque<>(3);
            sendList.offer(true);
            sendList.offer(true);
            sendMap.put(webSocket.toString(), sendList);
            new SendScreenshotThread(webSocket, sendList, dataList).start();
            Log.d("thread", "setListener: " + 1);

            webSocket.setClosedCallback(ex -> {
                    if (ex != null) Log.e("WebSocket", "Error");
            });

            webSocket.setStringCallback(s -> sendMap.get(webSocket.toString()).offer(true));
        });
    }
}