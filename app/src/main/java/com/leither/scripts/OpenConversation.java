package com.leither.scripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.exception.NodeNullException;
import com.leither.operation.BasicAction;
import com.leither.share.Global;

public class OpenConversation implements SyncScript{

    String param;
    AsyncHttpServerResponse response;
    public OpenConversation(AsyncHttpServerResponse response, String param){
        this.param = param;
        this.response = response;
    }

    @Override
    public String exec() {
        try {
            BasicAction.back(4);
            BasicAction.openWechat();
            Thread.sleep(500);
            BasicAction.Click("搜索");
            BasicAction.input(param.substring(0, param.length() - 1), Global.getDefault().getAccessibilityService().getRootInActiveWindow());
            Thread.sleep(800);
            BasicAction.Click(param);
        } catch (NodeNullException | InterruptedException e) {
            BasicAction.back(4);
            BasicAction.openWechat();
            e.printStackTrace();
            return e.getMessage();
        }
        return "ok";
    }

    @Override
    public void onComplete(String param) {
        response.send(param);
    }
}
