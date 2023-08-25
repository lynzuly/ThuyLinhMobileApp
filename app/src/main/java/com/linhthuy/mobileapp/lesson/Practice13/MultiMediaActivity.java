package com.linhthuy.mobileapp.lesson.Practice13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.linhthuy.mobileapp.R;

import java.io.IOException;
import java.util.List;

public class MultiMediaActivity extends AppCompatActivity {
    public static final int READ_EXTERNAL_REQUEST_CODE = 1;

    // [UI]
    TextView musicPathsView;
    EditText idInput;
    Button playButton;
    Button pauseButton;
    Button stopButton;

    // [Utils]
    List<String> musicPaths;
    private MediaPlayer mediaPlayer;
    private boolean isPlay = false;
    private boolean isPause = false;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson13_multi_media);
        idInput = findViewById(R.id.idInput);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);
        musicPathsView = findViewById(R.id.musicPaths);
        mediaPlayer = new MediaPlayer();

        if (ContextCompat.checkSelfPermission(MultiMediaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MultiMediaActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_REQUEST_CODE);
        } else {
            FetchMusicPaths();
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(idInput.getText().toString());
                if (musicPaths.size() < id) {
                    Toast.makeText(MultiMediaActivity.this, "No music with id " + id, Toast.LENGTH_LONG).show();
                    return;
                }
                playMusic(musicPaths.get(id));
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseMusic();
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMusic();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    FetchMusicPaths();
                } else {
                    // Permission denied, handle accordingly (e.g., inform the user)
                }
                break;
        }
    }

    private void FetchMusicPaths() {
        musicPathsView.setText("");
        musicPaths = MediaUtils.getAllFiles(MultiMediaActivity.this,"", "audio/mpeg");
        for (int i = 0; i < musicPaths.size(); i++) {
            musicPathsView.setText(musicPathsView.getText() + "\n" + i + " - " + GetMusicName(musicPaths.get(i)));
        }
    }

    private String GetMusicName(String path) {
        String[] separate = path.split("/");
        return separate[separate.length - 1];
    }

    private void playMusic(String filePath) {
        try {
            isPlay = true;
            isPause = false;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void pauseMusic() {
        if (isPlay) {
            if (isPause) {
                mediaPlayer.start();
                pauseButton.setText("Pause");
            } else {
                mediaPlayer.pause();
                pauseButton.setText("Continue");
            }
            isPause = !isPause;
        } else {
            Toast.makeText(MultiMediaActivity.this, "No music is playing", Toast.LENGTH_LONG).show();
        }
    }

    private void stopMusic() {
        if (isPlay) {
            mediaPlayer.stop();
            isPlay = false;
            isPause = false;
        } else {
            Toast.makeText(MultiMediaActivity.this, "No music is playing", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}