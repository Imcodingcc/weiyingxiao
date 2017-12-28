package com.leither.scripts;

import android.accessibilityservice.AccessibilityService;

import com.leither.share.Global;
import com.leither.exception.NodeNullException;
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
    public String start() {
        try {
            for (String s : step) {
                BasicAction.Click(s);
            }
            BasicAction.input(text, accessibilityService.getRootInActiveWindow());
            for (String s : step2) {
                BasicAction.Click(s);
            }
            BasicAction.back(2);
        } catch (NodeNullException e) {
            e.printStackTrace();
            BasicAction.back(10);
            BasicAction.openWechat();
        }
        return null;
    }

    @Override
    public void onComplete() {

    }
}
