package com.leither.Task.asyncTask;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.scripts.asyncScripts.AddFriend;
import com.leither.scripts.asyncScripts.GetFriendStatus;
import com.leither.scripts.asyncScripts.Mass;
import com.leither.scripts.asyncScripts.AsyncScript;
import com.leither.scripts.asyncScripts.SendMsg;
import com.leither.scripts.asyncScripts.TouchTest;

import java.util.Date;

public class TaskFactory {
    public static Task getTask(String type, AsyncHttpServerResponse response,  String content){
        AsyncScript asyncScript;
        Task task = null;
        switch (type){
            case "Mass":
                asyncScript = new Mass(response, content);
                task = new Task(1, new Date().getTime(), asyncScript);
                break;
            case "AddFriend":
                asyncScript = new AddFriend(response, content);
                task = new Task(1, new Date().getTime(), asyncScript);
                break;
            case "SendMsg":
                asyncScript = new SendMsg(response, content);
                task = new Task(1, new Date().getTime(), asyncScript);
                break;
            case "GetFriendStatus":
                asyncScript = new GetFriendStatus(response, content);
                task = new Task(-1, new Date().getTime(), asyncScript);
                break;
            case "TouchTest":
                asyncScript = new TouchTest(response, content);
                task = new Task(-1, new Date().getTime(), asyncScript);
                break;
            default:
                Log.d("TaskFactory", type + "No such task");
        }
        return task;
    }
}