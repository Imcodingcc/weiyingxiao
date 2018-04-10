package com.leither.Task;

import android.annotation.SuppressLint;
import com.leither.common.Action;
import com.leither.common.Tools;
import com.leither.network.SendInfoToServer;
import com.leither.scripts.asyncScripts.AsyncScript;
import java.util.Hashtable;
import java.util.concurrent.PriorityBlockingQueue;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"UseSparseArrays"})
public class AsyncTaskRunner
  extends Thread
{
  private Hashtable<Long, String> completedWork = new Hashtable<>();
  private Hashtable<Long, Task> copyOfquee = new Hashtable<>();
  private volatile boolean isRunning = true;
  private PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<Task>(10);
  
  public AsyncTaskRunner(String paramString)
  {
    super(paramString);
  }
  
  private void completeAndUp(long paramLong, String paramString)
  {
    this.completedWork.put(paramLong, paramString);
    JSONObject localJSONObject = new JSONObject();
    try {
      localJSONObject.put("ip", Tools.getLocalHostLANAddress()).put("mac", Tools.getWifiMac()).put("model", Tools.getDeviceName()).put("taskId", paramLong).put("status", paramString);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    SendInfoToServer.getDefault().execute("taskStatus", localJSONObject);
  }
  
  public void addTask(Task paramTask)
  {
      this.queue.offer(paramTask);
      this.copyOfquee.put(paramTask.getId(), paramTask);
  }
  
  public String getExecutingResult(long paramLong)
  {
      return completedWork.get(paramLong);
  }
  
  public void pauseWork(long paramLong)
  {
      queue.remove(copyOfquee.get(paramLong));
  }
  
  public void proceedWork(long paramLong)
  {
      queue.offer(copyOfquee.get(paramLong));
  }
  
  public void removeWork(long paramLong)
  {
      queue.remove(copyOfquee.remove(paramLong));
  }
  
  public void run()
  {
    while (this.isRunning)
    {
      long l3 = 0L;
      long l1 = l3;
      long l2 = l3;
      try
      {
        Object localObject1 = this.queue.take();
        l1 = l3;
        l2 = l3;
        l3 = ((Task)localObject1).getId();
        l1 = l3;
        l2 = l3;
        localObject1 = ((Task)localObject1).getAsyncScript();
        l1 = l3;
        l2 = l3;
        Action.reOpenWeChat();
        l1 = l3;
        l2 = l3;
        completeAndUp(l3, ((AsyncScript)localObject1).start());
        this.copyOfquee.remove(l3);
        Action.backToHome();
      }
      catch (Exception localException)
      {
        l2 = l1;
        completeAndUp(l1, localException.getMessage());
        l2 = l1;
        localException.printStackTrace();
        this.copyOfquee.remove(l1);
        Action.backToHome();
      }
      finally
      {
        this.copyOfquee.remove(l2);
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