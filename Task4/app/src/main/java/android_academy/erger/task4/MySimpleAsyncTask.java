package android_academy.erger.task4;

import android.os.Handler;
import android.os.Looper;

public abstract class MySimpleAsyncTask<Param> extends SimpleAsyncTask<Param> {
    private Thread mBackgroundThread;

    protected abstract void onPreExecute();
    protected abstract Param doInBackground();
    protected abstract void onPostExecute();

    @Override
    public void execute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onPreExecute();
                mBackgroundThread = new Thread("Handler_executor_thread") {
                    @Override
                    public void run() {
                        doInBackground();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onPostExecute();
                            }
                        });
                    }
                };
                mBackgroundThread.start();
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    @Override
    public void cancel() {
        mCancelled = true;
        if (mBackgroundThread != null) {
            mBackgroundThread.interrupt();
        }
    }

    @Override
    protected void publishProgress(final Param... values) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onProgressUpdate(values);
            }
        });
    }
}