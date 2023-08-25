package com.linhthuy.mobileapp.lesson.Practice12;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        System.out.println("received in phoneReceiver");
        if (action.equals("android.intent.action.PHONE_STATE")) {
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            System.out.println("phoneState " + phoneState);
            if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                System.out.println("Incoming call from " + phoneNumber);
                Toast.makeText(context, "Incoming Call From: " + phoneNumber, Toast.LENGTH_LONG).show();
            }
        } else if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                for (SmsMessage message : messages) {
                    String msg = message.getMessageBody();
                    long when = message.getTimestampMillis();
                    String from = message.getOriginatingAddress();
                    System.out.println("At " + when + "Received sms from " + from +": " + msg);
                    Toast.makeText(context, from+":"+ msg,Toast.LENGTH_LONG).show();
                }
            }
        } else if (action.equals("com.huy.test.CALL")) {
            System.out.println("received from broadcast " +  intent.getStringExtra("mess"));
        }
    }
}