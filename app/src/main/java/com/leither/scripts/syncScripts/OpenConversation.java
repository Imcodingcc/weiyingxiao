package com.leither.scripts.syncScripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.operation.BasicAction;
import com.leither.share.Global;

public class OpenConversation extends SyncScript{

    private String param;
    public OpenConversation(AsyncHttpServerResponse response, String param){
        super(response);
        this.param = param;
    }

    @Override
    public String exec() throws Exception{
        BasicAction.reOpenWeChat();
        BasicAction.Click("搜索", 0);
        BasicAction.input(param.substring(0, param.length() - 1), Global.getDefault().getAccessibilityService().getRootInActiveWindow());
        Thread.sleep(800);
        BasicAction.Click(param, 0);
        return returnValue("success");
    }
}