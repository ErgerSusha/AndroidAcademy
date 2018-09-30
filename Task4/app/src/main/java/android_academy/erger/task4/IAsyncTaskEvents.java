package android_academy.erger.task4;

public interface IAsyncTaskEvents {
    void onPreExecute();

    void onPostExecute();

    void onProgressUpdate(Integer integer);

    void onCancel();
}