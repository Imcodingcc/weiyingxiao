package com.leither.scripts.asyncScripts;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.entity.MsgContent;
import com.leither.entity.ChatMsg;
import com.leither.entity.MsgSummary;
import com.leither.exception.NodeNullException;
import com.leither.operation.BasicAction;
import com.leither.scripts.syncScripts.SyncScript;
import com.leither.share.Global;
import com.leither.weChatVersion.WeChatResourceId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RefreshMsg extends AsyncScript {
    private static final String TAG = RefreshMsg.class.getName();

    private AccessibilityService accessibilityService = Global.getDefault().getAccessibilityService();
    private WeChatResourceId weChatResourceId = Global.getDefault().getWeChatResourceId();

    public RefreshMsg() {
        super(null);
    }

    private void getConversationContent() throws Exception{
        BasicAction.DoubleClickById(weChatResourceId.weChat_main_tab, 0);
        Thread.sleep(1000);
        List<AccessibilityNodeInfo> tabMsgCountAll =
                accessibilityService
                        .getRootInActiveWindow()
                        .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_main_tab_msg_count);
        List<AccessibilityNodeInfo> listView =
                accessibilityService
                        .getRootInActiveWindow()
                        .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_main_list_view);
        if(listView.size() == 0){
            throw new NodeNullException("list not found");
        }
        while(tabMsgCountAll.size() != 0){
            List<AccessibilityNodeInfo> list =
                    accessibilityService
                            .getRootInActiveWindow()
                            .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_main_list);
            if(list.size() == 0){
                throw new NodeNullException("list not found");
            }
            for (int i = 0; i < list.size(); i++) {
                List<AccessibilityNodeInfo> tabMsgCount =
                        accessibilityService
                                .getRootInActiveWindow()
                                .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_main_tab_msg_count);
                if(tabMsgCount.size() == 0){
                    break;
                }
                AccessibilityNodeInfo oneMsg = list.get(i);
                List<AccessibilityNodeInfo> msgCount = oneMsg.
                        findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_msg_count);
                int readCount;
                String name;
                String lastTime;
                if(msgCount.size() == 0){
                    continue;
                }else{
                    readCount = Integer.parseInt(msgCount.get(0).getText().toString());
                    name = oneMsg
                            .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_title)
                            .get(0)
                            .getText()
                            .toString();
                    lastTime = oneMsg
                            .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_lastTime)
                            .get(0)
                            .getText()
                            .toString();
                }
                Rect rect0 = new Rect();
                oneMsg.getBoundsInScreen(rect0);
                Global.getDefault().getRootedAction().tap(rect0);
                Thread.sleep(500);
                List<AccessibilityNodeInfo> contents =
                        accessibilityService
                                .getRootInActiveWindow()
                                .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_content);
                int count2 = contents.size();
                if(count2 == 0){
                    throw new NodeNullException("content view not found or msg is null");
                }

                List<ChatMsg> texts = new ArrayList<>();
                for (int i1 = 0; i1 < readCount; i1++) {
                    Thread.sleep(200);
                    Rect rect1 = new Rect();
                    int index = count2 -1 - i1;
                    if(index < 0){
                        break;
                    }
                    contents.get(index).getBoundsInScreen(rect1);
                    Global.getDefault().getRootedAction().longPress(rect1);
                    List<AccessibilityNodeInfo> copy =
                            accessibilityService
                                    .getRootInActiveWindow()
                                    .findAccessibilityNodeInfosByText("复制");
                    if(copy.size() == 0){
                        continue;
                    }
                    Rect rect = new Rect();
                    copy.get(0).getBoundsInScreen(rect);
                    Global.getDefault().getRootedAction().tap(rect);
                    String text = Global.getDefault().getClipboardManager().getText().toString();
                    texts.add(new ChatMsg(name, text));
                }
                MsgContent megs = Global.getDefault().getRecentConversation().get(name);
                Collections.reverse(texts);
                if(megs != null){
                    megs.getMsg().addAll(texts);
                }else{
                    megs = new MsgContent();
                    megs.setMsg(new ArrayList<>(texts));
                }
                megs.setLastTime(lastTime);
                Global.getDefault().getRecentConversation().put(name, megs);

                MsgContent msgContent = new MsgContent();
                msgContent.setMsg(texts);
                addToAll(name, msgContent);

                if(!Global.getDefault().getConversationList().containsKey(name)){
                    Global.getDefault()
                            .getConversationList()
                            .put(name,
                                    new MsgSummary(name,
                                            lastTime,
                                            texts.get(texts.size() -1).getMsg()));
                }
                Thread.sleep(200);
                BasicAction.back(1);
                Thread.sleep(200);
            }
            listView.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            tabMsgCountAll =
                    accessibilityService
                            .getRootInActiveWindow()
                            .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_main_tab_msg_count);
        }
    }

    private void addToAll(String name, MsgContent msgContent){
        MsgContent old = Global.getDefault().getAllConversation().get(name);
        if(old == null){
            Global.getDefault().getAllConversation().put(name, msgContent);
        }else{
            old.setLastTime(msgContent.getLastTime());
            old.getMsg().addAll(msgContent.getMsg());
            Global.getDefault().getAllConversation().put(name, old);
        }
    }

    @Override
    public String start() throws Exception {
        getConversationContent();
        return "0";
    }
}