package com.leither.scripts.syncScripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SyncScript {
    public AsyncHttpServerResponse response;

    public SyncScript(AsyncHttpServerResponse response){
        this.response = response;
    }

    public String exec() throws  Exception{
        return "Did not do anything";
    }

    public void onComplete(String value){
        if(response != null){
            response.send(value);
        }
    }

    String returnValue(Object obj){
        Map<String, Object> map = new HashMap<>();
        map.put("code", "0");
        map.put("msg", obj);
        JSONObject res = new JSONObject(map);
        return  res.toString();
    }
}