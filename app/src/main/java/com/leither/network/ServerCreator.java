package com.leither.network;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import java.util.concurrent.BlockingQueue;

public class ServerCreator {
    private AsyncHttpServer server = new AsyncHttpServer();
    private static ServerCreator socketCreator = null;
    private AllHttpServer httpServer = null;

    private ServerCreator(){
        setListener();
    }

    private void setListener(){
        server.listen(5000);
    }

    public void setWsListener(BlockingQueue<byte[]> dataList){
        new SendScreenshotWebSocketServer(dataList, server);
        new ReceiveInputWebSocketServer(server);
    }

    public void setHttpListener(){
        httpServer = new AllHttpServer(server);
    }

    public static ServerCreator getDefault(){
        if(socketCreator == null){
            socketCreator = new ServerCreator();
        }
        return socketCreator;
    }

    public void destroy(){
        httpServer.destroy();
    }
}
