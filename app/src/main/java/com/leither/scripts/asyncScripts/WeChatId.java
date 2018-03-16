package com.leither.scripts.asyncScripts;

import android.view.accessibility.AccessibilityNodeInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leither.exception.NodeNullException;
import com.leither.operation.BasicAction;
import com.leither.common.Global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeChatId extends AsyncScript {
    private String[] steps = new String[]{"我", "设置", "帐号与安全"};
    private static final String TAG = WeChatId.class.getName();

    public WeChatId() {
        super(null);
    }

    @Override
    public String start() throws Exception {
        BasicAction.reOpenWeChat();
        for (String step : steps) {
            BasicAction.Click(step, 0);
        }
        AccessibilityNodeInfo nodeInfo = Global
                .getDefault()
                .getAccessibilityService()
                .getRootInActiveWindow();
        List<AccessibilityNodeInfo> idsInfo= nodeInfo
                .findAccessibilityNodeInfosByViewId(Global
                        .getDefault()
                        .getWeChatResourceId()
                        .weChat_account_id);
        Map<String, String> map = new HashMap<>();
        if(idsInfo.size() >=4){
            map.put("id", idsInfo.get(0).getText().toString());
            map.put("qq", idsInfo.get(1).getText().toString());
            map.put("phoneNum", idsInfo.get(2).getText().toString());
            map.put("email", idsInfo.get(3).getText().toString());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        if(map.keySet().size() != 0){
            String result = "{\"code\": 0, \"msg\": "+ objectMapper.writeValueAsString(map) +"}";
            Global.getDefault().setWeChatId(result);
            return "ok";
        }
        throw new NodeNullException("node not found");
    }
}