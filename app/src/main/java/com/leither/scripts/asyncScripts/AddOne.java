package com.leither.scripts.asyncScripts;

import android.util.Log;

import com.leither.operation.BasicAction;
import com.leither.share.Global;

import org.json.JSONException;
import org.json.JSONObject;

public class AddOne implements AsyncScript{
    private static final String TAG = AddOne.class.getName();
    String[] steps = new String[]{"更多功能", "添加朋友"};
    String[] steps2 = new String []{"搜索", "添加到通讯录"};
    private Global g = Global.getDefault();
    private String remarks;
    private String phoneNum;
    private String requestFirst;

    public AddOne(String param){
        try {
            JSONObject jsonObject = new JSONObject(param);
            remarks = jsonObject.getString("remarks");
            phoneNum = jsonObject.getString("phoneNum");
            requestFirst = jsonObject.getString("requestFirst");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String start() throws Exception{
        BasicAction.reOpenWeChat();
        for (String step : steps) {
            BasicAction.Click(step, 0);
        }
        Global.getDefault().getAddOneStatus().put(phoneNum, "1");
        BasicAction.RootClick("微信号/QQ号/手机号");
        BasicAction.input(phoneNum, g.getAccessibilityService().getRootInActiveWindow());
        for (String step : steps2) {
            BasicAction.Click(step, 0);
            Thread.sleep(1000);
        }
        BasicAction.Click("发送", 1);
        Global.getDefault().getAddOneStatus().put(phoneNum, "2");
        BasicAction.reOpenWeChat();
        return "ok";
    }

    @Override
    public void onComplete(Exception e, String result) {
        if (e == null) {
            Log.d(TAG, "onComplete: " + result);
        }else{
            Global.getDefault().getAddOneStatus().put(phoneNum, "-1");
            Log.d(TAG, "onComplete: " + phoneNum + "添加失败");
        }
    }
}
