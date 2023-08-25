package com.linhthuy.mobileapp.lesson.Practice12;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.linhthuy.mobileapp.R;

import java.util.Arrays;
import java.util.List;

public class Practice12 extends AppCompatActivity {

    final List<String> deviceList = Arrays.asList(
            "Sensors",
            "Wifi",
            "Telephony services",
            "Camera",
            "Bluetooth"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson12);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.listpractice, deviceList);
        ListView listView = findViewById(R.id.deviceList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = null;
                Context appContext = getApplicationContext();
                switch (position + 1) {
                    case 1:
                    default:
                        intent = new Intent(appContext, Sensor.class);
                        break;
                    case 2:
                        intent = new Intent(appContext, Wifi.class);
                        break;
                    case 3:
                        intent = new Intent(appContext, Telephony.class);
                        break;
                }
                intent.putExtra("practiceName", "11 - " + deviceList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}