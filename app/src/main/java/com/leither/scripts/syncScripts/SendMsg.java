package com.leither.scripts.syncScripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.operation.BasicAction;
import com.leither.share.Global;

import org.json.JSONObject;

public class SendMsg extends SyncScript{

    String param;
    public AsyncHttpServerResponse response;
    public SendMsg(AsyncHttpServerResponse response, String param){
        super(response);
        this.param = param;
        this.response = response;
    }

    @Override
    public String exec() throws Exception{
        //JSONObject jsonObject = new JSONObject(param);
        //String who = jsonObject.getString("who");
        //String msg = jsonObject.getString("msg");
        //BasicAction.reOpenWeChat();
        //BasicAction.Click("搜索", 0);
        //BasicAction.input(who.substring(0, who.length() - 1), Global.getDefault().getAccessibilityService().getRootInActiveWindow());
        //Thread.sleep(500);
        //BasicAction.Click(msg, 0);
        //Thread.sleep(500);
        BasicAction.input(param, Global.getDefault().getAccessibilityService().getRootInActiveWindow());
        Thread.sleep(500);
        BasicAction.Click("发送", 0);
        Thread.sleep(500);
        return returnValue("success");
    }
}