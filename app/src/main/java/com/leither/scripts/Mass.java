package com.leither.scripts;

import android.accessibilityservice.AccessibilityService;

import com.leither.share.Global;
import com.leither.exception.NodeNullException;
import com.leither.operation.Operation;

public class Mass implements Script{
    private String[] step = new String[]{"我", "设置", "通用", "功能", "群发助手", "开始群发", "新建群发", "全选", "下一步"};
    private String[] step2 = new String[]{"发送", "设置", "清空此功能消息记录", "清空"};
    private String text;
    private AccessibilityService accessibilityService;

    public Mass(String text){
        this.text = text;
        accessibilityService = Global.getDefault().getAccessibilityService();
    }

    @Override
    public void start() {
        try {
            for (String s : step) {
                Operation.Click(s);
            }
            Operation.input(text, accessibilityService.getRootInActiveWindow());
            for (String s : step2) {
                Operation.Click(s);
            }
            Operation.back(2);
        } catch (NodeNullException e) {
            e.printStackTrace();
            Operation.back(10);
            Operation.openWechat();
        }
    }

    @Override
    public void onComplete() {

    }
}
