package com.leither.scripts.asyncScripts;

import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.ChatMsg;
import com.leither.entity.MsgContent;
import com.leither.operation.BasicAction;
import com.leither.common.Global;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendMsg extends AsyncScript {

    private static final String TAG = SendMsg.class.getName();
    private String param;
    public SendMsg(AsyncHttpServerResponse response, String param){
        super(response);
        this.param = param;
    }

    private void preSend(String name) throws Exception {
        if(Global.getDefault().getAllConversation().get(name) == null){
            return;
        }
        Map<String, Object> map = getLastMsg(name);
        if(map == null){
            return;
        }
        int removeRequire = (int) map.get("i");
        ChatMsg chatMsg = (ChatMsg) map.get("chatMsg");

        Map<String, Object> map1 = findMsgInScreen(chatMsg);
        int screenIndex = (int)map1.get("i");
        List<AccessibilityNodeInfo> node = (List<AccessibilityNodeInfo>) map1.get("node");
        if(screenIndex == -1){
            return;
        }
        removeMsg(removeRequire, chatMsg.getName());
        reAdd(screenIndex, chatMsg, node);
    }

    private Map<String, Object>getLastMsg(String name){
        MsgContent msgContent = Global.getDefault().getAllConversation().get(name);
        List<ChatMsg> chatMsgr = msgContent.getMsg();
        Map<String, Object> map = new HashMap<>();
        for (int i = chatMsgr.size() - 1; i >= 0; i--) {
            if(chatMsgr.get(i).getName().equals(name)){
                map.put("i", i);
                map.put("chatMsg", chatMsgr.get(i));
                return map;
            }
        }
        return null;
    }

    private void removeMsg(int removeRequire, String name){
        MsgContent msgContent = Global.getDefault().getAllConversation().get(name);
        List<ChatMsg> list = new ArrayList<>();
        for(int i = removeRequire; i<msgContent.getMsg().size(); i++){
            list.add(msgContent.getMsg().get(i));
        }
        msgContent.getMsg().removeAll(list);
    }

    private Map<String, Object> findMsgInScreen(ChatMsg chatMsg) throws Exception{
        List<AccessibilityNodeInfo> node = Global
                .getDefault()
                .getAccessibilityService()
                .getRootInActiveWindow()
                .findAccessibilityNodeInfosByViewId(Global.getDefault().getWeChatResourceId().weChat_conversation_content);
        int screenIndex = -1;
        for (int i = 0;i < node.size();i++) {
            Rect rect = new Rect();
            node.get(i).getBoundsInScreen(rect);
            if(rect.left < 500){
                Global.getDefault().getRootedAction().longPress(rect);
                List<AccessibilityNodeInfo> copy =
                        Global.getDefault().getAccessibilityService()
                                .getRootInActiveWindow()
                                .findAccessibilityNodeInfosByText("复制");
                if(copy.size() == 0){
                    continue;
                }
                Rect rect1 = new Rect();
                copy.get(0).getBoundsInScreen(rect1);
                Global.getDefault().getRootedAction().tap(rect1);
                if(Global.getDefault().getClipboardManager().getText().toString().equals(chatMsg.getMsg())){
                    screenIndex = i;
                }
            }
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("node", node);
        map1.put("i", screenIndex);
        return map1;
    }

    private void reAdd(int screenIndex, ChatMsg chatMsg, List<AccessibilityNodeInfo > node) throws Exception{
        Log.d("screenIndex", String.valueOf(screenIndex));
        Log.d("nodeSize", String.valueOf(node.size()));
        for (int i = screenIndex;i < node.size(); i++) {
            Rect rect = new Rect();
            node.get(i).getBoundsInScreen(rect);
            Global.getDefault().getRootedAction().longPress(rect);
            List<AccessibilityNodeInfo> copy =
                    Global.getDefault().getAccessibilityService()
                            .getRootInActiveWindow()
                            .findAccessibilityNodeInfosByText("复制");
            if(copy.size() == 0){
                continue;
            }
            Rect rect1 = new Rect();
            copy.get(0).getBoundsInScreen(rect1);
            Global.getDefault().getRootedAction().tap(rect1);
            ChatMsg chatMsg1;
            if(rect.left > 400){
                chatMsg1 = new ChatMsg("我", Global.getDefault().getClipboardManager().getText().toString());
            }else{
                chatMsg1 = new ChatMsg(chatMsg.getName(), Global.getDefault().getClipboardManager().getText().toString());
            }
            MsgContent msgContent = Global.getDefault().getAllConversation().get(chatMsg.getName());
            msgContent.getMsg().add(chatMsg1);
        }
    }

    @Override
    public String start() throws Exception {
        Log.d(TAG, "exec: " + param);
        JSONObject jsonObject = new JSONObject(param);
        String who = jsonObject.getString("name");
        String msg = jsonObject.getString("msg");
        BasicAction.reOpenWeChat();
        BasicAction.Click("搜索", 0);
        BasicAction.input(who
                        .substring(0
                                , who.length() - 1),
                Global.getDefault()
                        .getAccessibilityService()
                        .getRootInActiveWindow());
        Thread.sleep(500);
        BasicAction.Click(who, 0);
        Thread.sleep(500);
        preSend(who);
        Thread.sleep(200);
        BasicAction.input(msg, Global.getDefault().getAccessibilityService().getRootInActiveWindow());
        BasicAction.Click("发送", 0);
        Thread.sleep(500);
        BasicAction.back(2);
        MsgContent msgContent = Global.getDefault().getAllConversation().get(who);
        if(msgContent == null){
            msgContent = new MsgContent();
            msgContent.setLastTime(new Date().toString());
            ChatMsg chatMsg = new ChatMsg("我", msg);
            List<ChatMsg> list = new ArrayList<>();
            list.add(chatMsg);
            msgContent.setMsg(list);
            Global.getDefault().getAllConversation().put(who, msgContent);
        }else{
            msgContent.setLastTime(new Date().toString());
            msgContent.getMsg().add(new ChatMsg("我", msg));
        }
        return "0";
    }
}