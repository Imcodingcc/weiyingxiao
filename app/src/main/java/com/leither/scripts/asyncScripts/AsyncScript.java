package com.leither.scripts.asyncScripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;

public abstract class AsyncScript {

    protected AsyncHttpServerResponse response;
    public AsyncScript(AsyncHttpServerResponse response){
        this.response = response;
    }

    public String start() throws Exception{
        return null;
    }

    public void onComplete(Exception e, String result){
        if(response == null){
            return;
        }
        if(e != null){
            response.send(errorValue(e.getMessage()));
        }else{
            response.send(returnValue(result));
        }
    }

    String returnValue(String obj){
        return  "{\"code\": 0, \"msg\": "+ obj +"}";
    }

    private String errorValue(String obj){
        return  "{\"code\": -1, \"msg\": "+ obj +"}";
    }
}