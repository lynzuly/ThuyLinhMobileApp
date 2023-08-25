package com.linhthuy.mobileapp.lesson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linhthuy.mobileapp.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class lesson11 extends AppCompatActivity{

    private final int IMAGE_LOADER_ID = 1;
    private final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE_FOR_INSERT = 2;
    private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE_FOR_DELETE = 3;
    private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE_FOR_RESET = 4;
    private ImageView imageView;
    private TextView imageInfo;
    private EditText inputId;
    private Button searchButton;
    private Button addButton;
    private Button deleteButton;
    private Button resetButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson10);

        imageView = findViewById(R.id.imageView);
        inputId = findViewById(R.id.inputId);
        searchButton = findViewById(R.id.searchButton);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        imageInfo = findViewById(R.id.imageInfo);
        resetButton = findViewById(R.id.reset);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(lesson11.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            lesson11.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    SearchImage();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(lesson11.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(lesson11.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE_FOR_INSERT);
                } else {
                    AddImage();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(lesson11.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(lesson11.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE_FOR_DELETE);
                } else {
                    DeleteImage();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(lesson11.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(lesson11.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE_FOR_RESET);
                } else {
                    ResetImage();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SearchImage();
                } else {
                    // Permission denied, handle accordingly (e.g., inform the user)
                }
                break;
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE_FOR_INSERT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AddImage();
                } else {
                    // Permission denied, handle accordingly (e.g., inform the user)
                }
                break;
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE_FOR_DELETE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DeleteImage();
                } else {
                    // Permission denied, handle accordingly (e.g., inform the user)
                }
                break;
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE_FOR_RESET:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ResetImage();
                } else {
                    // Permission denied, handle accordingly (e.g., inform the user)
                }
                break;
        }
    }

    private void SearchImage() {
        int id;
        try {
            id = Integer.parseInt(inputId.getText().toString());
        } catch (Exception e) {
            CheckImage();
            return;
        }

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        ContentResolver contentResolver = this.getContentResolver();
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            String imagePath = cursor.getString(columnIndex);

            imageInfo.setText(id + ": " + imagePath);
        } else {
            Toast.makeText(lesson11.this, "No image " + id, Toast.LENGTH_LONG).show();
        }
        cursor.close();
    }

    private void AddImage() {
        String destinationPath = createDestinationImagePath();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, destinationPath);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        CheckImage();
    }

    private void DeleteImage() {
        int id;
        try {
            id = Integer.parseInt(inputId.getText().toString());
        } catch (Exception e) {
            Toast.makeText(lesson11.this, "Must be an integer", Toast.LENGTH_LONG).show();
            return;
        }

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Images.Media._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        ContentResolver contentResolver = this.getContentResolver();

        int numberRowDeleted = contentResolver.delete(uri, selection, selectionArgs);
        if (numberRowDeleted > 0) {
            CheckImage();
        } else {
            Toast.makeText(lesson11.this, "No image with id " + id, Toast.LENGTH_LONG).show();
        }
    }

    private void ResetImage() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = this.getContentResolver();

        contentResolver.delete(uri, null, null);
        CheckImage();
    }

    private String createDestinationImagePath() {
        File destinationDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Pictures");

        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String timeStamp = dateFormat.format(new Date());
        return destinationDirectory.getAbsolutePath() + File.separator + timeStamp + "_clone.jpg";
    }


    private void CheckImage() {
        imageInfo.setText("");
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};

        ContentResolver contentResolver = this.getContentResolver();

        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int pathIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                String imagePath = cursor.getString(pathIndex);
                int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                String id = cursor.getString(idIndex);
                imageInfo.setText((imageInfo.getText() == "" ? "" :  (imageInfo.getText() + "\n")) + id + " - " + imagePath);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
