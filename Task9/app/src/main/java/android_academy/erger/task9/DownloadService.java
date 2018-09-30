package android_academy.erger.task9;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class DownloadService extends Service {

    public static final String URL = "URL";
    public static final int ONGOING_NOTIFICATION_ID = 987;
    public static final int ERROR_NOTIFICATION_ID = 1024;
    public static final String CHANNEL_DEFAULT_IMPORTANCE = "Channel";
    public static final String FILE_PATH = "FILE_PATH";

    private NotificationManagerCompat notificationManager;

    public static void startService(Activity activity, String url) {
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra(URL, url);
        activity.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Log.d("TAG", "DownloadService # onStartCommand");
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        startForeground();

        String url = intent.getStringExtra(URL);
        Log.d("TAG", "DownloadService # URL: " + url);

        startDownloadThread(url);

        return START_STICKY;
    }

    private void startDownloadThread(String url) {
        new DownloadThread(url, new DownloadThread.DownloadCallBack() {
            @Override
            public void onProgressUpdate(int progress) {
                Log.d("TAG", "DownloadService, DownloadThread, onProgressUpdate: " + progress + "%");
                updateNotification(progress);
            }

            @Override
            public void onDownloadFinished(String filePath) {
                Log.d("TAG", "DownloadService, DownloadThread, onDownloadFinished: " + filePath);
                sendBroadcastMsgDownloadComplete(filePath);
                stopSelf();
            }

            @Override
            public void onError(String error) {
                Log.e("TAG", "DownloadService, DownloadThread, Error: " + error);
                notificationManager.notify(ERROR_NOTIFICATION_ID, createErrorNotification());
                notificationManager.cancel(DownloadService.ONGOING_NOTIFICATION_ID);
                stopSelf();
            }
        }).start();
    }

    private void startForeground() {
        createNotificationChannel();

        Notification notification = createNotification(0);

        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    private Notification createNotification(int progress) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        return new NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                .setContentTitle(String.format(Locale.getDefault(), "Downloading... %d%%", progress))
                .setContentText(getText(R.string.notification_message))
                .setSmallIcon(R.drawable.ic_file_download)
                .setContentIntent(pendingIntent)
                .build();
    }

    private Notification createErrorNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(getText(R.string.notification_message))
                .setSmallIcon(R.drawable.ic_file_download)
                .build();
    }

    private void updateNotification(int progress) {
        Notification notification = createNotification(progress);
        notificationManager.notify(ONGOING_NOTIFICATION_ID, notification);
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_title);
            String description = getString(R.string.notification_message);

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, name, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
    }

    private void sendBroadcastMsgDownloadComplete(String filePath) {
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            intent = new Intent(this, CompleteReceiver.class);
        } else {
            intent = new Intent(CompleteReceiver.ACTION_DOWNLOAD_COMPLETE);
        }
        intent.putExtra(FILE_PATH, filePath);
        sendBroadcast(intent);
    }

    public static class CompleteReceiver extends BroadcastReceiver {

        public static final String ACTION_DOWNLOAD_COMPLETE = "DOWNLOAD_COMPLETE";

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", "CompleteReceiver # onReceive");
            Toast.makeText(context, "File downloaded", Toast.LENGTH_LONG).show();

            String filePath = intent.getStringExtra(DownloadService.FILE_PATH);
            Log.d("TAG", "CompleteReceiver # onReceive, filePath: " + filePath);

            MainActivity.startActivity(context, intent.getExtras());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}