package com.leither.scripts.asyncScripts;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.operation.BasicAction;
import com.leither.common.Global;

import org.json.JSONException;
import org.json.JSONObject;

public class AddFriend extends AsyncScript{
    private static final String TAG = AddFriend.class.getName();
    String[] steps = new String[]{"更多功能", "添加朋友"};
    String[] steps2 = new String []{"搜索", "添加到通讯录"};
    private Global g = Global.getDefault();
    private String remarks;
    private String tel;
    private String requestFirst;

    public AddFriend(AsyncHttpServerResponse response, String param){
        super(response);
        try {
            Log.d(TAG, "AddFriend: init " + param);
            JSONObject jsonObject = new JSONObject(param);
            remarks = jsonObject.getString("remarks");
            tel = jsonObject.getString("tel");
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
        Global.getDefault().getAddOneStatus().put(tel, "1");
        BasicAction.RootClick("微信号/QQ号/手机号");
        BasicAction.findAndInput(tel, g.getAccessibilityService().getRootInActiveWindow());
        for (String step : steps2) {
            BasicAction.Click(step, 0);
            Thread.sleep(1000);
        }
        BasicAction.Click("发送", 1);
        Global.getDefault().getAddOneStatus().put(tel, "2");
        BasicAction.reOpenWeChat();
        return "0";
    }
}