package android_academy.erger.task8.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android_academy.erger.task8.Constants;

public class PercentsService extends Service {
    private boolean isDestroyed;
    private ServiceHandler handler;

    @Override
    public void onCreate() {
        HandlerThread handlerThread = new HandlerThread("HardJobService", HandlerThread.MIN_PRIORITY);
        handlerThread.start();
        handler = new ServiceHandler(handlerThread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isDestroyed = false;
        Message msg = handler.obtainMessage();
        msg.arg1 = startId;
        handler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final class ServiceHandler extends Handler {

        ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                for (int i = 0; i <= 100 && !isDestroyed; i++) {
                    Thread.sleep(25);
                    Intent updateUIIntent = new Intent();
                    updateUIIntent.setAction(Constants.S_UPDATE_UI);
                    updateUIIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    updateUIIntent.putExtra(Constants.S_CURRENT_VALUE, i);
                    sendBroadcast(updateUIIntent);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }
}