package com.leither.httpServer;

import com.koushikdutta.async.http.server.AsyncHttpServer;

public interface Server {
    public void setListener(AsyncHttpServer server);
}
