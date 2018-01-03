package com.leither.scripts.syncScripts;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.MsgContent;
import com.leither.share.Global;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GetAllConversation extends SyncScript{

    public AsyncHttpServerResponse response;
    public GetAllConversation(AsyncHttpServerResponse response){
        super(response);
        this.response = response;
    }
    @Override
    public String exec() throws Exception{
        Map<String, MsgContent> msgSummaryMap = Global.getDefault().getAllConversation();
        Set<String> set = msgSummaryMap.keySet();
        for (String s : set) {
            Log.d("CONTENT", msgSummaryMap.get(s).toString());
        }
        JSONObject jsonObject = new JSONObject(msgSummaryMap);
        return returnValue(jsonObject);
    }
}