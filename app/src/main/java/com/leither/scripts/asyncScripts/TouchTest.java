package com.leither.scripts.asyncScripts;

import android.util.Log;

import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.leither.share.Global;

import org.json.JSONException;
import org.json.JSONObject;

public class TouchTest extends AsyncScript{
    private static final String TAG = TouchTest.class.getName();
    private Global g = Global.getDefault();
    private int x, y = 0;

    public TouchTest(AsyncHttpServerResponse response, String param){
        super(response);
        try {
            JSONObject jsonObject = new JSONObject(param);
            x = jsonObject.getInt("x");
            y = jsonObject.getInt("y");
            Log.d(TAG, "TouchTest: " + x + " " + y);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String start() throws Exception{
        g.getNexus5().touch(0 ,x, y);
        g.getNexus5().release(0);
        return "ok";
    }
}