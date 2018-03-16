package com.leither.httpServer;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.leither.common.Global;

import org.json.JSONException;
import org.json.JSONObject;

import cn.leither.touchlibiary.Nexus5;

public class InputWebSocketServer implements Server{
    private static final String TAG = InputWebSocketServer.class.getName();
    private Nexus5 nexus5 = new Nexus5();

    InputWebSocketServer(AsyncHttpServer asyncHttpServer){
        setListener(asyncHttpServer);
        nexus5.switchIme();
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        server.websocket("/event", (webSocket, request) -> {
            Global.getDefault().getWebSocketList().add(webSocket);
            webSocket.setClosedCallback(ex -> {
                    if (ex != null) Log.e("WebSocket", "Error");
                    Global.getDefault().getWebSocketList().remove(webSocket);
            });

            webSocket.setStringCallback(s -> {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = jsonObject.getInt("code");
                    if(code == 0){
                        int x = jsonObject.getInt("x");
                        int y = jsonObject.getInt("y");
                        nexus5.touch(0, x, y);
                    }else if(code == 1){
                        nexus5.release(0);
                    }else{
                        String text = jsonObject.getString("text");
                        nexus5.input(text);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "setListener: " + s);
            });
        });
    }
}
