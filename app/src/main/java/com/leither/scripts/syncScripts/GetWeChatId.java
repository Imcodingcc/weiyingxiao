package com.leither.scripts.syncScripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.common.Global;

public class GetWeChatId extends SyncScript{

    public AsyncHttpServerResponse response;
    public GetWeChatId(AsyncHttpServerResponse response){
        super(response);
        this.response = response;
    }
    @Override
    public String exec() throws Exception{
        return Global.getDefault().getWeChatId();
    }
}