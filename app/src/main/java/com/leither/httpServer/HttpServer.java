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
import com.leither.scripts.syncScripts.SyncScript;
import com.leither.scripts.asyncScripts.WeChatId;
import com.leither.share.Global;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpServer implements Server{
    private static final String TAG = "HttpServer";

    private String[] asyncHttpInterface = new String[]{
            "Mass",
            "AddOne",
            "BatchAdd",
            "SendMsg"};

    private String[] syncAndReturnInterface = new String[]{
            "GetConversationList",
            "GetWeChatId",
            "GetRecentConversation",
            "GetAllConversation",
            "GetOneChatRecord",
            "GetAddOneStatus"};

    private AsyncTaskRunner asyncTaskRunner;
    private SyncTaskRunner syncAndReturnRunner;

    HttpServer(AsyncHttpServer asyncHttpServer){
        new Thread(()->{
            sendIp();
            if(!startThread()) return;
            setListener(asyncHttpServer);
        }).start();
    }

    private boolean startThread(){
        asyncTaskRunner = new AsyncTaskRunner();
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
        new RefreshListRunner(asyncTaskRunner).start();
        return true;
    }

    private List<String> scanIpList() throws Exception {
        InetAddress inetAddress = Tools.getLocalHostLANAddress();
        String ip = inetAddress.getHostName();
        String[] bit = ip.split("\\.");
        Log.d(TAG, "scanIpList: " + Arrays.toString(bit));
        List<String> reachableIp = new ArrayList<>();
        for (int i = 0; i < 255; i++) {
            String isReachableIp = bit[0] + "." + bit[1] + "." +bit[2] + "." + i;
            if(Tools.isHostReachable(isReachableIp, 10)){
                reachableIp.add(isReachableIp);
            }
        }
        return reachableIp;
    }

    private String connServer(List<String> list){
        if (list == null) {
            return null;
        }
        for (String s : list) {
            Log.d(TAG, "connServer: " + s);
            if(Tools.isHostConnectAble(s, 5758)){
                return s;
            };
        }
        return null;
    }

    private void sendIp(){
        try {
            List<String> list = scanIpList();
            String s = connServer(list);
            if (s == null) {
               return;
            }
            AsyncHttpClient asyncHttpClient = AsyncHttpClient.getDefaultInstance();
            String uri = "http://" + s + ":5758" + "/phoneIp?ip=" + Tools.getLocalHostLANAddress().getHostName();
            asyncHttpClient.execute(uri, null).get().message();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        for (final String async : asyncHttpInterface) {
            server.post("/" + async, (request, response) -> {
                setHeader(response);
                String param= request.getBody().toString();
                Task task = TaskFactory.getTask(async, param);
                asyncTaskRunner.addTask(task);
                response.send("ok");
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

    private void setHeader(AsyncHttpServerResponse response){
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }
}