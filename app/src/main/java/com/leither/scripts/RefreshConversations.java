package com.leither.scripts;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.exception.NodeNullException;
import com.leither.operation.BasicAction;
import com.leither.operation.ComplexAction;
import com.leither.operation.MsgSummary;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class RefreshConversations implements SyncScript{

    private String param;
    private AsyncHttpServerResponse response;
    public RefreshConversations(AsyncHttpServerResponse response, String param){
        this.param = param;
        this.response = response;
    }
    @Override
    public String exec() {
        List<MsgSummary> list = null;
        try {
            BasicAction.back(5);
            BasicAction.openWechat();
            Thread.sleep(1000);
            BasicAction.DoubleClickById("android:id/text1");
            Thread.sleep(1000);
            list = ComplexAction.getConversationList();
            JSONArray jsonArray = new JSONArray();
            for (MsgSummary msgSummary : list) {
                ObjectMapper mapper = new ObjectMapper();
                Map map = mapper.convertValue(msgSummary, Map.class);
                JSONObject jsonObject = new JSONObject(map);
                jsonArray.put(jsonObject);
            }
            return jsonArray.toString();
        } catch (NodeNullException e) {
            BasicAction.back(10);
            BasicAction.openWechat();
            e.printStackTrace();
            return e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void onComplete(String param) {
        response.send(param);
    }
}
