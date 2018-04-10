package com.leither.Task;

import android.annotation.SuppressLint;
import com.leither.common.Action;
import com.leither.scripts.syncScripts.SyncScript;

import java.util.concurrent.LinkedBlockingDeque;

@SuppressLint({"UseSparseArrays"})
public class SyncTaskRunner
  extends Thread
{
  private volatile boolean isRunning = true;
  private LinkedBlockingDeque<SyncScript> queue = new LinkedBlockingDeque<SyncScript>(60);
  
  public void addScript(SyncScript paramSyncScript)
  {
      this.queue.offer(paramSyncScript);
  }
  
  public void run()
  {
    while (this.isRunning) {
      try
      {
        SyncScript localSyncScript = this.queue.take();
        Action.reOpenWeChat();
        localSyncScript.onComplete(localSyncScript.exec());
        Action.backToHome();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        this.queue = new LinkedBlockingDeque<>(60);
        Action.backToHome();
      }
      finally
      {
        Action.backToHome();
      }
    }
  }
  
  public void terminate()
  {
    this.isRunning = false;
    interrupt();
  }
}