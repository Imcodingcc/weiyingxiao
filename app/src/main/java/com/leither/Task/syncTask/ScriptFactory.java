package com.leither.Task.syncTask;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.scripts.syncScripts.GetAllConversation;
import com.leither.scripts.syncScripts.GetOneChatRecord;
import com.leither.scripts.syncScripts.GetRecentConversation;
import com.leither.scripts.syncScripts.GetConversationList;
import com.leither.scripts.syncScripts.GetWeChatId;
import com.leither.scripts.syncScripts.OpenConversation;
import com.leither.scripts.syncScripts.RefreshConversations;
import com.leither.scripts.syncScripts.SendMsg;
import com.leither.scripts.syncScripts.SyncScript;
import com.leither.scripts.syncScripts.WeChatId;

public class ScriptFactory {
    public static SyncScript getTask(String type, AsyncHttpServerResponse response, String content){
        SyncScript syncScript;
        switch (type){
            case "RefreshConversations":
                syncScript = new RefreshConversations(response);
                break;
            case "OpenConversation":
                syncScript = new OpenConversation(response, content);
                break;
            case "SendMsg":
                syncScript = new SendMsg(response, content);
                break;
            case "WeChatId":
                syncScript = new WeChatId(response);
                break;
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
            default:
                syncScript = new SyncScript(response);
        }
        return syncScript;
    }
}