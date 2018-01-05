package com.leither.scripts.syncScripts;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.MsgSummary;
import com.leither.share.Global;

import org.json.JSONObject;

import java.util.Map;

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