package com.leither.Task.syncTask;

import com.leither.scripts.syncScripts.RefreshConversations;

public class RefreshListRunner extends Thread{

    private SyncTaskRunner syncTaskRunner;

    public RefreshListRunner(SyncTaskRunner syncTaskRunner){
        this.syncTaskRunner = syncTaskRunner;
    }

    @Override
    public void run() {
        while (true){
            try {
                //syncTaskRunner.addScript(new RefreshConversations(null));
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
