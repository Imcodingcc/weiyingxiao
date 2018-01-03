package com.leither.operation;

import android.graphics.Rect;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RootedAction {
    private Process su;
    private DataOutputStream outputStream;
    public RootedAction(){
    }

    public void execCommand(String command) throws IOException, InterruptedException {
        su = Runtime.getRuntime().exec("su");
        outputStream = new DataOutputStream(su.getOutputStream());
        outputStream.writeBytes(command);
        outputStream.flush();
        outputStream.writeBytes("exit\n");
        outputStream.flush();
        outputStream.close();
        su.waitFor();
        su.destroy();
    }

    public void tap(Rect rect) throws IOException, InterruptedException {
        int left = rect.left + 1;
        int top = rect.top + 1;
        String action = "input tap " + left + " " + top + "\n";
        execCommand(action);
    }

    public void longPress(Rect rect) throws IOException, InterruptedException {
        int left = rect.left + 1;
        int top = rect.top + 1;
        String action = "input touchscreen swipe " + left + " " + top + " " + left + " " + top + " " + 1500 + "\n";
        execCommand(action);
    }
}
