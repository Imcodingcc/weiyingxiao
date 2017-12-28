package com.leither.Task;

import com.leither.scripts.AsyncScript;

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
