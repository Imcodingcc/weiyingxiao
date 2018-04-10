package com.leither.Task;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.scripts.syncScripts.SyncScript;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ScriptFactory
{
  public static SyncScript getTask(String paramString1, AsyncHttpServerResponse paramAsyncHttpServerResponse, String paramString2)
  {
    SyncScript syncScript = null;
    try {
      syncScript = (SyncScript)Class.forName("com.leither.scripts.syncScripts." + paramString1).getConstructor(new Class[] { AsyncHttpServerResponse.class, String.class }).newInstance(paramAsyncHttpServerResponse, paramString2);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return syncScript;
  }
}