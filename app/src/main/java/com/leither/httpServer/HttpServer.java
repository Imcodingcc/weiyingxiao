package com.leither.httpServer;

import android.util.Log;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.Task.syncTask.RefreshListRunner;
import com.leither.Task.syncTask.ScriptFactory;
import com.leither.Task.syncTask.SyncTaskRunner;
import com.leither.Task.asyncTask.Task;
import com.leither.Task.asyncTask.TaskFactory;
import com.leither.Task.asyncTask.AsyncTaskRunner;
import com.leither.common.Tools;
import com.leither.scripts.asyncScripts.RefreshConversations;
import com.leither.scripts.asyncScripts.WeChatId;
import com.leither.scripts.syncScripts.SyncScript;
import com.leither.share.Global;
import com.leither.share.ShotApplication;

import java.util.ArrayList;
import java.util.List;

public class HttpServer implements Server {
    private static final String TAG = "HttpServer";

    private String[] asyncHttpInterface = new String[]{
            "Mass",
            "AddFriend",
            "BatchAdd",
            "GetFriendStatus",
            "TouchTest",
            "SendMsg"};

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
    private RefreshListRunner refreshListRunner;

    HttpServer(AsyncHttpServer asyncHttpServer) {
        new Thread(() -> {
            sendIp();
            if (!startThread()) return;
            setListener(asyncHttpServer);
        }).start();
    }

    private boolean startThread() {
        asyncTaskRunner = new AsyncTaskRunner("asyncTaskRunner");
        syncAndReturnRunner = new SyncTaskRunner();
        asyncTaskRunner.start();
        syncAndReturnRunner.start();
        try {
            Global.getDefault().getRootedAction().back(1);
            new WeChatId().start();
            new RefreshConversations().start();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        refreshListRunner = new RefreshListRunner(asyncTaskRunner);
        refreshListRunner.start();
        return true;
    }

    private List<String> discoverIp() throws Exception {
        String[] hostName = Tools
                .getLocalHostLANAddress()
                .getHostName()
                .split("\\.");
        List<String> reachableIp = new ArrayList<>();
        for (int i = 0; i < 255; i++) {
            String isReachableIp = hostName[0]
                    + "." +
                    hostName[1]
                    + "." +
                    hostName[2] + "." + i;
            if (Tools.isHostReachable(isReachableIp, 20)) {
                reachableIp.add(isReachableIp);
            }
        }
        return reachableIp;
    }

    private String discoverReachablePortIp(List<String> list) {
        if (list == null) {
            return null;
        }
        for (String s : list) {
            Log.d(TAG, "discoverReachablePortIp: " + s);
            if (Tools.isHostConnectAble(s, 5758)) {
                return s;
            }
        }
        return null;
    }

    private void sendIp() {
        try {
            AsyncHttpClient.getDefaultInstance()
                    .execute("http://" + discoverReachablePortIp(discoverIp()) + ":5758"
                            + "/ipaddr?ip=" + Tools.getLocalHostLANAddress().getHostName()
                            + "&mac=" + Tools.getWifiMac(ShotApplication.getContext())
                            + "&model=" + Tools.getDeviceName(), null).get().message();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }
}