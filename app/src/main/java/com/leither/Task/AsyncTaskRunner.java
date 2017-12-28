package com.leither.Task;

import android.annotation.SuppressLint;

import com.leither.scripts.AsyncScript;

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
        while(true) {
            try {
                Task task = queue.take();
                long id = task.getId();
                AsyncScript asyncScript = task.getAsyncScript();
                asyncScript.start();
                asyncScript.onComplete();
                waitMap.remove(id);
            } catch (InterruptedException e) {
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
