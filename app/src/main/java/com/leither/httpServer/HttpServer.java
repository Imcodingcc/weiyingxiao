package com.leither.httpServer;

import android.util.Base64;
import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.leither.Task.syncTask.RefreshListRunner;
import com.leither.Task.syncTask.ScriptFactory;
import com.leither.Task.syncTask.SyncTaskRunner;
import com.leither.Task.asyncTask.Task;
import com.leither.Task.asyncTask.TaskFactory;
import com.leither.Task.asyncTask.AsyncTaskRunner;
import com.leither.scripts.syncScripts.SyncScript;

import java.io.UnsupportedEncodingException;

public class HttpServer implements Server{
    private String[] asyncHttpInterface = new String[]{"Mass", "AddOne", "BatchAdd"};
    private String[] syncHttpInterface = new String[]{"GetConversationList", "RefreshConversations", "OpenConversation", "SendMsg", "WeChatId"};

    private AsyncTaskRunner asyncTaskRunner;
    private SyncTaskRunner syncTaskRunner;
    private RefreshListRunner refreshListRunner;
    HttpServer(){
        asyncTaskRunner = new AsyncTaskRunner();
        syncTaskRunner = new SyncTaskRunner();
        refreshListRunner = new RefreshListRunner(syncTaskRunner);
        asyncTaskRunner.start();
        syncTaskRunner.start();
        refreshListRunner.start();
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
                    Object param= request.getBody();
                    if(param !=null){
                        byte[] bytes = Base64.decode(param.toString(), Base64.DEFAULT);
                        try {
                            Log.d("lvqiangTest", async);
                            String result = new String(bytes, "utf-8");
                            Task task = TaskFactory.getTask(async, result);
                            asyncTaskRunner.addTask(task);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    response.send("ok");
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
                                String param = new String(bytes, "utf-8");
                                SyncScript syncScript = ScriptFactory.getTask(sync, response, param);
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
