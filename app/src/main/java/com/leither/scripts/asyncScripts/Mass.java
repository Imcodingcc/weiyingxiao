package com.leither.scripts.asyncScripts;

import android.accessibilityservice.AccessibilityService;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.common.Global;
import com.leither.common.Action;

public class Mass extends AsyncScript {
    private String[] step = new String[]{"我", "设置", "通用", "功能", "群发助手", "开始群发", "新建群发", "全选", "下一步"};
    private String[] step2 = new String[]{"发送", "设置", "清空此功能消息记录", "清空"};
    private String text;
    private AccessibilityService accessibilityService;

    public Mass(AsyncHttpServerResponse response, String text){
        super(response);
        this.text = text;
        accessibilityService = Global.getDefault().getAccessibilityService();
    }

    @Override
    public String start() throws Exception{
        Action.reOpenWeChat();
        for (String s : step) {
            Action.Click(s, 0);
        }
        Action.findAndInput(text, accessibilityService.getRootInActiveWindow());
        for (String s : step2) {
            Action.Click(s, 0);
        }
        return "0";
    }
}
