package com.leither.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.dylan_wang.capturescreen.R;
import com.koushikdutta.async.http.WebSocket;
import com.leither.share.Global;

public class AdbIME extends InputMethodService {
    private String IME_MESSAGE = "ADB_INPUT_TEXT";
    private String IME_CHARS = "ADB_INPUT_CHARS";
    private String IME_KEYCODE = "ADB_INPUT_CODE";
    private String IME_EDITORCODE = "ADB_EDITOR_CODE";
    private static final String TAG = AdbIME.class.getName();
    private BroadcastReceiver mReceiver = null;

    @Override 
    public View onCreateInputView() {
		View mInputView = getLayoutInflater().inflate(R.layout.input_method, null);

        if (mReceiver == null) {
        	IntentFilter filter = new IntentFilter(IME_MESSAGE);
        	filter.addAction(IME_CHARS);
        	filter.addAction(IME_KEYCODE);
        	filter.addAction(IME_EDITORCODE);
        	mReceiver = new AdbReceiver();
        	registerReceiver(mReceiver, filter);
        }

        return mInputView; 
    }

	@Override
	public void onStartInput(EditorInfo attribute, boolean restarting) {
		super.onStartInput(attribute, restarting);
		Log.d(TAG, "onStartInput: ");
        for (WebSocket webSocket : Global.getDefault().getWebSocketList()) {
            webSocket.send("inputMethodOn");
        }
    }

	public void onDestroy() {
    	if (mReceiver != null)
    		unregisterReceiver(mReceiver);
    	super.onDestroy();    	
    }
    
    class AdbReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(IME_MESSAGE)) {
				String msg = intent.getStringExtra("msg");				
				if (msg != null) {
					InputConnection ic = getCurrentInputConnection();
					if (ic != null)
						ic.commitText(msg, 1);
				}
			}
			
			if (intent.getAction().equals(IME_CHARS)) {
				int[] chars = intent.getIntArrayExtra("chars");				
				if (chars != null) {					
					String msg = new String(chars, 0, chars.length);
					InputConnection ic = getCurrentInputConnection();
					if (ic != null)
						ic.commitText(msg, 1);
				}
			}
			
			if (intent.getAction().equals(IME_KEYCODE)) {				
				int code = intent.getIntExtra("code", -1);				
				if (code != -1) {
					InputConnection ic = getCurrentInputConnection();
					if (ic != null)
						ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, code));
				}
			}
			
			if (intent.getAction().equals(IME_EDITORCODE)) {				
				int code = intent.getIntExtra("code", -1);				
				if (code != -1) {
					InputConnection ic = getCurrentInputConnection();
					if (ic != null)
						ic.performEditorAction(code);
				}
			}
		}
    }
}
