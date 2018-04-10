package com.leither.Task;

import com.leither.scripts.asyncScripts.AsyncScript;
import com.leither.scripts.asyncScripts.RefreshMsg;
import java.util.Date;

public class RefreshDetailChatMsgRunner
  extends Thread
{
  private AsyncTaskRunner asyncTaskRunner;
  
  public RefreshDetailChatMsgRunner(AsyncTaskRunner paramAsyncTaskRunner)
  {
    this.asyncTaskRunner = paramAsyncTaskRunner;
  }
  
  public void run()
  {
    try
    {
      for (;;)
      {
        Object localObject = new RefreshMsg();
        localObject = new Task(1, new Date().getTime(), (AsyncScript)localObject);
        this.asyncTaskRunner.addTask((Task)localObject);
        Thread.sleep(20000L);
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }
}