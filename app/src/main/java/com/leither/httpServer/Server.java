package com.leither.httpServer;

import com.koushikdutta.async.http.server.AsyncHttpServer;

/**
 * Created by lvqiang on 17-12-22.
 */

public interface Server {
    public void setListener(AsyncHttpServer server);
}
