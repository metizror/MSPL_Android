package com.rn.android.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = NetworkUtil.getConnectivityStatusString(context);
        Log.e("status:", "onReceive: " + status);
        if (!status) {
            PreferenceHelper.putString("CHECK_NETWORK_STATUS","false");
            Utils.buildDialog(context);
        } else {
            Intent intent1 = new Intent("CHECK_NETWORK_STATUS");
            intent1.putExtra("status",""+status);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
            Utils.dismissDialog();
        }
    }
}
