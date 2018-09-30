package android_academy.erger.task1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView secondTextView = findViewById(R.id.second_textview);
        String intentText = getIntent().getStringExtra(MainActivity.ACTIVITY_EXTRA);
        secondTextView.setText(intentText);
    }
}
