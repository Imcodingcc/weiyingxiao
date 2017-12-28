package com.leither.httpServer;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import java.util.concurrent.BlockingQueue;

public class SocketCreator {
    private AsyncHttpServer server = new AsyncHttpServer();
    private static SocketCreator socketCreator = null;
    private BlockingQueue<byte[]> dataList;



    private SocketCreator(BlockingQueue<byte[]> dataList){
        this.dataList = dataList;
        setListener();
    }

    private void setListener(){
        new HttpServer().setListener(server);
        new WebSocketServer(dataList).setListener(server);
        server.listen(5000);
    }
    public static SocketCreator getDefault(BlockingQueue<byte[]> dataList){
        if(socketCreator == null){
            socketCreator = new SocketCreator(dataList);
        }
        return socketCreator;
    }
}
