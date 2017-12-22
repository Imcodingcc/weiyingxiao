package com.leither.httpServer;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.leither.Task.Task;
import com.leither.Task.TaskFactory;
import com.leither.Task.TaskRunner;

public class HttpServer implements Server{
    private String[] httpInterface = new String[]{"mass", "addOne", "batchAdd", "chat"};

    private TaskRunner taskRunner;

    HttpServer(){
        taskRunner = new TaskRunner();
        taskRunner.start();
    }

    @Override
    public void setListener(AsyncHttpServer server) {
        for (final String s : httpInterface) {
            server.post("/" + s, new HttpServerRequestCallback() {
                @Override
                public void onRequest(final AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                    response.send("ok");
                    Task task = TaskFactory.getTask(s, "hello");
                    taskRunner.addTask(task);
                }
            });
        }
    }
}
