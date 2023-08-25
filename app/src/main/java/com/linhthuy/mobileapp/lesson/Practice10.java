package com.linhthuy.mobileapp.lesson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.linhthuy.mobileapp.R;
import com.linhthuy.mobileapp.database.UserContract;

import java.util.Arrays;
import java.util.List;

public class Practice10 extends AppCompatActivity {

    private final String AUTHORITY = "com.linhthuy.Practice9_UserProvider";
    private final String CONTENT_PATH = "user";
    private final String URL = "content://" + AUTHORITY + "/" + CONTENT_PATH;
    private final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);

    private EditText edTextId, edTextUsername, edTextPassword, edTextPhone, edTextEmail;
    private Button btnSearch, btnInsert, btnUpdate, btnDelete;
    private Spinner spinnerRole;
    private TextView textResponse;
    private final List<String> listRole = Arrays.asList("None",
            UserContract.Role.USER,
            UserContract.Role.LIBRARY_CLERK,
            UserContract.Role.MANAGER);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson10);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getIntent().getStringExtra("practiceName") + "");

        edTextId = findViewById(R.id.EdTextId);
        edTextUsername = findViewById(R.id.EdTextUsername);
        edTextPassword = findViewById(R.id.EdTextPassword);
        edTextPhone = findViewById(R.id.EdTextPhone);
        edTextEmail = findViewById(R.id.EdTextEmail);
        btnSearch = findViewById(R.id.BtnSearch);
        btnInsert = findViewById(R.id.BtnInsert);
        btnUpdate = findViewById(R.id.BtnUpdate);
        btnDelete = findViewById(R.id.BtnDelete);
        spinnerRole = findViewById(R.id.SpinnerRole);
        textResponse = findViewById(R.id.TextResponse);

        textResponse.setVisibility(View.GONE);

        // role spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner, listRole);
        spinnerRole.setAdapter(adapter);

        // button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSearch();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleInsert();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleUpdate();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDelete();
            }
        });
    }

    private void handleSearch() {
        String username = edTextUsername.getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        String selection = UserContract.UserEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Uri uri = Uri.parse(URL);

        try {
            Cursor cursor = getContentResolver().query(uri, null, selection, selectionArgs, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            Toast.makeText(this, "Query success!", Toast.LENGTH_SHORT).show();
            textResponse.setText("Query success!");

            edTextId.setText(getCursorColumn(cursor, UserContract.UserEntry.COLUMN_ID));
            edTextUsername.setText(getCursorColumn(cursor, UserContract.UserEntry.COLUMN_USERNAME));
            edTextPassword.setText(getCursorColumn(cursor, UserContract.UserEntry.COLUMN_PASSWORD));
            edTextPhone.setText(getCursorColumn(cursor, UserContract.UserEntry.COLUMN_PHONE_NUMBER));
            edTextEmail.setText(getCursorColumn(cursor, UserContract.UserEntry.COLUMN_EMAIL));
            String role = getCursorColumn(cursor, UserContract.UserEntry.COLUMN_ROLE);
            spinnerRole.setSelection(listRole.indexOf(role));
        } catch (Exception e) {
            Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();
            textResponse.setText("Not found!");
        }
        textResponse.setVisibility(View.VISIBLE);
    }

    private String getCursorColumn(Cursor cursor, String column) {
        int colIndex = -1;
        if (cursor == null || (colIndex = cursor.getColumnIndex(column)) == -1) {
            return null;
        }
        return cursor.getString(colIndex);
    }

    private void handleInsert() {
        String username = edTextUsername.getText().toString();
        String role = spinnerRole.getSelectedItem().toString();
        String phoneNum = edTextPhone.getText().toString();
        if (username.isEmpty() || role.isEmpty() || role.equals("None") || phoneNum.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter username, role and phone", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_USERNAME, username);
        values.put(UserContract.UserEntry.COLUMN_PHONE_NUMBER, phoneNum);
        values.put(UserContract.UserEntry.COLUMN_ROLE, role);
        values.put(UserContract.UserEntry.COLUMN_PASSWORD, edTextPassword.getText().toString());
        values.put(UserContract.UserEntry.COLUMN_EMAIL, edTextEmail.getText().toString());

        try {
            Uri uri = getContentResolver().insert(Uri.parse(URL), values);
            Toast.makeText(this, "Insert success!", Toast.LENGTH_SHORT).show();
            textResponse.setText("Insert success! " + uri);
        } catch (Exception e) {
            Toast.makeText(this, "Insert failed!", Toast.LENGTH_SHORT).show();
            textResponse.setText("Insert failed!");
        }
        textResponse.setVisibility(View.VISIBLE);
    }

    private void handleUpdate() {

    }

    private void handleDelete() {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}