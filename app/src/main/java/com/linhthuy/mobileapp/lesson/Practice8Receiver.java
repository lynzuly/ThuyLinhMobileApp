package com.linhthuy.mobileapp.lesson;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class Practice8Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.d("LINH_RECEIVER","" + intent.getAction());

        String action = intent.getAction();
        if (action.equals("com.linh.CUSTOM_ACTION")) {
            String message = intent.getStringExtra("broadcastMessage");
            Toast.makeText(context, "CUSTOM_ACTION: " + message, Toast.LENGTH_LONG).show();
        } else if (action.equals("android.intent.action.AIRPLANE_MODE")) {
            boolean isOn = intent.getBooleanExtra("state", false);
            Toast.makeText(context, "AIRPLANE_MODE changed: " + (isOn ? "ON" : "OFF"), Toast.LENGTH_SHORT).show();
        }
    }
}