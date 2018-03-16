package com.leither.scripts.asyncScripts;

import android.accessibilityservice.AccessibilityService;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.common.Global;
import com.leither.operation.BasicAction;

public class Mass extends AsyncScript {
    private String[] step = new String[]{"我", "设置", "通用", "功能", "群发助手", "开始群发", "新建群发", "全选", "下一步"};
    private String[] step2 = new String[]{"发送", "设置", "清空此功能消息记录", "清空"};
    private String text;
    private AccessibilityService accessibilityService;
    private AsyncHttpServerResponse response;

    public Mass(AsyncHttpServerResponse response, String text){
        super(response);
        this.text = text;
        this.response = response;
        accessibilityService = Global.getDefault().getAccessibilityService();
    }

    @Override
    public String start() throws Exception{
        BasicAction.reOpenWeChat();
        for (String s : step) {
            BasicAction.Click(s, 0);
        }
        BasicAction.input(text, accessibilityService.getRootInActiveWindow());
        for (String s : step2) {
            BasicAction.Click(s, 0);
        }
        return "0";
    }
}
