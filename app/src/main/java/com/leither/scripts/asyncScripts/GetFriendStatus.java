package com.leither.scripts.asyncScripts;

import android.accessibilityservice.AccessibilityService;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.operation.BasicAction;
import com.leither.share.Global;

public class GetFriendStatus extends AsyncScript {

    public AsyncHttpServerResponse response;
    private String tel;
    private AccessibilityService service = Global.getDefault().getAccessibilityService();
    public GetFriendStatus(AsyncHttpServerResponse response, String tel){
        super(response);
        this.response = response;
        this.tel = tel;
    }

    @Override
    public String start() throws Exception {
        BasicAction.reOpenWeChat();
        BasicAction.Click("搜索", 0);
        BasicAction.input(tel, service.getRootInActiveWindow());
        Thread.sleep(1000);
        boolean isFriend = service
                .getRootInActiveWindow()
                .findAccessibilityNodeInfosByText("联系人").size() != 0;
        Thread.sleep(1000);
        BasicAction.back(2);
        if(isFriend){
            return "0";
        }
        return "-1";
    }
}