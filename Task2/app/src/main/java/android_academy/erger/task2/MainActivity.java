package android_academy.erger.task2;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        findViewById(R.id.button_minsk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://en.wikipedia.org/wiki/Minsk"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        if (displayMetrics.heightPixels / displayMetrics.density <= 550) {
            TextView textView = findViewById(R.id.lorem);
            textView.setMovementMethod(new ScrollingMovementMethod());
        }
    }
}