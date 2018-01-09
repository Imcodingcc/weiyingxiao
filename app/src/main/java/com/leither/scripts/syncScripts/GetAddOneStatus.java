package com.leither.scripts.syncScripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.share.Global;

public class GetAddOneStatus extends SyncScript{

    public AsyncHttpServerResponse response;
    public String phoneNum;
    public GetAddOneStatus(AsyncHttpServerResponse response, String phoneNum){
        super(response);
        this.response = response;
        this.phoneNum = phoneNum;
    }

    @Override
    public String exec() throws Exception{
        String res = Global.getDefault().getAddOneStatus().get(phoneNum);
        if(res == null){
            return "0";
        }
        return res;
    }
}