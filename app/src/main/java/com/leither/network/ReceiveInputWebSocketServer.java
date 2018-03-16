package com.leither.network;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.leither.common.Global;

import org.json.JSONException;
import org.json.JSONObject;

import cn.leither.touchlibiary.Nexus5;

public class ReceiveInputWebSocketServer implements Server {
    private Nexus5 nexus5 = new Nexus5();

    ReceiveInputWebSocketServer(AsyncHttpServer asyncHttpServer) {
        setListener(asyncHttpServer);
        nexus5.switchIme();
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        server.websocket("/event", (webSocket, request) -> {
            webSocket.setClosedCallback(ex -> {
                if (ex != null) Log.e("WebSocket", "Error");
            });
            Global.getDefault().getInputWebSocket().add(webSocket);
            webSocket.setStringCallback(this::sendMsg);
        });
    }

    private void sendMsg(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.getInt("code") == 3) {
                nexus5.input(jsonObject.getString("msg"));
            } else {
                nexus5.sendEvent(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
