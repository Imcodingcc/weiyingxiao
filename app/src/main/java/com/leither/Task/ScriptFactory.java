package com.leither.Task;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.scripts.GetConversationContent;
import com.leither.scripts.OpenConversation;
import com.leither.scripts.RefreshConversations;
import com.leither.scripts.SendMsg;
import com.leither.scripts.SyncScript;

public class ScriptFactory {
    public static SyncScript getTask(String type, AsyncHttpServerResponse response, String content){
        SyncScript syncScript = null;
        switch (type){
            case "RefreshConversations":
                syncScript = new RefreshConversations(response, content);
                break;
            case "GetConversationContent":
                syncScript = new GetConversationContent(response, content);
                break;
            case "OpenConversation":
                syncScript = new OpenConversation(response, content);
                break;
            case "SendMsg":
                syncScript = new SendMsg(response, content);
                break;
            default:
                Log.d("ScriptFactory", type + "No such script");
        }
        return syncScript;
    }
}
