package com.leither.scripts.syncScripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.MsgContent;
import com.leither.common.Global;

import java.util.Map;

public class GetAllConversation extends SyncScript{

    public AsyncHttpServerResponse response;
    public GetAllConversation(AsyncHttpServerResponse response, String forNull){
        super(response);
        this.response = response;
    }
    @Override
    public String exec() throws Exception{
        Map<String, MsgContent> msgSummaryMap = Global.getDefault().getAllConversation();
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(msgSummaryMap);
        return returnValue(res);
    }
}