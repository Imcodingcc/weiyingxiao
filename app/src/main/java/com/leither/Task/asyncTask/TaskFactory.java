package com.leither.Task.asyncTask;

import android.util.Log;

import com.leither.scripts.asyncScripts.AddOne;
import com.leither.scripts.asyncScripts.Mass;
import com.leither.scripts.asyncScripts.AsyncScript;
import com.leither.scripts.asyncScripts.SendMsg;

import java.util.Date;

public class TaskFactory {
    public static Task getTask(String type, String content){
        AsyncScript asyncScript;
        Task task = null;
        switch (type){
            case "Mass":
                asyncScript = new Mass(content);
                task = new Task(new Date().getTime(), asyncScript);
                break;
            case "AddOne":
                asyncScript = new AddOne(content);
                task = new Task(new Date().getTime(), asyncScript);
                break;
            case "SendMsg":
                asyncScript = new SendMsg(content);
                task = new Task(new Date().getTime(), asyncScript);
                break;
            default:
                Log.d("TaskFactory", type + "No such task");
        }
        return task;
    }
}