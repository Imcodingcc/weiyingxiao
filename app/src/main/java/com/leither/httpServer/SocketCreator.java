package com.leither.httpServer;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import java.util.concurrent.BlockingQueue;

public class SocketCreator {
    private AsyncHttpServer server = new AsyncHttpServer();
    private static SocketCreator socketCreator = null;



    private SocketCreator(){
        setListener();
    }

    private void setListener(){
        server.listen(5000);
    }

    public void setWsListener(BlockingQueue<byte[]> dataList){
        new WebSocketServer(dataList, server);
    }

    public void setHttpListener(){
        new HttpServer(server);
    }

    public static SocketCreator getDefault(){
        if(socketCreator == null){
            socketCreator = new SocketCreator();
        }
        return socketCreator;
    }
}
