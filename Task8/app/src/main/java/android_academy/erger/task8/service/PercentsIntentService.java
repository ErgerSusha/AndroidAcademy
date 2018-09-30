package android_academy.erger.task8.service;

import android.app.IntentService;
import android.content.Intent;
import android_academy.erger.task8.Constants;

public class PercentsIntentService extends IntentService {
    private boolean isDestroyed = false;

    public PercentsIntentService() {
        super("PercentsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for (int i = 0; i <= 100 && !isDestroyed; i++) {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Intent updateUIIntent = new Intent();
            updateUIIntent.setAction(Constants.IS_UPDATE_UI);
            updateUIIntent.addCategory(Intent.CATEGORY_DEFAULT);
            updateUIIntent.putExtra(Constants.IS_CURRENT_VALUE, i);
            sendBroadcast(updateUIIntent);
        }
    }

    @Override
    public void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
    }
}
