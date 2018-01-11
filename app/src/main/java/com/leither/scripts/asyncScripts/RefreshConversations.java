package com.leither.scripts.asyncScripts;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.exception.NodeNullException;
import com.leither.operation.BasicAction;
import com.leither.entity.MsgSummary;
import com.leither.scripts.syncScripts.SyncScript;
import com.leither.share.Global;
import com.leither.weChatVersion.WeChatResourceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class RefreshConversations extends AsyncScript {

    private static final String TAG = RefreshConversations.class.getName();
    private AccessibilityService accessibilityService = Global.getDefault().getAccessibilityService();
    private WeChatResourceId weChatResourceId = Global.getDefault().getWeChatResourceId();

    public RefreshConversations() {
        super(null);
    }

    private List<MsgSummary> readChatListById() throws NodeNullException {
        List<MsgSummary> msgSummaries = new ArrayList<>();
        AccessibilityNodeInfo nodeInfo = accessibilityService
                .getRootInActiveWindow();
        List<AccessibilityNodeInfo> list1 = nodeInfo
                .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_title);
        List<AccessibilityNodeInfo> list2 = nodeInfo
                .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_lastTime);
        List<AccessibilityNodeInfo> list3 = nodeInfo
                .findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_lastMsg);
        if(list1.size() == 0 || list2.size() == 0 || list3.size() == 0){
            throw new NodeNullException("conversation not found");
        }
        for (int i = 0; i < list1.size(); i++) {
            CharSequence a1, a2, a3;
            a1 = list1.get(i).getText();
            a2 = list2.get(i).getText();
            a3 = list3.get(i).getText();
            if(a1 != null && a2 != null && a3 != null){
                msgSummaries.add(new MsgSummary(a1.toString(), a2.toString(), a3.toString()));
            }
        }
        return msgSummaries;
    }

    private List<MsgSummary> getConversationList() throws Exception{
        List<MsgSummary> list = new ArrayList<>();
        _getConversationList(list, null);
        return deduplication(list);
    }

    private void _getConversationList(List<MsgSummary> list, List<MsgSummary> last) throws Exception{
        if(last == null){
            last = new ArrayList<>();
            last.add(new MsgSummary("", "", ""));
        }
        List<MsgSummary> list1 = readChatListById();
        if(!Objects.equals(last.get(0).getTitle(), list1.get(0).getTitle())){
            list.addAll(list1);
            BasicAction.slide(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            last = list1;
            Thread.sleep(200);
            _getConversationList(list, last);
        }
    }

    private List<MsgSummary> deduplication(List<MsgSummary> list){
        Set<MsgSummary> msgSummaries = new TreeSet<>((o1, o2)-> o1.getTitle().compareTo(o2.getTitle()));
        msgSummaries.addAll(list);
        return new ArrayList<>(msgSummaries);
    }

    @Override
    public String start() throws Exception {
        List<MsgSummary> list;
        BasicAction.reOpenWeChat();
        BasicAction.DoubleClickById(weChatResourceId.weChat_main_tab, 0);
        Thread.sleep(500);
        BasicAction.DoubleClickById(weChatResourceId.weChat_title, 0);
        Thread.sleep(1000);
        list = getConversationList();
        Map<String, MsgSummary> map = new HashMap<>();
        for (MsgSummary msgSummary : list) {
            map.put(msgSummary.getTitle(), msgSummary);
        }
        Global.getDefault().setConversationList(map);
        return "0";
    }
}