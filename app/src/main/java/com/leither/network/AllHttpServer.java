package com.leither.network;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.Task.ScriptFactory;
import com.leither.Task.SyncTaskRunner;
import com.leither.Task.Task;
import com.leither.Task.TaskFactory;
import com.leither.Task.AsyncTaskRunner;
import com.leither.scripts.syncScripts.SyncScript;

public class AllHttpServer implements Server {

    //TODO I want change this const array to interface
    private final String[] asyncHttpInterface = new String[]{
            "Mass",
            "AddFriend",
            "BatchAdd",
            "GetFriendStatus",
            "TouchTest",
            "SendChatMsg"};

    private final String[] syncHttpInterface = new String[]{
            "GetConversationList",
            "GetRecentConversation",
            "GetAllConversation",
            "GetOneChatRecord",
            "GetScreenXy",
            "GetAddOneStatus"};

    private AsyncTaskRunner asyncTaskRunner;
    private SyncTaskRunner syncTaskRunner;

    AllHttpServer(AsyncHttpServer asyncHttpServer) {
        setListener(asyncHttpServer);
        startTaskRunner();
    }

    private void startTaskRunner() {
        new SendIpTimerRunner();
        syncTaskRunner = new SyncTaskRunner();
        asyncTaskRunner = new AsyncTaskRunner("asyncTaskRunner");
        syncTaskRunner.start();
        asyncTaskRunner.start();
        //new RefreshDetailChatMsgRunner(asyncTaskRunner).start();
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

        for (final String sync : syncHttpInterface) {
            server.post("/" + sync, (request, response) -> {
                setHeader(response);
                String param = request.getBody().toString();
                SyncScript syncScript = ScriptFactory.getTask(sync, response, param);
                syncTaskRunner.addScript(syncScript);
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