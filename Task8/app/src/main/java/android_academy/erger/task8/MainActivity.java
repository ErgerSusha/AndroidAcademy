package android_academy.erger.task8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android_academy.erger.task8.service.PercentsIntentService;
import android_academy.erger.task8.service.PercentsService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView percentsTextView;
    TextView progressTextView;
    Button sButton;
    Button isButton;
    ISUpdateUIBroadcastReceiver isUpdateUIBroadcastReceiver;
    SUpdateUIBroadcastReceiver sUpdateUIBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sButton = findViewById(R.id.start_service_button);
        isButton = findViewById(R.id.start_intent_service_button);
        percentsTextView = findViewById(R.id.percents_text_view);
        progressTextView = findViewById(R.id.progress_text_view);
        findViewById(R.id.start_intent_service_button).setOnClickListener(this);
        findViewById(R.id.start_service_button).setOnClickListener(this);

        isUpdateUIBroadcastReceiver = new ISUpdateUIBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(Constants.IS_UPDATE_UI);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(isUpdateUIBroadcastReceiver, intentFilter);

        sUpdateUIBroadcastReceiver = new SUpdateUIBroadcastReceiver();
        intentFilter = new IntentFilter(Constants.S_UPDATE_UI);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(sUpdateUIBroadcastReceiver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_intent_service_button:
                disEnAbleButtons(true);
                progressTextView.setText(R.string.running);
                startService(new Intent(MainActivity.this, PercentsIntentService.class));
                break;
            case R.id.start_service_button:
                disEnAbleButtons(true);
                progressTextView.setText(R.string.running);
                startService(new Intent(MainActivity.this, PercentsService.class));
                break;
        }
    }

    public class ISUpdateUIBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int value = intent.getIntExtra(Constants.IS_CURRENT_VALUE, 0);
            if (value == 100){
                disEnAbleButtons(false);
                progressTextView.setText(R.string.done);
            }
            percentsTextView.setText(String.format(getString(R.string.percent_label), value));
        }
    }

    public class SUpdateUIBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int value = intent.getIntExtra(Constants.S_CURRENT_VALUE, 0);
            if (value == 100) {
                disEnAbleButtons(false);
                progressTextView.setText(R.string.done);
            }
            percentsTextView.setText(String.format(getString(R.string.percent_label), value));
        }
    }

    private void disEnAbleButtons(boolean disable) {
        if (disable) {
            isButton.setEnabled(false);
            sButton.setEnabled(false);
        } else {
            isButton.setEnabled(true);
            sButton.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(isUpdateUIBroadcastReceiver);
        unregisterReceiver(sUpdateUIBroadcastReceiver);
    }
}
