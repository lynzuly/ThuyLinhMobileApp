package com.linhthuy.mobileapp.lesson.Practice7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.linhthuy.mobileapp.R;

public class ImplicitIntent extends AppCompatActivity {

    private static final int CALL_PERMISSION_REQUEST_CODE = 11112001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson7_implicit_intent);
        {
            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/2"));
            //startActivity(intent);
        }
        {
            //Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:11112001"));
            //startActivity(intent);
        }
        {
            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
            //startActivity(intent);
        }
        {
            /*Intent intent = new Intent (Intent.ACTION_WEB_SEARCH );
            intent.putExtra(SearchManager.QUERY, "straight hitting golf clubs");
            startActivity(intent);*/
        }
        {
            /*Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:11112001"));
            intent.putExtra("sms_body", "Suka blyat.");
            startActivity(intent);*/
        }
        {
            /*Intent myIntent = new Intent();
            myIntent.setType("image/pictures/*");
            myIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivity(myIntent);*/
        }
        {
            RequestACallTo("11112001");
        }
    }

    private String cachedPhoneNumber = "";
    private void RequestACallTo(String phoneNumber) {
        cachedPhoneNumber = phoneNumber;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        } else {
            Call(cachedPhoneNumber);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Call(cachedPhoneNumber);
            } else {
                // Permission denied, handle accordingly (e.g., inform the user)
            }
        }
    }

    private void Call(String phoneNumber) {
        if (cachedPhoneNumber.equals(""))
            return;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
        cachedPhoneNumber = "";
    }
}