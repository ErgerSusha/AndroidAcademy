package android_academy.erger.task4;

import android.os.SystemClock;

public class MyCounterAsyncTaskImpl extends MySimpleAsyncTask<Integer> {
    private IAsyncTaskEvents mIAsyncTaskEvents;

    public MyCounterAsyncTaskImpl(IAsyncTaskEvents iAsyncTaskEvents) {
        this.mIAsyncTaskEvents = iAsyncTaskEvents;
    }

    @Override
    protected Integer doInBackground() {
        int end = 10;
        for (int i = 0; i <= end; i++) {
            if (isCancelled())
                return i;
            publishProgress(i);
            SystemClock.sleep(500);
        }
        return end;
    }

    @Override
    protected void onPreExecute() {
        if (mIAsyncTaskEvents != null) {
            mIAsyncTaskEvents.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute() {
        if (mIAsyncTaskEvents != null) {
            mIAsyncTaskEvents.onPostExecute();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (mIAsyncTaskEvents != null) {
            mIAsyncTaskEvents.onProgressUpdate(values[0]);
        }
    }
}