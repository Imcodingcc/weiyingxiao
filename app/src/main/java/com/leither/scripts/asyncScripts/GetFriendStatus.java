package com.leither.scripts.asyncScripts;

import android.accessibilityservice.AccessibilityService;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.common.Action;
import com.leither.common.Global;

public class GetFriendStatus extends AsyncScript {

    private String tel;
    private AccessibilityService service = Global.getDefault().getAccessibilityService();
    public GetFriendStatus(AsyncHttpServerResponse response, String tel){
        super(response);
        this.tel = tel;
    }

    @Override
    public String start() throws Exception {
        Action.reOpenWeChat();
        Action.Click("搜索", 0);
        Action.findAndInput(tel, service.getRootInActiveWindow());
        Thread.sleep(1000);
        boolean isFriend = service
                .getRootInActiveWindow()
                .findAccessibilityNodeInfosByText("联系人").size() != 0;
        Thread.sleep(1000);
        Global.getDefault().getNexus5().back(2);
        if(isFriend){
            return "0";
        }
        return "-1";
    }
}