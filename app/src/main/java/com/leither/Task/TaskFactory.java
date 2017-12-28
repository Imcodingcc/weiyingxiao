package com.leither.Task;

import android.util.Log;

import com.leither.scripts.OpenConversation;
import com.leither.scripts.RefreshConversations;
import com.leither.scripts.Mass;
import com.leither.scripts.AsyncScript;
import com.leither.scripts.SendMsg;

import java.util.Date;

public class TaskFactory {
    public static Task getTask(String type, String content){
        AsyncScript asyncScript = null;
        Task task = null;
        switch (type){
            case "Mass":
                asyncScript = new Mass(content);
                task = new Task(new Date().getTime(), asyncScript);
                break;
            default:
                Log.d("TaskFactory", type + "No such task");
        }
        return task;
    }
}
