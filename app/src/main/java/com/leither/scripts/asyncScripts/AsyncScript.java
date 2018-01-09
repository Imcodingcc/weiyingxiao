package com.leither.scripts.asyncScripts;

public interface AsyncScript {
    String start() throws Exception;
    void onComplete(Exception e, String result);
}
