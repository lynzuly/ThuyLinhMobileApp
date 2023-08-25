package com.linhthuy.mobileapp.lesson.Practice12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            Toast.makeText(context, "Broadcast: Network changed!", Toast.LENGTH_LONG).show();
        } else if (action.equals("android.net.conn.wifi.WIFI_STATE_CHANGED_ACTION")) {
            Toast.makeText(context, "Broadcast: Wifi changed!", Toast.LENGTH_LONG).show();
        }
    }
}