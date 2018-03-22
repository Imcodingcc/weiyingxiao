package com.leither.scripts.asyncScripts;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.common.Action;
import com.leither.common.Global;

public class FriendCircle extends AsyncScript {

    private String text;
    private String[] steps = {"发现", "朋友圈"};
    private AccessibilityService service = Global.getDefault().getAccessibilityService();
    public FriendCircle(AsyncHttpServerResponse response, String text){
        super(response);
        this.text= text;
    }

    @Override
    public String start() throws Exception {
        for (String step : steps) {
            Action.Click(step, 0);
        }
        Rect rect = new Rect(1, 2, 1, 2);
        Global.getDefault().getNexus5().longPress(rect);
        Action.findAndInput(text, service.getRootInActiveWindow());
        Thread.sleep(1000);
        Action.Click("发送", 0);
        return "0";
    }
}