package com.leither.httpServer;

import android.util.Base64;
import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.leither.Task.syncTask.RefreshListRunner;
import com.leither.Task.syncTask.ScriptFactory;
import com.leither.Task.syncTask.SyncTaskRunner;
import com.leither.Task.asyncTask.Task;
import com.leither.Task.asyncTask.TaskFactory;
import com.leither.Task.asyncTask.AsyncTaskRunner;
import com.leither.scripts.syncScripts.RefreshConversations;
import com.leither.scripts.syncScripts.SyncScript;
import com.leither.scripts.syncScripts.WeChatId;
import com.leither.share.Global;

import java.io.UnsupportedEncodingException;

public class HttpServer implements Server{
    private String[] asyncHttpInterface = new String[]{"Mass", "AddOne", "BatchAdd"};
    private String[] syncHttpInterface = new String[]{
            "RefreshConversations",
            "OpenConversation",
            "SendMsg",
            "WeChatId", };
    private String[] syncAndReturnInterface = new String[]{
            "GetConversationList",
            "GetWeChatId",
            "GetRecentConversation",
            "GetAllConversation",
            "GetOneChatRecord"};

    private AsyncTaskRunner asyncTaskRunner;
    private SyncTaskRunner syncTaskRunner;
    private SyncTaskRunner syncAndReturnRunner;

    HttpServer(AsyncHttpServer asyncHttpServer){
        new Thread(()->{
            if(!preStart()) return;
            setListener(asyncHttpServer);
        }).start();
    }

    private boolean preStart(){
        asyncTaskRunner = new AsyncTaskRunner();
        syncTaskRunner = new SyncTaskRunner();
        syncAndReturnRunner = new SyncTaskRunner();
        asyncTaskRunner.start();
        syncTaskRunner.start();
        syncAndReturnRunner.start();
        try {
            Global.getDefault().getRootedAction().back(1);
            new WeChatId(null).exec();
            new RefreshConversations(null).exec();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        new RefreshListRunner(syncTaskRunner).start();
        return true;
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        for (final String async : asyncHttpInterface) {
            server.post("/" + async, (request, response) -> {
                request.getHeaders().add("text/plain", "charset=utf-8");
                response.getHeaders().add("Access-Control-Allow-Origin", "*");
                response.getHeaders().add("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
                response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                Object param= request.getBody();
                String result = "";
                if(param !=null){
                    byte[] bytes = Base64.decode(param.toString(), Base64.DEFAULT);
                    try {
                        result = new String(bytes, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                Task task = TaskFactory.getTask(async, result);
                asyncTaskRunner.addTask(task);
                response.send("ok");
            });
        }

        for (final String sync : syncHttpInterface) {
            server.post("/" + sync, (request, response) -> {
                if(asyncTaskRunner.queue.size() == 0){
                    response.getHeaders().add("Access-Control-Allow-Origin", "*");
                    response.getHeaders().add("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
                    response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                    String param = "";
                    if(request.getBody() !=null){
                        byte[] bytes = Base64.decode(request.getBody().toString(), Base64.DEFAULT);
                        try {
                            param = new String(bytes, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    SyncScript syncScript = ScriptFactory.getTask(sync, response, param);
                    syncTaskRunner.addScript(syncScript);
                }else{
                    response.send("other tasks is running");
                }
            });
        }

        for (final String sync : syncAndReturnInterface) {
            server.post("/" + sync, (request, response) -> {
                response.getHeaders().add("Access-Control-Allow-Origin", "*");
                response.getHeaders().add("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
                response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
                String param = "";
                if(request.getBody() !=null){
                    byte[] bytes = Base64.decode(request.getBody().toString(), Base64.DEFAULT);
                    try {
                        param = new String(bytes, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                SyncScript syncScript = ScriptFactory.getTask(sync, response, param);
                syncAndReturnRunner.addScript(syncScript);
            });
        }
    }
}