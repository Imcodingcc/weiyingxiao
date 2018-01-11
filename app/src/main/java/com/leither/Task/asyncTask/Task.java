package com.leither.Task.asyncTask;

import android.support.annotation.NonNull;

import com.leither.scripts.asyncScripts.AsyncScript;

public class Task implements Comparable<Task>{
    private long id;
    private int nice;

    public Task(int nice, long id, AsyncScript asyncScript) {
        this.id = id;
        this.asyncScript = asyncScript;
        this.nice = nice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AsyncScript getAsyncScript() {
        return asyncScript;
    }

    public void setAsyncScript(AsyncScript asyncScript) {
        this.asyncScript = asyncScript;
    }

    private AsyncScript asyncScript;

    @Override
    public int compareTo(@NonNull Task another) {
        return this.nice > another.nice ? 1 : this.nice > another.nice ? -1: 0;
    }
}