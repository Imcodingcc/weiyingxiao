package com.leither.Task;

import android.util.Log;

import com.leither.scripts.Mass;
import com.leither.scripts.Script;

import java.util.Date;

public class TaskFactory {
    public static Task getTask(String type, String content){
        Script script = null;
        Task task = null;
        switch (type){
            case "mass":
                script = new Mass(content);
                task = new Task(new Date().getTime(), script);
                break;
            default:
                Log.d("TaskFactory", type + "No such task");
        }
        return task;
    }
}
