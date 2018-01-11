package com.leither.Task.syncTask;

import android.annotation.SuppressLint;
import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.operation.BasicAction;
import com.leither.scripts.syncScripts.SyncScript;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@SuppressLint("UseSparseArrays")
public class SyncTaskRunner extends Thread{
    private BlockingQueue<SyncScript> queue = new LinkedBlockingDeque<>(60);

    @Override
    public void run() {
        while(true) {
            SyncScript syncScript;
            try {
                syncScript = queue.take();
                syncScript.onComplete(syncScript.exec());
            } catch (Exception e) {
                e.printStackTrace();
                queue = new LinkedBlockingDeque<>(60);
                try {
                    BasicAction.reOpenWeChat();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public synchronized void addScript(SyncScript syncScript){
        queue.offer(syncScript);
    }
}