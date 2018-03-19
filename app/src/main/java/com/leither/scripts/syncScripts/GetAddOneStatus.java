package com.leither.scripts.syncScripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.common.Global;

public class GetAddOneStatus extends SyncScript{

    public AsyncHttpServerResponse response;
    private String tel;
    public GetAddOneStatus(AsyncHttpServerResponse response, String tel){
        super(response);
        this.response = response;
        this.tel = tel;
    }

    @Override
    public String exec() throws Exception{
        String res = Global.getDefault().getAddOneStatus().get(tel);
        return res == null? "0" : res;
    }
}