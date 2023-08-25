package com.linhthuy.mobileapp.lesson.Practice12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.linhthuy.mobileapp.R;

public class Wifi extends AppCompatActivity {

    private ConnectivityManager connectivity;
    private WifiManager wifi;

    Button btnCheckNetwork;
    TextView textNetworkInfo;
    WifiReceiver receiver;
    ListView wifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson12_wifi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        initView();

        connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        checkNetwork();
        registerBroadcastReceiver();
    }

    private void initView() {
        btnCheckNetwork = findViewById(R.id.btnCheckNetwork);
        textNetworkInfo = findViewById(R.id.textNetworkInfo);
        wifiList = findViewById(R.id.wifiList);

        btnCheckNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNetwork();
            }
        });
    }

    private void registerBroadcastReceiver() {
        receiver = new WifiReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.conn.wifi.WIFI_STATE_CHANGED_ACTION");
        registerReceiver(receiver, intentFilter);
    }
    private void checkNetwork(){
        NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
        if (activeNetwork != null) {
            textNetworkInfo.setText("You are connected to " + activeNetwork.getTypeName() + "!");
        } else {
            textNetworkInfo.setText("You are offline!");
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}