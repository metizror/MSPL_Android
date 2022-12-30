package com.rn.android.FCM;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;
import org.json.JSONObject;

import java.util.Set;

public class FirebaseDataRecevier extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            JSONObject json = new JSONObject();
            Set<String> keys = bundle.keySet();
            for (String key : keys) {
                try {
                    json.put(key, JSONObject.wrap(bundle.get(key)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.e("TAG","onMessageReceived---Recevier--" + json.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
