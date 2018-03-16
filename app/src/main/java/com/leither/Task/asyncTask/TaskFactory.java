package com.leither.Task.asyncTask;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.scripts.asyncScripts.AsyncScript;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;


@SuppressWarnings("unchecked")
public class TaskFactory {
    public static Task getTask(String type, AsyncHttpServerResponse response,  String content){
        AsyncScript asyncScript;
        Task task = null;
        try {
            Class<AsyncScript> clazz = (Class<AsyncScript>)
                    Class.forName("com.leither.scripts.asyncScripts" + type);
            Constructor<AsyncScript> constructor=
                    clazz.getConstructor(AsyncHttpServerResponse.class, String.class);
            asyncScript = constructor.newInstance(response, content);
            task = new Task(1, new Date().getTime(), asyncScript);
        } catch (ClassNotFoundException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | InstantiationException e) {
            e.printStackTrace();
        }
        return task;
    }
}