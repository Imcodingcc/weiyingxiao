package com.leither.Task;

import android.annotation.SuppressLint;

import com.leither.scripts.AsyncScript;
import com.leither.scripts.SyncScript;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@SuppressLint("UseSparseArrays")
public class SyncTaskRunner extends Thread{
    private BlockingQueue<SyncScript> queue = new LinkedBlockingDeque<>(60);

    @Override
    public void run() {
        while(true) {
            try {
                SyncScript syncScript= queue.take();
                syncScript.onComplete(syncScript.exec());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addScript(SyncScript syncScript){
        queue.offer(syncScript);
    }
}
