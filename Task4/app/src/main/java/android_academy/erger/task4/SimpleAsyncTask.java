package android_academy.erger.task4;

public abstract class SimpleAsyncTask<Param> {

    protected volatile boolean mCancelled = false;

    protected abstract void onPreExecute();

    protected abstract Param doInBackground();

    protected abstract void onPostExecute();

    protected abstract void execute();

    protected void onProgressUpdate(Param... values) { }

    protected abstract void publishProgress(Param... values);

    public abstract void cancel();

    public boolean isCancelled() { return mCancelled; }
}