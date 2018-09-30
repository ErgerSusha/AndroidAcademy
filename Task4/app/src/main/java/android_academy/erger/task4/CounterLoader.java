package android_academy.erger.task4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.TextView;

public class CounterLoader extends AsyncTaskLoader<Integer> {
    private TextView progressTextView;
    static int num = 1;

    CounterLoader(@NonNull Context context, TextView progressTextView, int savedNum) {
        super(context);
        this.progressTextView = progressTextView;
        if (savedNum != -1)
            num = savedNum;
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        while(num < 10) {
            num++;
            Log.v("---- ", String.valueOf(num));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public void onStartLoading() {
        progressTextView.setText(R.string.loading_label);
        forceLoad();
    }
}