package android_academy.erger.task4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ThreadsActivity extends AppCompatActivity implements View.OnClickListener, IAsyncTaskEvents {

    private TextView progressTextView;

    private MySimpleAsyncTask asyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_and_threads);

        findViewById(R.id.asynctask_create_button).setOnClickListener(this);
        findViewById(R.id.asynctask_start_button).setOnClickListener(this);
        findViewById(R.id.asynctask_cancel_button).setOnClickListener(this);

        progressTextView = findViewById(R.id.asynctask_progress_text_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.asynctask_create_button:
                asyncTask = new MyCounterAsyncTaskImpl(this);
                break;
            case R.id.asynctask_start_button:
                if ((asyncTask != null) && !asyncTask.isCancelled()) asyncTask.execute();
                break;
            case R.id.asynctask_cancel_button:
                asyncTask.cancel();
                break;
        }
    }

    @Override
    public void onPreExecute() { }

    @Override
    public void onPostExecute() {
        progressTextView.setText(R.string.done_label);
    }

    @Override
    public void onProgressUpdate(Integer integer) {
        progressTextView.setText(String.valueOf(integer));
    }

    @Override
    public void onCancel() { }

    @Override
    protected void onDestroy() {
        if (asyncTask != null) {
            asyncTask.cancel();
            asyncTask = null;
        }
        super.onDestroy();
    }
}