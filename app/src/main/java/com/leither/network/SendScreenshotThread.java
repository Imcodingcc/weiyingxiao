package com.leither.network;

import android.util.Log;

import com.koushikdutta.async.http.WebSocket;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class SendScreenshotThread extends Thread{

    private WebSocket webSocket;
    private BlockingQueue<Boolean> sendList;
    private BlockingQueue<byte[]> dataList;
    private boolean isRunning = true;
    SendScreenshotThread(WebSocket webSocket, BlockingQueue<Boolean> sendList, BlockingQueue<byte[]> dataList){
        this.webSocket = webSocket;
        this.sendList = sendList;
        this.dataList = dataList;
    }

    @Override
    public void run() {
        super.run();
        while(isRunning) {
            try {
                if (sendList.poll(10L, TimeUnit.SECONDS) != null) {
                    webSocket.send(dataList.take());
                } else {
                   webSocket.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void terminate(){
        isRunning = false;
    }
}
