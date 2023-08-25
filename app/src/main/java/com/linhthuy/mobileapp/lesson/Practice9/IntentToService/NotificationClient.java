package com.linhthuy.mobileapp.lesson.Practice9.IntentToService;


import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linhthuy.mobileapp.R;

public class NotificationClient extends AppCompatActivity {

    Button createNotifyServiceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson9_client);
        createNotifyServiceButton = findViewById(R.id.CreateNotificationService);
        createNotifyServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceIntent = new Intent(NotificationClient.this, NotificationService.class);
                startService(serviceIntent);
            }
        });
    }
}