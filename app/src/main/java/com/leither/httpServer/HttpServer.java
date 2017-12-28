package com.leither.httpServer;

import android.util.Base64;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.leither.Task.ScriptFactory;
import com.leither.Task.SyncTaskRunner;
import com.leither.Task.Task;
import com.leither.Task.TaskFactory;
import com.leither.Task.AsyncTaskRunner;
import com.leither.scripts.SyncScript;

import java.io.UnsupportedEncodingException;

public class HttpServer implements Server{
    private String[] asyncHttpInterface = new String[]{"Mass", "addOne", "BatchAdd"};
    private String[] syncHttpInterface = new String[]{"RefreshConversations", "GetConversationContent", "OpenConversation", "SendMsg"};

    private AsyncTaskRunner asyncTaskRunner;
    private SyncTaskRunner syncTaskRunner;
    HttpServer(){
        asyncTaskRunner = new AsyncTaskRunner();
        syncTaskRunner = new SyncTaskRunner();
        asyncTaskRunner.start();
        syncTaskRunner.start();
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        for (final String async : asyncHttpInterface) {
            server.post("/" + async, new HttpServerRequestCallback() {
                @Override
                public void onRequest(final AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                    request.getHeaders().add("text/plain", "charset=utf-8");
                    response.getHeaders().add("Access-Control-Allow-Origin", "*");
                    response.getHeaders().add("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
                    response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                    response.send("ok");
                    Task task = TaskFactory.getTask(async, "hello");
                    asyncTaskRunner.addTask(task);
                }
            });
        }
        for (final String sync : syncHttpInterface) {
            server.post("/" + sync, new HttpServerRequestCallback() {
                @Override
                public void onRequest(final AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                    if(asyncTaskRunner.queue.size() == 0){
                        response.getHeaders().add("Access-Control-Allow-Origin", "*");
                        response.getHeaders().add("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
                        response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                        if(request.getBody() !=null){
                            byte[] bytes = Base64.decode(request.getBody().toString(), Base64.DEFAULT);
                            try {
                                String result = new String(bytes, "utf-8");
                                SyncScript syncScript = ScriptFactory.getTask(sync, response, result);
                                syncTaskRunner.addScript(syncScript);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }else{
                            response.send("param null");
                        }
                    }else{
                        response.send("other tasks is running");
                    }
                }
            });
        }
    }
}
