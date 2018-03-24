package cn.leither.touchlibiary;

import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class Nexus5 {
    private String TAG = "Nexus5";

    public void sendEvent(String opt) {
        try {
            Socket socket = new Socket("localhost", 10151);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(opt.getBytes());
            out.close();
            input.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "touch: " + "client error");
        }
    }

    public void input(String text) {
        executeCommand("am broadcast -a ADB_INPUT_TEXT --es msg " + text);
    }

    public void tap(Rect rect) throws IOException, InterruptedException {
        executeCommand("input tap " + rect.left + " " + rect.top + "\n");
    }

    public void longPress(Rect rect) throws IOException, InterruptedException {
        //TODO the code has not been tested yet
        executeCommand("input touchscreen swipe "
                + rect.left + " " + rect.top + " "
                + rect.left + " " + rect.top + " " + 1500 + "\n");
    }

    public void back(int times) throws InterruptedException {
        for (int i = 0; i < times; i++) {
            executeCommand("input keyevent " + KeyEvent.KEYCODE_BACK);
            Thread.sleep(500);
        }
    }

    public void switchToAdbIME() {
        executeCommand("ime set com.dylan_wang.capturescreen/com.leither.service.AdbIMEService");
    }

    public void executeCommand(String command) {
        CommandCapture cmd = new CommandCapture(0, command);
        try {
            RootTools.getShell(true).add(cmd);
        } catch (IOException | TimeoutException | RootDeniedException e) {
            e.printStackTrace();
        }
    }
}