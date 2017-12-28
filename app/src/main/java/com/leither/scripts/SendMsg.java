package com.leither.scripts;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.exception.NodeNullException;
import com.leither.operation.BasicAction;
import com.leither.share.Global;

public class SendMsg implements SyncScript{

    String param;
    AsyncHttpServerResponse response;
    public SendMsg(AsyncHttpServerResponse response, String param){
        this.param = param;
        this.response = response;
    }

    @Override
    public String exec() {
        try {
            BasicAction.input(param, Global.getDefault().getAccessibilityService().getRootInActiveWindow());
            Thread.sleep(1000);
            BasicAction.Click("发送");
        } catch (NodeNullException | InterruptedException e) {
            BasicAction.back(10);
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
