package com.leither.Task.asyncTask;

import android.annotation.SuppressLint;

import com.leither.operation.BasicAction;
import com.leither.scripts.asyncScripts.AsyncScript;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

@SuppressLint("UseSparseArrays")
public class AsyncTaskRunner extends Thread{
    public BlockingQueue<Task> queue = new LinkedBlockingDeque<>(10);
    private Map<Long, Task> waitMap = new HashMap<>();
    private Map<Long, Task> pausedMap= new HashMap<>();

    @Override
    public void run() {
        while(true) {AsyncScript asyncScript = null;
            try {
                Task task = queue.take();
                long id = task.getId();
                asyncScript = task.getAsyncScript();
                asyncScript.start();
                asyncScript.onComplete();
                waitMap.remove(id);
            } catch (Exception e) {
                assert asyncScript != null;
                asyncScript.onComplete();
                try {
                    BasicAction.reOpenWeChat();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

    public void addTask(Task task){
        queue.offer(task);
        waitMap.put(task.getId(), task);
    }

    public void removeWork(long id) {
        Task task = waitMap.remove(id);
        queue.remove(task);
    }

    public void proceed(long id){
        Task task = pausedMap.remove(id);
        waitMap.put(id, task);
        queue.offer(task);
    }

    public void pauseWork(long id){
        Task task = waitMap.remove(id);
        pausedMap.put(task.getId(), task);
    }
}
