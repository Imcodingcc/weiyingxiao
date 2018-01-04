package com.leither.scripts.syncScripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.MsgContent;
import com.leither.share.Global;

public class GetOneChatRecord extends SyncScript{

    public AsyncHttpServerResponse response;
    private String param;
    public GetOneChatRecord(AsyncHttpServerResponse response, String param){
        super(response);
        this.response = response;
        this.param = param;
    }
    @Override
    public String exec() throws Exception{
        MsgContent msgContent= Global.getDefault().getAllConversation().get(param);
        if(msgContent == null){
            return returnValue(" ");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(msgContent);
        return returnValue(res);
    }
}