package android_academy.erger.task5;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;
import android_academy.erger.task5.api.App;
import android_academy.erger.task5.entities.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter adapter;
    private RecyclerView recyclerView;
    private TextView statusText;
    private Button searchBtn;

    private int pageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_films);
        initRecyclerView();

        statusText = findViewById(R.id.status_text);
        searchBtn = findViewById(R.id.search_button);
        final EditText searchQueryEdit = findViewById(R.id.search_edit_text);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchImages(searchQueryEdit.getText().toString());
                hideKeyboard();
            }
        });
    }

    private void searchImages(String query) {
        showMessage(getString(R.string.searching));

        getApp().getApi().getSearch(query).enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movie>> call, @NonNull Response<List<Movie>> response) {
                // Check if call went through and update the UI accordingly.
                if (response.code() == 200) {
                    final List<Movie> movies = response.body();
                    if (movies != null)
                        showContent(movies);
                    showBtnMessageIfNeeded();
                } else {
                    showMessage(getString(R.string.error, response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Movie>> call, @NonNull Throwable t) {
                // HTTP call failed, show something to the user.
                showMessage(getString(R.string.error_http));
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.film_recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void showMessage(String message) {
        statusText.setText(message);
        recyclerView.setVisibility(View.INVISIBLE);
        statusText.setVisibility(View.VISIBLE);
    }

    private void showContent(List<Movie> movies) {
        adapter.replaceWith(movies);
        recyclerView.setVisibility(View.VISIBLE);
        statusText.setVisibility(View.INVISIBLE);
    }

    private void showBtnMessageIfNeeded() {
        // Update button text after first search succeeded
        if (pageNumber == 1)
            searchBtn.setText(R.string.show_next);
        else
            pageNumber++;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private App getApp() {
        return ((App) getApplication());
    }
}
