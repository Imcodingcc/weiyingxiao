package com.leither.Task.syncTask;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.scripts.asyncScripts.GetFriendStatus;
import com.leither.scripts.syncScripts.GetAddOneStatus;
import com.leither.scripts.syncScripts.GetAllConversation;
import com.leither.scripts.syncScripts.GetOneChatRecord;
import com.leither.scripts.syncScripts.GetRecentConversation;
import com.leither.scripts.syncScripts.GetConversationList;
import com.leither.scripts.syncScripts.GetWeChatId;
import com.leither.scripts.syncScripts.SyncScript;

public class ScriptFactory {
    public static SyncScript getTask(String type, AsyncHttpServerResponse response, String content){
        SyncScript syncScript;
        switch (type){
            case "GetConversationList":
                syncScript = new GetConversationList(response);
                break;
            case "GetWeChatId":
                syncScript = new GetWeChatId(response);
                break;
            case "GetRecentConversation":
                syncScript = new GetRecentConversation(response);
                break;
            case "GetAllConversation":
                syncScript = new GetAllConversation(response);
                break;
            case "GetOneChatRecord":
                syncScript = new GetOneChatRecord(response, content);
                break;
            case "GetAddOneStatus":
                syncScript = new GetAddOneStatus(response, content);
                break;
            default:
                syncScript = new SyncScript(response);
        }
        return syncScript;
    }
}