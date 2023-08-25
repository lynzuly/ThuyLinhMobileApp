package com.linhthuy.mobileapp.lesson.Practice12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.linhthuy.mobileapp.R;

public class Telephony extends AppCompatActivity {

    private TelephonyManager telephonyManager;
    private SmsManager smsManager;

    PhoneReceiver phoneReceiver;
    EditText edTextPhoneNumber, edTextSmsNumber, edTextSmsMessage;
    Button btnCall, btnSendSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson12_telephony);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        initView();
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        smsManager = SmsManager.getDefault();
        registerBroadcastReceiver();
    }

    private void initView() {
        edTextPhoneNumber = findViewById(R.id.edTextPhoneNumber);
        edTextSmsNumber = findViewById(R.id.edTextSmsNumber);
        edTextSmsMessage = findViewById(R.id.edTextSmsMessage);
        btnCall = findViewById(R.id.btnCall);
        btnSendSms = findViewById(R.id.btnSendSms);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
    }

    private void registerBroadcastReceiver() {
        phoneReceiver = new PhoneReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.huy.test.CALL");
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(phoneReceiver, filter);
        System.out.println("registered phone receiver");
    }

    private void makePhoneCall() {
        Intent intent = new Intent("com.huy.test.CALL");
        intent.putExtra("mess", "shit");
        sendBroadcast(intent);
    }

    private void sendSms() {
        String phoneNum = edTextSmsNumber.getText().toString();
        String message = edTextSmsMessage.getText().toString();
        if (phoneNum.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Please enter phone number and message!", Toast.LENGTH_SHORT).show();
            return;
        }
        smsManager.sendTextMessage(phoneNum, null, message, null, null);
        Toast.makeText(this, "Sent SMS!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        System.out.println("unregistered phone receiver");
        unregisterReceiver(phoneReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}