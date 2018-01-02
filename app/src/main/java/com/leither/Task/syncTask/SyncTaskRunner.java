package com.leither.Task.syncTask;

import android.annotation.SuppressLint;

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
    private List<AsyncHttpServerResponse> list = new ArrayList<>();

    @Override
    public void run() {
        while(true) {
            SyncScript syncScript = null;
            try {
                syncScript = queue.take();
                list.remove(syncScript.response);
                if(null != syncScript.response){
                    syncScript.response.send(syncScript.exec());
                }else{
                    syncScript.exec();
                }
            } catch (Exception e) {
                e.printStackTrace();
                assert syncScript != null;
                for (AsyncHttpServerResponse asyncHttpServerResponse : list) {
                    asyncHttpServerResponse.send("{code: -1, msg: "+e.getMessage()+"}");
                }
                queue = new LinkedBlockingDeque<>(60);
                list = new ArrayList<>();
                try {
                    BasicAction.reOpenWeChat();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void addScript(SyncScript syncScript){
        queue.offer(syncScript);
        list.add(syncScript.response);
    }
}
