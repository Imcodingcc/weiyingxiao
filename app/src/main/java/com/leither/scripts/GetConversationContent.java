package com.leither.scripts;

import android.view.accessibility.AccessibilityNodeInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.operation.BasicAction;
import com.leither.operation.ComplexAction;
import com.leither.operation.MsgText;
import com.leither.share.Global;
import com.leither.weChatVersion.WeChatResourceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

//TODO
public class GetConversationContent implements SyncScript{

    String param;
    AsyncHttpServerResponse response;
    public GetConversationContent(AsyncHttpServerResponse response, String param){
        this.param = param;
        this.response = response;
    }

    @Override
    public String exec() {
        //List<MsgText> list = ComplexAction.getConversationContent();
        //JSONArray jsonArray = new JSONArray();
        //for (MsgText msgSummary : list) {
        //    ObjectMapper mapper = new ObjectMapper();
        //    Map map = mapper.convertValue(msgSummary, Map.class);
        //    JSONObject jsonObject = new JSONObject(map);
        //    jsonArray.put(jsonObject);
        //}
        //return jsonArray.toString();
        List<AccessibilityNodeInfo> list = Global.getDefault().getAccessibilityService().getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.tencent.mm:id/j1");
        list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        return "ok";
    }

    @Override
    public void onComplete(String param) {
        response.send(param);
    }
}
