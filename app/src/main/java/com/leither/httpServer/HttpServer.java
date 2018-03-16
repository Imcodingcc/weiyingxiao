package com.leither.httpServer;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.Task.syncTask.RefreshListRunner;
import com.leither.Task.syncTask.ScriptFactory;
import com.leither.Task.syncTask.SyncTaskRunner;
import com.leither.Task.asyncTask.Task;
import com.leither.Task.asyncTask.TaskFactory;
import com.leither.Task.asyncTask.AsyncTaskRunner;
import com.leither.scripts.syncScripts.SyncScript;

public class HttpServer implements Server {
    private String[] asyncHttpInterface = new String[]{
            "Mass",
            "AddFriend",
            "BatchAdd",
            "GetFriendStatus",
            "TouchTest",
            "SendChatMsg"};

    private String[] syncAndReturnInterface = new String[]{
            "GetConversationList",
            "GetWeChatId",
            "GetRecentConversation",
            "GetAllConversation",
            "GetOneChatRecord",
            "GetScreenXy",
            "GetAddOneStatus"};

    private AsyncTaskRunner asyncTaskRunner;
    private SyncTaskRunner syncAndReturnRunner;

    HttpServer(AsyncHttpServer asyncHttpServer) {
        setListener(asyncHttpServer);
        startTaskRunner();
    }

    private void startTaskRunner() {
        new SendIpTimerRunner();
        syncAndReturnRunner = new SyncTaskRunner();
        asyncTaskRunner = new AsyncTaskRunner("asyncTaskRunner");
        asyncTaskRunner.start();
        syncAndReturnRunner.start();
        new RefreshListRunner(asyncTaskRunner).start();
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        for (final String async : asyncHttpInterface) {
            server.post("/" + async, (request, response) -> {
                setHeader(response);
                String param = request.getBody().toString();
                Task task = TaskFactory.getTask(async, response, param);
                asyncTaskRunner.addTask(task);
            });
        }

        for (final String sync : syncAndReturnInterface) {
            server.post("/" + sync, (request, response) -> {
                setHeader(response);
                String param = request.getBody().toString();
                SyncScript syncScript = ScriptFactory.getTask(sync, response, param);
                syncAndReturnRunner.addScript(syncScript);
            });
        }
    }

    private void setHeader(AsyncHttpServerResponse response) {
        response.getHeaders()
                .add("Access-Control-Allow-Origin", "*");
        response.getHeaders()
                .add("Access-Control-Allow-Methods",
                        "POST, PUT, GET, OPTIONS, DELETE");
        response.getHeaders()
                .add("Access-Control-Allow-Headers",
                        "Content-Type");
    }
}