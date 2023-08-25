package com.linhthuy.mobileapp.lesson.Practice12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.linhthuy.mobileapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Practice12_Sensor extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor currentSensor;
    List<String> listType = Arrays.asList("Motion - Accelerometer",
            "Environmental - Pressure",
            "Position - Magnetic");

    Button btnGetSensor, btnValueSensor;
    TextView btnHideListSensor, textSensorInfo, textSensorValue;
    Spinner spinnerSensor;
    ListView sensorList;
    int selectSensorType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson12_sensor);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        initView();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    private void getAllSensor() {
        List<Sensor> listSensor = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> sensorInfo = new ArrayList<>();
        for (Sensor s : listSensor) {
            StringBuilder sb = new StringBuilder();
            sb.append("Sensor name: ").append(s.getName()).append(", ");
            sb.append("version: ").append(s.getVersion()).append(", ");
            sb.append("type: ").append(s.getType());
            sensorInfo.add(sb.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, sensorInfo);
        sensorList.setAdapter(adapter);
        sensorList.setVisibility(View.VISIBLE);
        btnHideListSensor.setVisibility(View.VISIBLE);
    }

    private final SensorEventListener ssEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Accuracy: ").append(e.accuracy).append("\n");
            int sensorType = e.sensor.getType();
            if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                sb.append("Acceleration force:\n");
                sb.append("x: ").append(e.values[0]).append(" m/s2\n");
                sb.append("y: ").append(e.values[1]).append(" m/s2\n");
                sb.append("z: ").append(e.values[2]).append(" m/s2");
            } else if (sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE
                    || sensorType == Sensor.TYPE_LIGHT
                    || sensorType == Sensor.TYPE_PRESSURE
                    || sensorType == Sensor.TYPE_RELATIVE_HUMIDITY) {
                sb.append("Ambient air Pressure: ").append(e.values[0]).append(" hPa");
            } else if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
                sb.append("Geomagnetic field strength:\n");
                sb.append("x: ").append(e.values[0]).append(" μT\n");
                sb.append("y: ").append(e.values[1]).append(" μT\n");
                sb.append("z: ").append(e.values[2]).append(" μT");
            }
            textSensorValue.setText(sb.toString());
            textSensorValue.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private void getSensorValue() {
        if (selectSensorType == -1) {
            selectSensorType = Sensor.TYPE_ACCELEROMETER;
        }
        if (currentSensor != null) {
            sensorManager.unregisterListener(ssEventListener, currentSensor);
            currentSensor = null;
        }

        currentSensor = sensorManager.getDefaultSensor(selectSensorType);
        StringBuilder sb = new StringBuilder();
        sb.append("Sensor name: ").append(currentSensor.getName()).append(", ");
        sb.append("version: ").append(currentSensor.getVersion()).append(", ");
        sb.append("type: ").append(currentSensor.getType());

        textSensorInfo.setText(sb.toString());
        textSensorInfo.setVisibility(View.VISIBLE);

        if (selectSensorType == Sensor.TYPE_MAGNETIC_FIELD) {
            sensorManager.registerListener(ssEventListener, currentSensor, SensorManager.SENSOR_DELAY_NORMAL); // slowest
        } else {
            sensorManager.registerListener(ssEventListener, currentSensor, SensorManager.SENSOR_DELAY_UI); // second slow
        }
    }

    private void initView() {
        btnGetSensor = findViewById(R.id.btnGetSensor);
        btnHideListSensor = findViewById(R.id.btnHideListSensor);
        sensorList = findViewById(R.id.sensorList);
        spinnerSensor = findViewById(R.id.spinnerSensor);
        btnValueSensor= findViewById(R.id.btnValueSensor);
        textSensorValue = findViewById(R.id.textSensorValue);
        textSensorInfo = findViewById(R.id.textSensorInfo);

        btnGetSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllSensor();
            }
        });

        btnHideListSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sensorList.getVisibility() == View.VISIBLE) {
                    sensorList.setVisibility(View.GONE);
                    btnHideListSensor.setText("SHOW");
                } else {
                    sensorList.setVisibility(View.VISIBLE);
                    btnHideListSensor.setText("HIDE");
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listpractice, listType);
        adapter.setDropDownViewResource(R.layout.spinner);
        spinnerSensor.setAdapter(adapter);
        spinnerSensor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println("change spinner " + position);
                switch (position) {
                    case 0: //Motion - Accelerometer
                    default:
                        selectSensorType = Sensor.TYPE_ACCELEROMETER;
                        break;
                    case 1: //Environmental - Pressure: Ambient air pressure
                        selectSensorType = Sensor.TYPE_PRESSURE;
                        break;
                    case 2: // Position - Magnetic: x, y, z μT
                        selectSensorType = Sensor.TYPE_MAGNETIC_FIELD;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnValueSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSensorValue();
            }
        });
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(ssEventListener);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}