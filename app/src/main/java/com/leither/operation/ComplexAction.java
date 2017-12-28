package com.leither.operation;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

import com.leither.exception.NodeNullException;
import com.leither.share.Global;
import com.leither.weChatVersion.WeChatResourceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComplexAction{
    @SuppressLint("StaticFieldLeak")
    private static AccessibilityService accessibilityService = Global.getDefault().getAccessibilityService();
    private static WeChatResourceId weChatResourceId = new WeChatResourceId("6.5.23");

    public static String[] readOneRecentChat() throws NodeNullException {
        AccessibilityNodeInfo nodeInfo = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = new ArrayList<>();
        BasicAction.findByClassName(list, nodeInfo, "android.widget.ListView");
        if(list.size() == 0){
            throw new NodeNullException("ListView node not found");
        }
        List<AccessibilityNodeInfo> texts = new ArrayList<>();
        BasicAction.findByClassName(texts, list.get(0), "android.widget.TextView");
        if(texts.size() == 0){
            throw new NodeNullException("TextView node not found");
        }
        String[] contents = new String[texts.size()];
        for (int i = 0; i < texts.size(); i++) {
            contents[i] = texts.get(i).getText().toString();
        }
        return contents;
    }

    public static String[][] readChatList() throws NodeNullException {
        List<AccessibilityNodeInfo> list = new ArrayList<>();
        BasicAction.findByClassName(list, accessibilityService.getRootInActiveWindow(), "android.widget.ListView");
        if(list.size() == 0){
            throw new NodeNullException("ListView node not found");
        }
        AccessibilityNodeInfo fNode =  null;
        for (AccessibilityNodeInfo nodeInfo : list) {
            if(nodeInfo.isFocused()){
                fNode = nodeInfo;
            }
        }
        if(fNode == null){
            throw new NodeNullException(" Focused ListView node not found");
        }
        List<AccessibilityNodeInfo> texts = new ArrayList<>();
        BasicAction.findByClassName(texts, fNode, "android.view.View");
        if(texts.size() == 0){
            throw new NodeNullException("View node not found");
        }
        int x = 0;
        String[] tmp = new String[3];
        String[][] contents = new String[texts.size() / 3][3];
        for (int i = 0; i < texts.size(); i+=3) {
            tmp[i] = texts.get(i).getText().toString();
            tmp[i + 1] = texts.get(i + 1).getText().toString();
            tmp[i + 2] = texts.get(i + 2).getText().toString();
            contents[x] = tmp;
            x += 1;
        }
        return contents;
    }

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

    public static List<MsgText> getConversationContent(){
        AccessibilityNodeInfo nodeInfo = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list1 = nodeInfo.findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_content);
        List<AccessibilityNodeInfo> list2 = nodeInfo.findAccessibilityNodeInfosByViewId(weChatResourceId.weChat_conversation_time);
        List<MsgText> list22 = new ArrayList<>();
        List<MsgText> list11 = new ArrayList<>();
        for (AccessibilityNodeInfo info : list2) {
            Rect rect = new Rect();
            info.getBoundsInScreen(rect);
            list22.add(new MsgText("time", info.getText().toString(), rect.bottom));
        }
        for (AccessibilityNodeInfo info : list1) {
            info.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
            //TODO
            String content = Global.getDefault().getClipboardManager().getText().toString();
            Rect rect = new Rect();
            info.getBoundsInScreen(rect);
            list11.add(new MsgText("data", content, rect.bottom));
        }
        list11.addAll(list22);
        return list11;
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
        int x = 0,y = 0;
        anchor:
        for (int i = list.size() - 1; i >= 0; i--) {
            for (int i1 = list.size() - 1; i1 >= 0; i1--) {
                if(list.get(i).getTitle().equals(list.get(i1).getTitle()) && i != i1){
                    x = i;
                    y = i1 + 1;
                    break anchor;
                }
            }
        }
        while(x != y - 1){
            list.remove(x);
            x--;
        }
        return list;
    }
}
