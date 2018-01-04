package com.leither.scripts.syncScripts;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.MsgContent;
import com.leither.share.Global;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GetRecentConversation extends SyncScript{

    public AsyncHttpServerResponse response;
    public GetRecentConversation(AsyncHttpServerResponse response){
        super(response);
        this.response = response;
    }
    @Override
    public String exec() throws Exception{
        Map<String, MsgContent> msgSummaryMap = Global.getDefault().getRecentConversation();
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(msgSummaryMap);
        Global.getDefault().setRecentConversation(new HashMap<>());//clear
        return returnValue(res);
    }
}