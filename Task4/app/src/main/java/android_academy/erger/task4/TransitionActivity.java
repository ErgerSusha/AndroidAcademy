package android_academy.erger.task4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TransitionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_transition);

        findViewById(R.id.asynctask_button).setOnClickListener(this);
        findViewById(R.id.loader_button).setOnClickListener(this);
        findViewById(R.id.threads_button).setOnClickListener(this);
    }

    private void startChosenActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.asynctask_button:
                startChosenActivity(AsyncTaskActivity.class);
                break;
            case R.id.loader_button:
                startChosenActivity(LoaderActivity.class);
                break;
            case R.id.threads_button:
                startChosenActivity(ThreadsActivity.class);
                break;
        }
    }
}
