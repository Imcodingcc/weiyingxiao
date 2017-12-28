package com.leither.operation;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;

import com.leither.share.Global;
import com.leither.exception.NodeNullException;

import java.util.ArrayList;
import java.util.List;

public class BasicAction {
    @SuppressLint("StaticFieldLeak")
    private static AccessibilityService accessibilityService = Global.getDefault().getAccessibilityService();
    public static void Click(String name) throws NodeNullException {
        AccessibilityNodeInfo clickAble = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = clickAble
                .findAccessibilityNodeInfosByText(name);
        if(list.size() == 0){
           throw new NodeNullException(name + " node not found");
        }
        clickAble = list.get(0);
        while(clickAble != null){
            if(clickAble.isClickable()){
                clickAble.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            clickAble = clickAble.getParent();
        }
    }

    public static void ClickById(String id) throws NodeNullException {
        AccessibilityNodeInfo clickAble = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = clickAble
                .findAccessibilityNodeInfosByViewId(id);
        if(list.size() == 0){
            throw new NodeNullException(id + " node not found");
        }
        clickAble = list.get(0);
        while(clickAble != null){
            if(clickAble.isClickable()){
                clickAble.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            clickAble = clickAble.getParent();
        }
    }

    public static void DoubleClickById(String id) throws NodeNullException {
        AccessibilityNodeInfo clickAble = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = clickAble
                .findAccessibilityNodeInfosByViewId(id);
        if(list.size() == 0){
            throw new NodeNullException(id + " node not found");
        }
        clickAble = list.get(0);
        while(clickAble != null){
            if(clickAble.isClickable()){
                clickAble.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                clickAble.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            clickAble = clickAble.getParent();
        }
    }

    public static boolean input(String content, AccessibilityNodeInfo rootNode) throws NodeNullException {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if ("android.widget.EditText".equals(nodeInfo.getClassName())) {
                Bundle arguments = new Bundle();
                arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                        AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
                arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN,
                        true);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY,
                        arguments);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                ClipData clip = ClipData.newPlainText("label", content);
                ClipboardManager clipboardManager = Global.getDefault().getClipboardManager();
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                return true;
            }
            if(input(content, nodeInfo)){
               return true;
            }
        }
        return false;
    }

    public static void findByClassName(List<AccessibilityNodeInfo> nodeInfos, AccessibilityNodeInfo rootNode, String className){
        int count = rootNode.getChildCount();
        for(int i = 0; i<count; i++){
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if(className.equals(nodeInfo.getClassName())){
                nodeInfos.add(nodeInfo);
            }
            findByClassName(nodeInfos, nodeInfo, className);
        }
    }

    public static void back(int times){
        for (int i = 0; i < times; i++) {
           accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void slide(int orientation) throws NodeNullException {
        AccessibilityNodeInfo nodeInfo = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> nodeInfos = new ArrayList<>();
        findByClassName(nodeInfos, nodeInfo, "android.widget.ListView");
        if(nodeInfos.size() != 0){
            nodeInfos.get(0).performAction(orientation);
        }else{
            throw new NodeNullException("ListView node not found");
        }
    }

    public static void openWechat(){
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        accessibilityService.getApplication().startActivity(intent);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
