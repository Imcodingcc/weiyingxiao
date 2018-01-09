package com.leither.Task.syncTask;

import com.leither.Task.asyncTask.AsyncTaskRunner;
import com.leither.Task.asyncTask.Task;
import com.leither.scripts.asyncScripts.AsyncScript;
import com.leither.scripts.asyncScripts.RefreshMsg;

import java.util.Date;

public class RefreshListRunner extends Thread{

    private AsyncTaskRunner asyncTaskRunner;

    public RefreshListRunner(AsyncTaskRunner asyncTaskRunner){
        this.asyncTaskRunner = asyncTaskRunner;
    }

    @Override
    public void run() {
        while (true){
            try {
                AsyncScript asyncScript = new RefreshMsg();
                Task task = new Task(new Date().getTime(), asyncScript);
                asyncTaskRunner.addTask(task);
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}