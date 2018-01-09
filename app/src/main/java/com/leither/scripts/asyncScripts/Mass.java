package com.leither.scripts.asyncScripts;

import android.accessibilityservice.AccessibilityService;

import com.leither.share.Global;
import com.leither.operation.BasicAction;

public class Mass implements AsyncScript {
    private String[] step = new String[]{"我", "设置", "通用", "功能", "群发助手", "开始群发", "新建群发", "全选", "下一步"};
    private String[] step2 = new String[]{"发送", "设置", "清空此功能消息记录", "清空"};
    private String text;
    private AccessibilityService accessibilityService;

    public Mass(String text){
        this.text = text;
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
        return "ok";
    }

    @Override
    public void onComplete(Exception e, String result) {

    }
}
