package com.leither.scripts.syncScripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.operation.BasicAction;
import com.leither.operation.ComplexAction;
import com.leither.entity.MsgSummary;
import com.leither.share.Global;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class RefreshConversations extends SyncScript{

    public AsyncHttpServerResponse response;
    public RefreshConversations(AsyncHttpServerResponse response){
        super(response);
        this.response = response;
    }

    @Override
    public String exec() throws Exception{
        List<MsgSummary> list;
        BasicAction.reOpenWeChat();
        BasicAction.DoubleClickById("android:id/text1", 0);
        Thread.sleep(1000);
        list = ComplexAction.getConversationList();
        JSONArray jsonArray = new JSONArray();
        for (MsgSummary msgSummary : list) {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.convertValue(msgSummary, Map.class);
            JSONObject jsonObject = new JSONObject(map);
            jsonArray.put(jsonObject);
        }
        Global.getDefault().setConversationList(returnValue(jsonArray));
        return returnValue(jsonArray);
    }
}
