package com.leither.network;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.leither.common.Global;

import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveInputWebSocketServer implements Server {

    ReceiveInputWebSocketServer(AsyncHttpServer asyncHttpServer) {
        setListener(asyncHttpServer);
        Global.getDefault().getNexus5().switchToAdbIME();
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        server.websocket("/event", (webSocket, request) -> {
            webSocket.setClosedCallback(ex -> {
                if (ex != null) Log.e("WebSocket", "Error");
            });
            Global.getDefault().getInputWebSocket().add(webSocket);
            webSocket.setStringCallback(s -> {
                try {
                    sendMsg(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private void sendMsg(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        if (jsonObject.getInt("code") == 3) {
            Global.getDefault().getNexus5().input(jsonObject.getString("msg"));
        } else {
            Global.getDefault().getNexus5().sendEvent(s);
        }
    }
}
