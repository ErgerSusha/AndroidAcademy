package android_academy.erger.task4;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AsyncTaskActivity extends AppCompatActivity {
    static TextView progressTextView;
    CounterAsyncTask counterAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_and_threads);

        progressTextView = findViewById(R.id.asynctask_progress_text_view);

        findViewById(R.id.asynctask_create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterAsyncTask = new CounterAsyncTask(-1, iAsyncTaskEvents);
            }
        });

        findViewById(R.id.asynctask_start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counterAsyncTask != null &&
                        counterAsyncTask.getStatus() != AsyncTask.Status.RUNNING &&
                        counterAsyncTask.getStatus() != AsyncTask.Status.FINISHED)
                    counterAsyncTask.execute();
            }
        });

        findViewById(R.id.asynctask_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counterAsyncTask != null)
                    iAsyncTaskEvents.onCancel();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("CURRENT_VALUE", counterAsyncTask.i);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int i = savedInstanceState.getInt("CURRENT_VALUE");
        counterAsyncTask = new CounterAsyncTask(i, iAsyncTaskEvents);
        counterAsyncTask.execute();
    }

    IAsyncTaskEvents iAsyncTaskEvents = new IAsyncTaskEvents() {

        @Override
        public void onProgressUpdate(Integer integer) {
            progressTextView.setText(String.valueOf(integer));
        }

        @Override
        public void onPostExecute() {
            progressTextView.setText(R.string.done_label);
        }

        @Override
        public void onCancel() {
            counterAsyncTask.cancel(true);
            progressTextView.setText(R.string.cancelled_label);
        }

        @Override
        public void onPreExecute() { }
    };
}