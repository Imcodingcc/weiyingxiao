package com.leither.scripts.asyncScripts;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.common.Action;
import com.leither.common.Global;

import org.json.JSONException;
import org.json.JSONObject;

public class AddFriend extends AsyncScript {
    private static final String TAG = AddFriend.class.getName();
    String[] steps = new String[]{"更多功能", "添加朋友"};
    String[] steps2 = new String[]{"搜索", "添加到通讯录"};
    private Global g = Global.getDefault();
    private String remarks;
    private String tel;
    private String requestFirst;

    public AddFriend(AsyncHttpServerResponse response, String param) throws JSONException {
        super(response);
        Log.d(TAG, "AddFriend: init " + param);
        JSONObject jsonObject = new JSONObject(param);
        remarks = jsonObject.getString("remarks");
        tel = jsonObject.getString("tel");
        requestFirst = jsonObject.getString("requestFirst");
    }

    @Override
    public String start() throws Exception {
        for (String step : steps) {
            Action.Click(step, 0);
        }
        Global.getDefault().getAddOneStatus().put(tel, "1");
        Action.RootClick("微信号/QQ号/手机号");
        Action.findAndInput(tel, g.getAccessibilityService().getRootInActiveWindow());
        for (String step : steps2) {
            Action.Click(step, 0);
            Thread.sleep(1000);
        }
        Action.Click("发送", 1);
        Global.getDefault().getAddOneStatus().put(tel, "2");
        return "0";
    }
}