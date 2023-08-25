package com.linhthuy.mobileapp.lesson.Practice9.IntentToService;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.linhthuy.mobileapp.R;


public class NotificationService extends Service {
    private static final int NOTIFICATION_ID = 123;
    private static final String CHANNEL_ID = "NotificationServiceChannel";
    private static final CharSequence CHANNEL_NAME = "Notification Service Channel";
    private static final String CHANNEL_DESCRIPTION = "Channel for NotificationService";

    private CountDownTimer countDownTimer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create a notification
        Notification notification = createNotification();

        // Start the service in the foreground
        startForeground(NOTIFICATION_ID, notification);

        // Start the countdown timer
        startCountdownTimer();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Notification Service")
                .setContentText("Service is running in the foreground")
                .setContentIntent(pendingIntent);

        return builder.build();
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showCountdownProgressNotification(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                showCountdownFinishedNotification();
            }
        };
        countDownTimer.start();
    }

    private void showCountdownFinishedNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Countdown Finished")
                .setContentText("The countdown timer has finished!")
                .setSmallIcon(R.drawable.ic_notification);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void showCountdownProgressNotification(long progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Countdown In Progress")
                .setContentText("Time remaining: " + progress)
                .setSmallIcon(R.drawable.ic_notification);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}