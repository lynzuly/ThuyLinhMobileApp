package com.linhthuy.mobileapp.lesson.Practice7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linhthuy.mobileapp.R;

public class GreetingActivity extends AppCompatActivity {
    private TextView textViewMessage;
    private Button backButton;
    private String fullName;
    private String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson7_activity_greeting);

        textViewMessage = findViewById(R.id.MessageOutput);

        Intent intent = getIntent();
        fullName = intent.getStringExtra("FullName");
        message = intent.getStringExtra("Message");
        textViewMessage.setText(this.fullName + ": " + this.message);

        backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoBack();
            }
        });

    }
    public void GoBack() {
        this.onBackPressed();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        String feedback = "OK, Hello " + this.fullName + ".";
        intent.putExtra("Feedback", feedback);
        setResult(Activity.RESULT_OK, intent);
        super.finish();
    }
}