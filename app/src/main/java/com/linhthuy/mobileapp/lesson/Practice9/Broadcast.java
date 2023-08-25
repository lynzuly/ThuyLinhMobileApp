package com.linhthuy.mobileapp.lesson.Practice9;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linhthuy.mobileapp.R;

public class Broadcast extends AppCompatActivity {
    Button sendBroadcastButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson9_broadcast);

        sendBroadcastButton = findViewById(R.id.SendBroadcast);
        sendBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendBroadcast();
            }
        });
        IntentFilter intentFilter = new IntentFilter("com.linhthuy.mobileapp.ACTION_BROADCAST_TEST");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Broadcast received: " + intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
            }
        };
        registerReceiver(receiver, intentFilter);
    }

    private void SendBroadcast() {
        Intent intent = new Intent("com.linhthuy.mobileapp.ACTION_BROADCAST_TEST");
        intent.putExtra("message", "Hello, this is a broadcast event!");
        sendBroadcast(intent);
    }
}