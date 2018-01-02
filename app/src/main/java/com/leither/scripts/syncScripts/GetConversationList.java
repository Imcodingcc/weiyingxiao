package com.leither.scripts.syncScripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.exception.NodeNullException;
import com.leither.share.Global;

public class GetConversationList extends SyncScript{

    public AsyncHttpServerResponse response;
    public GetConversationList(AsyncHttpServerResponse response){
        super(response);
        this.response = response;
    }
    @Override
    public String exec() throws Exception{
        return Global.getDefault().getConversationList();
    }
}
