package com.leither.scripts.syncScripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.MsgSummary;
import com.leither.common.Global;

import java.util.Map;

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