package com.leither.scripts.syncScripts;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.MsgSummary;
import com.leither.share.Global;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GetConversationList extends SyncScript{

    public AsyncHttpServerResponse response;
    public GetConversationList(AsyncHttpServerResponse response){
        super(response);
        this.response = response;
    }
    @Override
    public String exec() throws Exception{
        Map<String, MsgSummary> msgSummaryMap = Global.getDefault().getConversationList();
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(msgSummaryMap);
        return returnValue(res);
    }
}