package com.leither.scripts.asyncScripts;

import com.leither.operation.BasicAction;
import com.leither.share.Global;

public class AddOne implements AsyncScript{
    String[] steps = new String[]{"更多功能", "添加朋友"};
    String[] steps2 = new String []{"搜索", "添加到通讯录"};
    private Global g = Global.getDefault();
    private String phoneNum;

    public AddOne(String phoneNum){
        this.phoneNum = phoneNum;
    }
    @Override
    public String start() throws Exception{
        BasicAction.reOpenWeChat();
        for (String step : steps) {
            BasicAction.Click(step, 0);
        }
        BasicAction.RootClick("微信号/QQ号/手机号");
        BasicAction.input(phoneNum, g.getAccessibilityService().getRootInActiveWindow());
        for (String step : steps2) {
            BasicAction.Click(step, 0);
            Thread.sleep(1000);
        }
        BasicAction.Click("发送", 1);
        BasicAction.reOpenWeChat();
        return "ok";
    }

    @Override
    public void onComplete() {

    }
}
