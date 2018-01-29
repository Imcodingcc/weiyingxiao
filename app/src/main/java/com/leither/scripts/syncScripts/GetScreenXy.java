package com.leither.scripts.syncScripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.MsgContent;
import com.leither.share.Global;

import java.util.Map;

public class GetScreenXy extends SyncScript{

    public AsyncHttpServerResponse response;
    public GetScreenXy(AsyncHttpServerResponse response){
        super(response);
        this.response = response;
    }
    @Override
    public String exec() throws Exception{
        Map<String, Integer> xy= Global.getDefault().getXy();
        ObjectMapper objectMapper = new ObjectMapper();
        String res = objectMapper.writeValueAsString(xy);
        return returnValue(res);
    }
}