package com.leither.scripts.asyncScripts;

public interface AsyncScript {
    public String start() throws Exception;
    public void onComplete();
}
