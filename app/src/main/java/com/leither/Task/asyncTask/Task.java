package com.leither.Task.asyncTask;

import com.leither.scripts.asyncScripts.AsyncScript;

public class Task {
    private long id;

    public Task(long id, AsyncScript asyncScript) {
        this.id = id;
        this.asyncScript = asyncScript;
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
}
