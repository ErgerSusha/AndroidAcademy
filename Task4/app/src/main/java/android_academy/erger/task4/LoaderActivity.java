package android_academy.erger.task4;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoaderActivity extends Activity {
    TextView progressTextView;
    LoaderManager loaderManager;
    private final Integer LOADER_ID = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        progressTextView = findViewById(R.id.loader_progress_text_view);

        final Bundle queryBundle = new Bundle();
        if (savedInstanceState != null) {
            queryBundle.putInt("b", savedInstanceState.getInt("a"));
            loaderManager = getLoaderManager();
            progressTextView.setText(R.string.loading_label);
            loaderManager.initLoader(LOADER_ID, queryBundle, loaderCallbacks).forceLoad();
        }

        findViewById(R.id.loader_start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryBundle.putInt("b", -1);
                loaderManager = getLoaderManager();
                loaderManager.initLoader(LOADER_ID, queryBundle, loaderCallbacks);
            }
        });

        findViewById(R.id.loader_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loaderManager != null) {
                    loaderManager.destroyLoader(LOADER_ID);
                    progressTextView.setText(R.string.cancelled_label);
                }
            }
        });
    }

    LoaderManager.LoaderCallbacks loaderCallbacks = new LoaderManager.LoaderCallbacks() {
        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            return new CounterLoader(getBaseContext(), progressTextView, args.getInt("b"));
        }

        @Override
        public void onLoadFinished(Loader loader, Object data) {
            progressTextView.setText(R.string.done_label);
            CounterLoader.num = 1;
        }

        @Override
        public void onLoaderReset(Loader loader) { }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("a", CounterLoader.num);
        super.onSaveInstanceState(outState);
    }
}
