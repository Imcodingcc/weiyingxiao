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
        Rect rect = new Rect(948, 99, 1044, 195);
        Global.getDefault().getNexus5().longPress(rect);
        Thread.sleep(1000);
        Global.getDefault().getNexus5().tap(new Rect(200, 300, 200, 300));
        Thread.sleep(1000);
        Global.getDefault().getNexus5().input(text);
        Thread.sleep(1000);
        Global.getDefault().getNexus5().longPress(new Rect(876, 102, 1056, 192));
        return "0";
    }
}