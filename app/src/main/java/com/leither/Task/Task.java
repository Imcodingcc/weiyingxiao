package com.leither.Task;

import android.support.annotation.NonNull;
import com.leither.scripts.asyncScripts.AsyncScript;

public class Task
  implements Comparable<Task>
{
  private AsyncScript asyncScript;
  private long id;
  private int nice;
  
  Task(int paramInt, long paramLong, AsyncScript paramAsyncScript)
  {
    this.id = paramLong;
    this.asyncScript = paramAsyncScript;
    this.nice = paramInt;
  }
  
  public int compareTo(@NonNull Task paramTask)
  {
    if (this.nice > paramTask.nice) {
      return 1;
    }
    if (this.nice == paramTask.nice) {
      return 0;
    }
    return -1;
  }
  
  AsyncScript getAsyncScript()
  {
    return this.asyncScript;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long paramLong)
  {
    this.id = paramLong;
  }
}