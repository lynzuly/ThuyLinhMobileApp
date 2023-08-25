package com.linhthuy.mobileapp.lesson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linhthuy.mobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class practice2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        // number picker
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) NumberPicker numberPicker = findViewById(R.id.NumberPicker);
        int minValue = 1;
        int maxValue = 1000;
        List<String> pickerValues = new ArrayList<>();
        for (int i = minValue; i < maxValue; i++) {
            pickerValues.add(i + "");
        }
        String[] pickerValueArr = new String[pickerValues.size()];
        numberPicker.setDisplayedValues(pickerValues.toArray(pickerValueArr));
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue - minValue);
        numberPicker.setValue(200);

        // progress bar
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ProgressBar progressBar = findViewById(R.id.ProgressBar);
        progressBar.setProgress(20);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                float progress = (float)(newVal - minValue)/(maxValue - minValue)*100;
                progressBar.setProgress((int) progress);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText amountEdit = findViewById(R.id.AmountEdit);
        amountEdit.addTextChangedListener(new EditTextListener());
        amountEdit.setText("199");

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView totalTextView = findViewById(R.id.TotalTextView);
        totalTextView.setText("Total so far $" + amountEdit.getText());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

    private class EditTextListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            TextView totalTextView = findViewById(R.id.TotalTextView);
            String newVal = editable.toString();
            totalTextView.setText("Total so far $" + (newVal.isEmpty() ? 0 : newVal));
        }
    }
}