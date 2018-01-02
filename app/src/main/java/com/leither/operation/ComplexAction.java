package com.leither.operation;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

import com.leither.entity.MsgSummary;
import com.leither.entity.MsgText;
import com.leither.exception.NodeNullException;
import com.leither.share.Global;
import com.leither.weChatVersion.WeChatResourceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class ComplexAction{
    @SuppressLint("StaticFieldLeak")
    private static AccessibilityService accessibilityService = Global.getDefault().getAccessibilityService();
    private static WeChatResourceId weChatResourceId = Global.getDefault().getWeChatResourceId();

    public static List<MsgSummary> readChatListById() throws NodeNullException {
        List<MsgSummary> msgSummaries = new ArrayList<>();
        AccessibilityNodeInfo nodeInfo = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list1 = nodeInfo.findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_title);
        List<AccessibilityNodeInfo> list2 = nodeInfo.findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_lastTime);
        List<AccessibilityNodeInfo> list3 = nodeInfo.findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_lastMsg);
        List<AccessibilityNodeInfo> list4 = nodeInfo.findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_msg_count);
        if(list1.size() == 0 || list2.size() == 0 || list3.size() == 0){
            throw new NodeNullException("conversation not found");
        }
        String[][] res = new String[list1.size()][3];
        for (int i = 0; i < list1.size(); i++) {
            CharSequence a1, a2, a3, a4;
            a1 = list1.get(i).getText();
            a2 = list2.get(i).getText();
            a3 = list3.get(i).getText();
            if(list4.size() > i){
                a4 = list4.get(i).getText();
            }else{
                a4 = null;
            }
            if(a1 != null && a2 != null && a3 != null){
                if(a4 == null){
                    msgSummaries.add(new MsgSummary(a1.toString(), a2.toString(), a3.toString(), "0"));
                }else{
                    msgSummaries.add(new MsgSummary(a1.toString(), a2.toString(), a3.toString(), a4.toString()));
                }
            }
        }
        return msgSummaries;
    }

    public static List<MsgSummary> getConversationList() throws NodeNullException {
        List<MsgSummary> list = new ArrayList<>();
        _getConversationList(list, null);
        return deduplication(list);
    }

    private static void _getConversationList(List<MsgSummary> list, List<MsgSummary> last) throws NodeNullException {
        if(last == null){
            last = new ArrayList<>();
            last.add(new MsgSummary("", "", "", ""));
        }
        List<MsgSummary> list1 = readChatListById();
        if(!Objects.equals(last.get(0).getTitle(), list1.get(0).getTitle())){
            list.addAll(list1);
            BasicAction.slide(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            last = list1;
            _getConversationList(list, last);
        }
    }

    private static List<MsgSummary> deduplication(List<MsgSummary> list){
        Set<MsgSummary> msgSummaries = new TreeSet<>((o1, o2)-> o1.getTitle().compareTo(o2.getTitle()));
        msgSummaries.addAll(list);
        return new ArrayList<>(msgSummaries);
    }
}
