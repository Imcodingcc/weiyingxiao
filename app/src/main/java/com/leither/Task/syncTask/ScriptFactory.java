package com.leither.Task.syncTask;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.scripts.syncScripts.SyncScript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unchecked")
public class ScriptFactory {
    public static SyncScript getTask(String type, AsyncHttpServerResponse response, String content){
        SyncScript syncScript = null;
        try {
            Class<SyncScript> clazz = (Class<SyncScript>)
                    Class.forName("com.leither.scripts.syncScripts" + type);
            Constructor<SyncScript> constructor=
                    clazz.getConstructor(AsyncHttpServerResponse.class, String.class);
            syncScript = constructor.newInstance(response, content);
        } catch (ClassNotFoundException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | InstantiationException e) {
            e.printStackTrace();
        }
        return syncScript;
    }
}