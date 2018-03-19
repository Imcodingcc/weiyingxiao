package com.leither.common;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;

import com.leither.common.Global;
import com.leither.exception.NodeNullException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

public class Action {
    @SuppressLint("StaticFieldLeak")
    private static AccessibilityService accessibilityService = Global.getDefault().getAccessibilityService();
    public static void Click(String name, int index) throws Exception{
        AccessibilityNodeInfo clickAble = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = clickAble
                .findAccessibilityNodeInfosByText(name);
        if(list.size() <= index){
           throw new NodeNullException(name + " node not found");
        }
        clickAble = list.get(index);
        while(clickAble != null){
            if(clickAble.isClickable()){
                clickAble.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Thread.sleep(1000);
            }
            clickAble = clickAble.getParent();
        }
    }

    public static void ClickById(String id, int index) throws Exception{
        AccessibilityNodeInfo clickAble = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = clickAble
                .findAccessibilityNodeInfosByViewId(id);
        if(list.size() <= index){
            throw new NodeNullException(id + " node not found");
        }
        clickAble = list.get(index);
        while(clickAble != null){
            if(clickAble.isClickable()){
                clickAble.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Thread.sleep(1000);
            }
            clickAble = clickAble.getParent();
        }
    }

    public static void RootClick(String text) throws NodeNullException, IOException, InterruptedException {
        List<AccessibilityNodeInfo> list = accessibilityService.getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
        if(list.size() == 0) throw new NodeNullException(text + " node not found");
        Rect rect = new Rect();
        list.get(0).getBoundsInScreen(rect);
        Global.getDefault().getNexus5().tap(rect);
        Thread.sleep(1000);
    }

    public static void DoubleClickById(String id, int index) throws Exception{
        AccessibilityNodeInfo clickAble = accessibilityService.getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = clickAble
                .findAccessibilityNodeInfosByViewId(id);
        if(list.size() <= index){
            throw new NodeNullException(id + " node not found");
        }
        clickAble = list.get(index);
        while(clickAble != null){
            if(clickAble.isClickable()){
                clickAble.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                clickAble.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
            clickAble = clickAble.getParent();
        }
    }

    public static boolean findAndInput(String content, AccessibilityNodeInfo rootNode) throws Exception{
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
            if(findAndInput(content, nodeInfo)){
                Thread.sleep(500);
               return true;
            }
        }
        return false;
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

    private static void openWeChat() throws Exception{
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        accessibilityService.getApplication().startActivity(intent);
        Thread.sleep(2000);
    }

    public static void reOpenWeChat() throws Exception{
        Global.getDefault().getNexus5().back(5);
        openWeChat();
    }

    public static void backToHome(){
        try {
            Global.getDefault().getNexus5().back(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void findByClassName(List<AccessibilityNodeInfo> nodeInfos, AccessibilityNodeInfo rootNode, String className){
        int count = rootNode.getChildCount();
        for(int i = 0; i<count; i++){
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if(nodeInfo != null){
                if(className.equals(nodeInfo.getClassName())){
                    nodeInfos.add(nodeInfo);
                }
                findByClassName(nodeInfos, nodeInfo, className);
            }
        }
    }
}
