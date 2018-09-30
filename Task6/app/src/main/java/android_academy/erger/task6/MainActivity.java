package android_academy.erger.task6;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android_academy.erger.task6.database.AppDatabase;
import android_academy.erger.task6.entities.Note;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerAdapter adapter = new RecyclerAdapter(AppDatabase.getInstance(this).noteDao().getAll(), this);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                Note note = RecyclerAdapter.notes.get(position);
                AppDatabase.getInstance(MainActivity.super.getBaseContext()).noteDao().delete(note);
                RecyclerAdapter.notes.remove(note);
                adapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(recyclerView);

        findViewById(R.id.add_button).setOnClickListener(v -> {
            Note note = createNote(((EditText)findViewById(R.id.title_edit_text)).getText().toString(),
                    ((EditText)findViewById(R.id.body_edit_text)).getText().toString());
            AppDatabase.getInstance(this).noteDao().insert(note);
            adapter.updateList(AppDatabase.getInstance(this).noteDao().getAll());
        });
    }

    private Note createNote(String title, String body) {
        Note note = new Note();
        note.setTitle(title);
        note.setBody(body);
        return note;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int coordinates[] = new int[2];
            view.getLocationOnScreen(coordinates);

            float x = ev.getRawX() + view.getLeft() - coordinates[0];
            float y = ev.getRawY() + view.getTop() - coordinates[1];
            if ((x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) &&
                    (this.getWindow() != null && this.getWindow().getDecorView() != null)) {
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
}