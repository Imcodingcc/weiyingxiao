package com.leither.scripts.syncScripts;

import android.view.accessibility.AccessibilityNodeInfo;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.exception.NodeNullException;
import com.leither.operation.BasicAction;
import com.leither.share.Global;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeChatId extends SyncScript{
    private String[] steps = new String[]{"我", "设置", "帐号与安全"};
    public AsyncHttpServerResponse response;
    public WeChatId(AsyncHttpServerResponse response){
        super(response);
        this.response = response;
    }
    @Override
    public String exec() throws Exception{
        BasicAction.reOpenWeChat();
        for (String step : steps) {
            BasicAction.Click(step, 0);
        }
        AccessibilityNodeInfo nodeInfo = Global.getDefault().getAccessibilityService().getRootInActiveWindow();
        List<AccessibilityNodeInfo> idsInfo= nodeInfo.findAccessibilityNodeInfosByViewId(Global.getDefault().getWeChatResourceId().weChat_account_id);
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = null;
        if(idsInfo.size() >=4){
            map.put("id", idsInfo.get(0).getText().toString());
            map.put("qq", idsInfo.get(1).getText().toString());
            map.put("phoneNum", idsInfo.get(2).getText().toString());
            map.put("email", idsInfo.get(3).getText().toString());
            jsonObject = new JSONObject(map);
        }
        if(jsonObject != null){
            return returnValue(jsonObject);
        }
        throw new NodeNullException("node not found");
    }
}