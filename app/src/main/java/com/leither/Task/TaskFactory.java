package com.leither.Task;

import android.os.AsyncTask;
import android.util.Log;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.scripts.asyncScripts.AsyncScript;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class TaskFactory
{
  public static Task getTask(String paramString1, AsyncHttpServerResponse paramAsyncHttpServerResponse, String paramString2) {
    Log.d("TaskFactory", "getTask: " + paramString1);
    AsyncScript asyncScript = null;
    try {
      asyncScript = (AsyncScript) Class.forName("com.leither.scripts.asyncScripts." + paramString1).getConstructor(new Class[]{AsyncHttpServerResponse.class, String.class}).newInstance(paramAsyncHttpServerResponse, paramString2);
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return new Task(1, new Date().getTime(), asyncScript);
  }
}