package com.leither.Task;

import com.leither.scripts.Script;

public class Task {
    private long id;

    public Task(long id, Script script) {
        this.id = id;
        this.script = script;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    private Script script;
}
