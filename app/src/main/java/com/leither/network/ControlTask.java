package com.leither.network;

import android.util.Log;

import com.leither.Task.AsyncTaskRunner;

class ControlTask {

    private AsyncTaskRunner asyncTaskRunner;
    ControlTask(AsyncTaskRunner asyncTaskRunner){
        this.asyncTaskRunner = asyncTaskRunner;
    }

    String deal(String opt, long id){
        Log.d("remove", "removeWork: "  + opt);
        switch (opt){
            case "PauseWork":
                asyncTaskRunner.pauseWork(id);
                break;
            case "GetExecutingResult":
                return asyncTaskRunner.getExecutingResult(id);
            case "ProceedWork":
                asyncTaskRunner.proceedWork(id);
                break;
            case "RemoveWork":
                asyncTaskRunner.removeWork(id);
                break;
        }
        return "";
    }
}