package com.linhthuy.mobileapp.lesson.Practice7;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linhthuy.mobileapp.R;

public class ExplicitIntent extends AppCompatActivity {

    private Button buttonSendMessage;
    private EditText feedbackInput;
    private TextView feedbackOutput;

    private int MY_REQUEST_CODE = 11112001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson7_explicit_intent);

        buttonSendMessage = (Button)this.findViewById((R.id.SendFeedbackButton));
        feedbackInput = (EditText)this.findViewById(R.id.FullNameInput);
        feedbackOutput = (TextView)this.findViewById((R.id.FeedbackOutput));

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage();
            }

        });
    }


    public void SendMessage() {
        String fullName = this.feedbackInput.getText().toString();
        String message = "Hello, please say hello me.";

        Intent intent = new Intent(this, GreetingActivity.class);
        intent.putExtra("FullName", fullName);
        intent.putExtra("Message", message);

        //startActivity(intent);
        //startActivityForResult(intent, MY_REQUEST_CODE);
        startActivityIntent.launch(intent);
    }

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Add same code that you want to add in onActivityResult method
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        String feedback = result.getData().getStringExtra("Feedback");
                        feedbackOutput.setText(feedback);
                    } else {
                        feedbackOutput.setText("????");
                    }
                }
            });


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            String feedback = data.getStringExtra("Feedback");
            feedbackOutput.setText(feedback);
        } else {
            feedbackOutput.setText("????");
        }
    }
}