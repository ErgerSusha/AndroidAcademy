package android_academy.erger.task1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTIVITY_EXTRA = "ACTIVITY_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.activity_button).setOnClickListener(this);
        findViewById(R.id.email_button).setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_button:
                startSecondActivity();
                break;
            case R.id.email_button:
                startEmailApp();
                break;
        }
    }

    private void startSecondActivity() {
        String editTextInput = ((EditText) findViewById(R.id.edittext)).getText().toString();
        Intent intent = new Intent(this, SecondActivity.class);

        intent.putExtra(ACTIVITY_EXTRA, editTextInput);

        startActivity(intent);
    }

    private void startEmailApp() {
        String editTextInput = ((EditText) findViewById(R.id.edittext)).getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.my_email) });
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
        intent.putExtra(Intent.EXTRA_TEXT, editTextInput);

        startActivity(Intent.createChooser(intent, getString(R.string.chooser_text)));
    }
}