package com.leither.network;

import com.koushikdutta.async.http.server.AsyncHttpServer;

public interface Server {
    void setListener(AsyncHttpServer server);
}
