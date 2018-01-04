package com.leither.Task.syncTask;

import com.leither.scripts.syncScripts.RefreshMsg;

public class RefreshListRunner extends Thread{

    private SyncTaskRunner syncTaskRunner;

    public RefreshListRunner(SyncTaskRunner syncTaskRunner){
        this.syncTaskRunner = syncTaskRunner;
    }

    @Override
    public void run() {
        while (true){
            try {
                syncTaskRunner.addScript(new RefreshMsg(null));
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}