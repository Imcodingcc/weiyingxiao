package com.leither.scripts;

public interface SyncScript {
    String exec();
    void onComplete(String param);
}
