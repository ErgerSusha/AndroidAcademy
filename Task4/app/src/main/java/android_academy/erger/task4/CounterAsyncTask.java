package android_academy.erger.task4;

import android.os.AsyncTask;

public class CounterAsyncTask extends AsyncTask<Void, Integer, Void> {

    private IAsyncTaskEvents iAsyncTaskEvents;

    public int i = 1;

    CounterAsyncTask(int savedValue, IAsyncTaskEvents iAsyncTaskEvents) {
        if (savedValue != -1)
            i = savedValue;
        this.iAsyncTaskEvents = iAsyncTaskEvents;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (i < 11) {
            publishProgress(i++);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        iAsyncTaskEvents.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        iAsyncTaskEvents.onPostExecute();
    }
}